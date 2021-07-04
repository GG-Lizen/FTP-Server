package Command;

import FTPServer.Server_Ctrl;

import java.io.BufferedWriter;

public interface Command {

    /**
     * @param data   从ftp客户端接收的除ftp命令之外的数据
     * @param writer 网络输出流
     * @param t      控制连接所对应的处理线程
     */
    public void getResult(String data, BufferedWriter writer, Server_Ctrl t);

}