package Command.extend;

import Command.Command;
import FTPServer.Server_Ctrl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class PasvCommand implements Command {
    @Override
    public void getResult(String data, BufferedWriter writer, Server_Ctrl t) {
        System.out.println("execute the PASV command......");
        String response = "";
        try {
            int tempport = -1;
            ServerSocket serverSocket = null;
            while (serverSocket == null) {
                tempport = (int) (Math.random() * 100000) % 9999 + 1024;
                serverSocket = getDataServerSocket(tempport);
            }
            if (tempport != -1 && serverSocket != null) {
                int p1 = tempport / 256;
                int p2 = tempport - p1 * 256;
                response = "2271 Entering Passive Mode (" + InetAddress.getLocalHost().getHostAddress().replace(".", ",") + "," + p1 + "," + p2 + ")";
                System.out.println(response);
            }

            writer.write(response);
            writer.write("\r\n");
            writer.flush();
            System.out.println("set PASV successful");

            //数据传输的socket
            Socket datasocket = null;
            //设置被动模式相关参数
            datasocket = serverSocket.accept();
            t.setDatasocket(datasocket);
            t.setDataPort(tempport);
            t.setIspasv(true);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static ServerSocket getDataServerSocket(int port) {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
        return socket;
    }

}
