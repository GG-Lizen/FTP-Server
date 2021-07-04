package UI;

import FTPServer.FTPServer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainUI extends JFrame {
    JFrame frame = new JFrame("FTP站点");
    JTextField userField;
    JTextField diretoryField;
    JRadioButton typeRB2;
    JButton button1;
    int WIDTH = 600;
    int HEIGHT = 700;

    public void init() {
        //主窗口
        double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        frame.setLocation(new Point((int) (lx / 2) - WIDTH / 2, (int) (ly / 2) - HEIGHT / 2));//设定窗口出现位置
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//使能关闭窗口，结束程序
        Box top = Box.createVerticalBox();
        //title
        Box titleB = Box.createHorizontalBox();
        JLabel titleL = new JLabel("站点信息");
        titleL.setFont(new Font("Dialog", Font.BOLD, 28));
        titleL.add(Box.createHorizontalStrut(50));
        titleB.add(titleL);

        // user type
        Box userB = Box.createVerticalBox();
        Box typeRBB = Box.createVerticalBox();
        JRadioButton typeRB1 = new JRadioButton("匿名用户", true);
        typeRB2 = new JRadioButton("指定用户");
        ButtonGroup userBG = new ButtonGroup();
        userBG.add(typeRB1);
        userBG.add(typeRB2);
        typeRBB.add(typeRB1);
        typeRBB.add(typeRB2);
        userB.add(typeRBB);
        //选中指定用户则用户文件输入可写
        typeRB1.addActionListener(e -> {
            userField.setEditable(false);
            button1.setEnabled(false);
        });

        //选中指定用户则用户文件输入可写
        typeRB2.addActionListener(e -> {
            userField.setEditable(true);
            button1.setEnabled(true);
        });

        Box T = Box.createVerticalBox();//directory select
        Box userBox = Box.createHorizontalBox();
        JLabel userLabel = new JLabel("用户文件: ");
        userField = new JTextField(15);
        userField.setEditable(false);
        userBox.setSize(100, 30);
        button1 = new JButton(". . .");
        button1.setEnabled(false);
        userBox.add(Box.createHorizontalStrut(20));
        userBox.add(userLabel);
        userBox.add(Box.createHorizontalStrut(20));
        userBox.add(userField);
        userBox.add(Box.createHorizontalStrut(20));
        userBox.add(button1);
        userBox.add(Box.createHorizontalStrut(20));
        T.add(userBox);
        ButtonListener BL1 = new ButtonListener(this, userField, JFileChooser.FILES_ONLY);
        button1.addActionListener(BL1);

        Box T2 = Box.createVerticalBox();
        Box diretoryBox = Box.createHorizontalBox();
        JLabel diretoryLabel = new JLabel("目录文件: ");
        diretoryField = new JTextField(15);
        diretoryBox.setSize(100, 30);
        JButton button2 = new JButton(". . .");
        diretoryBox.add(Box.createHorizontalStrut(20));
        diretoryBox.add(diretoryLabel);
        diretoryBox.add(Box.createHorizontalStrut(20));
        diretoryBox.add(diretoryField);
        diretoryBox.add(Box.createHorizontalStrut(20));
        diretoryBox.add(button2);
        diretoryBox.add(Box.createHorizontalStrut(20));
        T2.add(diretoryBox);
        ButtonListener BL2 = new ButtonListener(this, diretoryField, JFileChooser.DIRECTORIES_ONLY);
        button2.addActionListener(BL2);


        //start button
        JButton start = new JButton("启动");
        start.addActionListener(e -> {

            String userpath = userField.getText();
            String directory = diretoryField.getText();
            boolean isanonymous = false;
            if (userpath.equals("")) {
                if (typeRB2.isSelected()) {
                    JOptionPane.showMessageDialog(frame, "用户文件不能为空");
                    return;
                }
                isanonymous = true;

            } else {
                String[] strs = userpath.split("\\.");//获取后缀名 注意转义
                if (strs.length == 0 || !strs[strs.length - 1].equals("properties")) {
                    JOptionPane.showMessageDialog(frame, "请检查用户文件是否正确");
                    return;
                }

            }

            if (directory.equals("")) {
                JOptionPane.showMessageDialog(frame, "请确认目录文件输入是否正确");
            } else {
                try {
                    frame.setVisible(false);
                    System.out.println("=====服务器已启动======");
                    new FTPServer(directory, userpath, isanonymous).listen();

                } catch (IOException ioException) {
                    frame.setVisible(true);
                    if (ioException.getMessage().equals("Address already in use: bind")) {
                        JOptionPane.showMessageDialog(frame, "端口被占用");
                    }
                    //窗口占用判断
                    else JOptionPane.showMessageDialog(frame, "请确认用户文件输入是否正确");
                }
            }


        });
        //build up
        top.add(Box.createVerticalStrut(30));
        top.add(titleB);
        top.add(Box.createVerticalStrut(50));
        top.add(userB);
        top.add(Box.createVerticalStrut(50));
        top.add(T);
        top.add(Box.createVerticalStrut(50));
        top.add(T2);
        top.add(Box.createVerticalStrut(50));
        top.add(start);
        top.add(Box.createVerticalStrut(50));
        frame.add(top);


        frame.setVisible(true);
    }


    public static void main(String[] args) {
        MainUI UI = new MainUI();
        UI.init();
    }
}