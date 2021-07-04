package UI;

//监听器类：


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ButtonListener implements ActionListener {

    private MainUI m;
    private JTextField msgTextArea;
    private int mode;

    public ButtonListener(MainUI m, JTextField msgTextArea, int mode) {
        super();
        this.m = m;
        this.msgTextArea = msgTextArea;
        this.mode = mode;
    }


    public void actionPerformed(ActionEvent e) {
        showFileOpenDialog(m, msgTextArea, mode);

    }

    public void showFileOpenDialog(Component parent, JTextField msgTextArea, int mode) {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("."));//设置默认显示为当前文件夹
        fc.setFileSelectionMode(mode);//设置选择模式（只选文件、只选文件夹、文件和文件均可选）
        fc.setMultiSelectionEnabled(false);//是否允许多选
        int result = fc.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();//获取打开的文件
            msgTextArea.setText(file.getAbsolutePath());
        }
    }
}

