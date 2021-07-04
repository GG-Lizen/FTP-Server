package Command;

import FTPServer.Server_Ctrl;

import java.io.BufferedWriter;
import java.io.IOException;

public class PortCommand implements Command {
    @Override
    public void getResult(String data, BufferedWriter writer, Server_Ctrl t) {
        String[] result = data.split(",");
        String ip = result[0] + "." + result[1] + "." + result[2] + "." + result[3];
        System.out.println(Thread.currentThread() + "  主机:" + t.getSocket().getInetAddress().getHostAddress() + " " + "ip:" + ip);
        int port = Integer.parseInt(result[4]) * 256 + Integer.parseInt(result[5]);//port= p1*256+p2
        System.out.println(Thread.currentThread() + "  主机:" + t.getSocket().getInetAddress().getHostAddress() + " " + "port:" + port);
        try {
            writer.write("200 PORT command successful.\r\n");// 200 Command okay.
            writer.flush();
            t.setDataIP(ip);
            t.setDataPort(port);
            t.setIspasv(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

