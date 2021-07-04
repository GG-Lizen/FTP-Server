package FTPServer;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class Server_Config {
    private static String rootPath; //= System.getProperty("user.dir") + "\\Data";//指向服务器存放文件位置
    private static HashMap<String, String> users;

    public static String getRootPath() {
        return rootPath;
    }


    public static void init(String rPath, String userPath, boolean isAnonymous) throws IOException {
        rootPath = rPath;
        users = new HashMap<>();
        if (isAnonymous) {
            users.put("anonymous", "");
        } else
            initialAllUsers(userPath);
    }

    public static boolean isnameExist(String name) {
        return users.get(name) != null;
    }

    public static String getpassword(String name) {
        return users.get(name);
    }

    public static void initialAllUsers(final String path) throws IOException {//初始化AllUsers，即读取文件中用户信息
        users.clear();
        Properties properties = new Properties();

        properties.load(new FileInputStream(path));

        users = new HashMap(properties);


    }


}
