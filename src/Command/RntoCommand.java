package Command;

import FTPServer.Server_Ctrl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import static utils.Absolute2Position.PathFormat;

public class RntoCommand implements Command {
    @Override
    public void getResult(String data, BufferedWriter writer, Server_Ctrl t) {
        data = PathFormat(t.getCurrentDirectory(), data);//格式化路径
        File file = new File(t.getCurrentDirectory() + File.separator + t.getRenameRegister());
        File newFile = new File(t.getCurrentDirectory() + File.separator + data);
        file.renameTo(newFile);

        try {
            writer.write("250 RNTO Command successful\r\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
