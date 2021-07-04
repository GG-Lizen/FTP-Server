package Command;

import FTPServer.Server_Ctrl;

import java.io.BufferedWriter;
import java.io.IOException;

public class QuitCommand implements Command {
    @Override
    public void getResult(String data, BufferedWriter writer, Server_Ctrl t) {
        try {
            writer.write("221 GoodBye.\r\n");
            System.out.println(Thread.currentThread() + "  主机:" + t.getSocket().getInetAddress().getHostAddress() + " " + "221 GoodBye\r\n");
            writer.flush();
            t.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
