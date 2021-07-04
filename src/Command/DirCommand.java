package Command;

import FTPServer.Server_Config;
import FTPServer.Server_Ctrl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DirCommand implements Command {
    @Override
    public void getResult(String data, BufferedWriter writer, Server_Ctrl t) {
        File file = new File(t.getCurrentDirectory());
        try {
            if (!file.exists() || !file.isDirectory()) {

                writer.write("550 There is no file to transfer.\r\n");//550 Requested action not taken.  File unavailable (e.g., file not found, no access).
                writer.flush();
            } else {
                StringBuffer dirList = new StringBuffer();

                for (String item : file.list()) {
                    File itemFile = new File(t.getCurrentDirectory() + File.separator + item);//如果直接用file会判断错误
                    Long lastModified = itemFile.lastModified();
                    Date date = new Date(lastModified);//最后修改的日期
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateString = formatter.format(date);
                    String type;
                    String size = "";
                    if (itemFile.isDirectory()) {
                        type = "<DIR>";//文件夹

                    } else {
                        type = "     ";//文件
                        size = String.format("%-10d", itemFile.length());
                    }
                    dirList.append(dateString + "\t\t" + String.format("%-5s", type) + " ");
                    dirList.append(String.format("%-10s", size));
                    dirList.append(item);
                    dirList.append("\r\n");

                }


                writer.write("125 Data connection already open; Transfer starting.\r\n");// 125 Data connection already open; transfer starting.
                writer.flush();
                Socket socket = t.getDatasocket();

                System.out.println(Thread.currentThread() + "  主机:" + t.getSocket().getInetAddress().getHostAddress() + " " + socket.getLocalPort());
                BufferedWriter portWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "GBK"));
                portWriter.write(dirList.toString());
                portWriter.flush();
                socket.close();
                writer.write("226 Transfer complete.\r\n");// 226 Closing data connection.
                writer.flush();
                System.out.println(Thread.currentThread() + "  主机:" + t.getSocket().getInetAddress().getHostAddress() + " " + "List :");
                System.out.println(dirList.toString());

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            try {
                writer.write("550 Requested action not taken.\r\n");//550 Requested action not taken.  File unavailable (e.g., file not found, no access).
                writer.write("ERROR details: directory not found.\r\n  550 End\n");
                writer.flush();
                t.setCurrentDirectory(Server_Config.getRootPath());//文件路径出错，重置当前路径
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }
    }
}