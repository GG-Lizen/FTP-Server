package Command;

import FTPServer.Server_Ctrl;

import java.io.*;
import java.net.Socket;

import static utils.Absolute2Position.PathFormat;

public class RetrCommand implements Command {
    @Override
    public void getResult(String data, BufferedWriter writer, Server_Ctrl t) {
        data = PathFormat(t.getCurrentDirectory(), data);//格式化路径
        File file = new File(t.getCurrentDirectory() + File.separator + data);
        if (file.exists() && !file.isDirectory()) {
            try {

                writer.write("125 Data connection already open; Transfer starting.\r\n");
                writer.flush();
                Socket datasocket = t.getDatasocket();
                BufferedOutputStream dataOut = new BufferedOutputStream(datasocket.getOutputStream());

                byte[] buf = new byte[1024];
                InputStream is = new FileInputStream(file);
                int len;

                while ((len = is.read(buf)) != -1) {
                    dataOut.write(buf, 0, len);
                    System.out.println(len);

                }
                dataOut.flush();
                datasocket.close();
                is.close();
                writer.write("220 transfer complete...\r\n");
                writer.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            try {
                writer.write("553 File name not allowed\r\n"); //553 Requested action not taken.File name not allowed.
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
