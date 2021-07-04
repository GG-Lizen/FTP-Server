package Command;

import FTPServer.Server_Config;
import FTPServer.Server_Ctrl;

import java.io.BufferedWriter;
import java.io.IOException;

public class PassCommand implements Command {
    @Override
    public void getResult(String data, BufferedWriter writer, Server_Ctrl t) {
        String respond = "";
        if (data.equals(Server_Config.getpassword(t.getUSER().get()))) {
            System.out.println(Thread.currentThread() + "  主机:" + t.getSocket().getInetAddress().getHostAddress() + " " + "登录成功");
            t.setLogin(true);
            respond = "230 User " + "anonymous" + " logged in";// 230 User logged in, proceed.

        } else {
            System.out.println(Thread.currentThread() + "  主机:" + t.getSocket().getInetAddress().getHostAddress() + " " + "登录失败，密码错误");
            respond = "530   Wrong password.";
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
