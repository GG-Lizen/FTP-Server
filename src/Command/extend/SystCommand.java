package Command.extend;

import Command.Command;
import FTPServer.Server_Ctrl;

import java.io.BufferedWriter;
import java.io.IOException;

public class SystCommand implements Command {
    @Override
    public void getResult(String data, BufferedWriter writer, Server_Ctrl t) {
        try {
            writer.write("215 Windows_NT\r\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
