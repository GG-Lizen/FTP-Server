package Command;

import FTPServer.Server_Config;
import FTPServer.Server_Ctrl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class CwdCommand implements Command {
    @Override
    public void getResult(String data, BufferedWriter writer, Server_Ctrl t) {
        File file;
        String respond;
        //判断是否是绝对路径
        if (data.indexOf(":") != -1) {
            data = data.replace("/", "\\");//适配手机
            file = new File(data);
            if (file.exists() && file.isDirectory()) {

                t.setCurrentDirectory(data);
                respond = "250 CWD command successful.\r\n";// 250 Requested file action okay, completed.
            } else {
                respond = "550 directory does not exist.\r\n";// 550 Requested action not taken.
            }
        } else {
            file = new File(t.getCurrentDirectory() + File.separator + data);

            //特殊符号处理
            if (data.equals("\\") || data.equals("/")) {//CWD \ 回到根目录 // \ linux (为了在手机上使用)
                t.setCurrentDirectory(Server_Config.getRootPath());
                respond = "250 CWD command successful.\r\n";
            } else if (data.startsWith(".") && data.split(".").length == 0) {
                if (data.equals("..")) {
                    if (file.getParent().equals(Server_Config.getRootPath())) {
                        t.setCurrentDirectory(Server_Config.getRootPath());
                    } else {
                        File temp = new File(file.getParent());
                        t.setCurrentDirectory(temp.getParent());
                    }
                }
                respond = "250 CWD command successful.\r\n";
            } else if (file.exists() && file.isDirectory()) {
                t.setCurrentDirectory(t.getCurrentDirectory() + File.separator + data);
                respond = "250 CWD command successful.\r\n";
            } else {
                respond = "550 directory does not exist.\r\n";
            }
        }
        System.out.println(Thread.currentThread() + "  主机:" + t.getSocket().getInetAddress().getHostAddress() + " " + t.getCurrentDirectory());
        try {
            writer.write(respond);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
