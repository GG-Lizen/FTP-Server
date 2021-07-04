package Command;

import FTPServer.Server_Config;
import FTPServer.Server_Ctrl;

import java.io.BufferedWriter;
import java.io.IOException;

public class UserCommand implements Command {
    @Override
    public void getResult(String data, BufferedWriter writer, Server_Ctrl Sp) {
        String respond = "";
        //匿名用户


        if (Server_Config.isnameExist(data)) {
            respond = "331 please enter your password.";// 331 User name okay, need password.
            Sp.getUSER().set(data);
        } else {
            respond = "501 Invalid user name.";//  Syntax error in parameters or arguments.
        }
        try {
            writer.write(respond);
            writer.write("\r\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
