package Command;

import FTPServer.Server_Ctrl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import static utils.Absolute2Position.PathFormat;

public class DeleCommand implements Command {
    @Override
    public void getResult(String data, BufferedWriter writer, Server_Ctrl t) {
        String respond;
        data = PathFormat(t.getCurrentDirectory(), data);//格式化路径
        File file = new File(t.getCurrentDirectory() + File.separator + data);
        if (file.delete()) respond = "250 DELE command successful.\r\n";
        else respond = "550-Access is denied.\r\n ERROR details:  File system denied the access.\r\n 550 End\r\n";
        try {
            writer.write(respond);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
