package Command;

import FTPServer.Server_Ctrl;

import java.io.*;
import java.net.Socket;

import static utils.Absolute2Position.PathFormat;

public class AppCommand implements Command {
    @Override
    public void getResult(String data, BufferedWriter writer, Server_Ctrl t) {
        try {
            writer.write("125 Data connection already open; Transfer starting.\r\n");// 125 Data connection already open; transfer starting.

            writer.flush();
            Socket datasocket = t.getDatasocket();
            BufferedInputStream dataIn = new BufferedInputStream(datasocket.getInputStream());
            data = PathFormat(t.getCurrentDirectory(), data);//格式化路径
            FileOutputStream fos = new FileOutputStream(t.getCurrentDirectory() + File.separator + data, true);
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = dataIn.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }
            writer.write("226 transfer complete.\r\n");// 226 Closing data connection.
            writer.flush();
            datasocket.close();
            fos.close();
            dataIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
