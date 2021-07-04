package FTPServer;
//协议解释器

import Command.Command;
import Command.CommandFactory;
import Command.PassCommand;
import Command.UserCommand;

import java.io.*;
import java.net.Socket;

public class Server_Ctrl extends Thread {


    private boolean isLogin = false;//登录状态判定
    private boolean ispasv = false;//判断是否为被动模式

    private Socket socket = null;

    BufferedReader reader;
    BufferedWriter writer;

    private String dataIP = "";
    private int dataPort;
    private Socket datasocket = null;//被动模式下的数据连接 ispasv = true 有效

    private String currentDirectory = Server_Config.getRootPath();
    private String RenameRegister = null;
    //当前的线程所对应的用户
    public static final ThreadLocal<String> USER = new ThreadLocal<>();

    //constructor
    public Server_Ctrl(Socket s) {//建立链接
        this.socket = s;
    }

    //setter
    public void setLogin(boolean login) {
        isLogin = login;
    }

    public void setDataIP(String dataIP) {
        this.dataIP = dataIP;
    }

    public void setDataPort(int dataPort) {
        this.dataPort = dataPort;
    }

    public void setCurrentDirectory(String currentDirectory) {
        this.currentDirectory = currentDirectory;
    }

    public void setRenameRegister(String renameRegister) {
        RenameRegister = renameRegister;
    }

    public void setDatasocket(Socket datasocket) {
        this.datasocket = datasocket;
    }

    public void setIspasv(boolean ispasv) {
        this.ispasv = ispasv;
    }

    //getter
    public ThreadLocal<String> getUSER() {
        return USER;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public String getDataIP() {
        return dataIP;
    }

    public int getDataPort() {
        return dataPort;
    }


    public String getRenameRegister() {
        return RenameRegister;
    }

    public Socket getDatasocket() throws IOException {
        return ispasv ? datasocket : new Socket(dataIP, dataPort, null, 20);

    }

    public String getCurrentDirectory() {
        return currentDirectory;
    }

    public Socket getSocket() {
        return socket;
    }

    public boolean isIspasv() {
        return ispasv;
    }

    @Override
    public void run() {
        System.out.println("a new client is connected=== ");

        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "GBK"));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "GBK"));

            //输出欢迎
            this.writer.write("220 welcome to my ftp server.\r\n");// 220 Service ready for new user.
            this.writer.flush();
            //----test--------
            if (utils.Ping.ping(socket.getInetAddress().toString()) == 128) {
                this.reader.readLine();
                this.writer.write("\r\n");
                this.writer.flush();
            }
            while (true) {
                //两种情况会关闭连接：(1)quit命令 (2)密码错误
                if (!this.socket.isClosed()) {
                    //取客户端发送的指令，以截取命令和数据
                    String command = this.reader.readLine();
                    System.out.println(Thread.currentThread() + "  主机:" + this.socket.getInetAddress().getHostAddress() + " " + command);
                    if (command != null) {//command is null 代表客户端强制断开了
                        String[] datas = command.split(" ");
                        Command commandSolver = CommandFactory.createCommand(datas[0]);

                        //登录验证,在没有登录的情况下，无法使用除了user,pass之外的命令
                        if (loginStatus(commandSolver)) {
                            if (commandSolver == null) {
                                this.writer.write("502 Command does not exist.\r\n");// 502 Command not implemented.
                                this.writer.flush();
                            } else {
                                String data = "";
                                if (datas.length >= 2) {
                                    data = datas[1];
                                }
                                commandSolver.getResult(data, this.writer, this);

                            }
                        } else {
                            this.writer.write("532 Log out,please login first.\r\n");// 532 Need account for storing files.
                            this.writer.flush();
                        }

                    } else {
                        //command is null 代表客户端强制断开了
                        this.reader.close();
                        this.writer.close();
                        this.socket.close();
                        break;
                    }
                } else {
                    //连接已经关闭，这个线程不再有存在的必要
                    this.reader.close();
                    this.writer.close();
                    break;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            System.out.println(Thread.currentThread() + "  主机:" + this.socket.getInetAddress().getHostAddress() + " " + "结束tcp连接");
        }

    }

    //登录判断
    public boolean loginStatus(Command command) {
        if (command instanceof UserCommand || command instanceof PassCommand) {
            return true;
        } else {
            return this.isLogin;
        }
    }

}
