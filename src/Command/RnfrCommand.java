package Command;

import FTPServer.Server_Ctrl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import static utils.Absolute2Position.PathFormat;

public class RnfrCommand implements Command {
    @Override
    public void getResult(String data, BufferedWriter writer, Server_Ctrl t) {
        data = PathFormat(t.getCurrentDirectory(), data);//格式化路径
        File file = new File(t.getCurrentDirectory() + File.separator + data);
        String respond = "350 \r\n";
        if (!file.exists()) {
            respond = "550 file not exist\r\n";
        } else {
            t.setRenameRegister(data);
        }

        try {
            writer.write(respond);
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
