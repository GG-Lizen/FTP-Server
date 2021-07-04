package Command;

import FTPServer.Server_Ctrl;

import java.io.*;
import java.net.Socket;
import java.util.Random;

import static utils.Absolute2Position.PathFormat;

public class StoreCommand implements Command {
    @Override
    public void getResult(String data, BufferedWriter writer, Server_Ctrl t) {

        try {
            writer.write("125 Data connection already open; Transfer starting.\r\n");
            writer.flush();
            Socket datasocket = t.getDatasocket();
            BufferedInputStream dataIn = new BufferedInputStream(datasocket.getInputStream());
            data = PathFormat(t.getCurrentDirectory(), data);//格式化路径
            FileOutputStream fos = new FileOutputStream(t.getCurrentDirectory() + File.separator + data);
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = dataIn.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }
            writer.write("226 transfer complete.\r\n");
            writer.flush();
            datasocket.close();
            fos.close();
            dataIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String addUniqueID(String filename) {
        String[] spilt = filename.split("\\.");
        String timeStamp = Integer.toString((int) (System.currentTimeMillis() / 1000) * new Random().nextInt(65535));
        StringBuffer id = new StringBuffer();
        for (int i = 0; i < spilt.length - 1; i++) {
            id.append(spilt[i]);
        }
        id.append(timeStamp);
        id.append(".");
        id.append(spilt[spilt.length - 1]);
        return id.toString();
    }
}
