package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Ping {
    public static int ping(String pingStr) {
        BufferedReader br;
        int ttl = 0;
        try {
            Process p = Runtime.getRuntime().exec("ping  " + pingStr.replace("/", ""));//调用cmd
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            int index = sb.indexOf("TTL=");
            ttl = Integer.parseInt(sb.substring(index + 4, index + 7).replace("\n", ""));
            System.out.println("TTL=" + ttl);
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ttl;
    }

//    public static void main(String[] args) {
//        new Ping().ping("/192.168.1.101");
//    }
}

