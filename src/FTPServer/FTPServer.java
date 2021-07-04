package FTPServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FTPServer {

    private static final int Ctrlport = 21;

    ServerSocket serverSocket;

    public FTPServer(String rootPath, String usersPath, boolean isAnonymous) throws IOException {//建立服务控制端口

        serverSocket = new ServerSocket(Ctrlport);
        //初始化系统信息
        Server_Config.init(rootPath, usersPath, isAnonymous);

    }

    public void listen() throws IOException {

        Socket socket;
        while (true) {
            //这个是建立连接,三次握手的过程，当连接建立了之后，两个socket之间的通讯是直接通过流进行的，不用再通过这一步
            socket = serverSocket.accept();
            Server_Ctrl thread = new Server_Ctrl(socket);
            thread.start();
        }
    }

//    public static void main(String args[]) throws IOException {
//        FTPServer ftpServer = new FTPServer("D:\\MY_WEB\\Data", "D:\\MY_WEB\\Data\\A.properties", false);
//        ftpServer.listen();
//    }


}
