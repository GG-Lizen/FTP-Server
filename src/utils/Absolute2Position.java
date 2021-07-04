package utils;

public class Absolute2Position {
    public static String PathFormat(String rootPath, String absolutePath) {
        absolutePath = absolutePath.replace("/", "\\");//适配手机
        return absolutePath.replace(rootPath + "\\", "");//转为绝对路径
    }
}
