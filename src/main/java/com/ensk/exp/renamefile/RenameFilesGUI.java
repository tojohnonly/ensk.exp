package com.ensk.exp.renamefile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RenameFilesGUI {

    public static void main(String[] args) {
        // 主窗体
        final JFrame jFrame = new JFrame("RenameFilesGUI");
        // 主窗体设置大小
        jFrame.setSize(480, 210);
        // 主窗体设置位置
        // f.setLocation(200, 200);
        // 窗口居中
        jFrame.setLocationRelativeTo(null);
        // 窗体大小不可变化
        jFrame.setResizable(false);
        // 主窗体中的组件设置为绝对定位
        jFrame.setLayout(null);
        // 设置背景色
        jFrame.getContentPane().setBackground(Color.WHITE);
        // 输入框组件
        final JTextField jTextField = new JTextField();
        // 设置组件的大小和位置
        jTextField.setBounds(50, 50, 250, 30);
        // 按钮组件
        JButton jButtonOpen = new JButton("Chose Folder");
        // 设置组件的大小和位置
        jButtonOpen.setBounds(300, 50, 120, 30);
        // 按钮组件
        JButton jButtonRename = new JButton("Rename Folder Files");
        // 设置组件的大小和位置
        jButtonRename.setBounds(50, 100, 370, 30);

        // 文件选择器
        final JFileChooser jFileChooser = new JFileChooser();
        // 设置只选择目录
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // 为 jButtonRename 添加鼠标监听事件
        jButtonOpen.addMouseListener(new MouseAdapter() {
            // 鼠标按下起来组合键事件
            public void mouseClicked(MouseEvent e) {
                int returnVal = jFileChooser.showOpenDialog(jFrame);
                File file = jFileChooser.getSelectedFile();
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    // JOptionPane.showMessageDialog(jFrame, "计划打开文件:" + file.getAbsolutePath());
                    jTextField.setText(file.getAbsolutePath());
                }
            }
        });
        // 为 jButtonRename 添加鼠标监听事件
        jButtonRename.addMouseListener(new MouseAdapter() {
            // 鼠标按下起来组合键事件
            public void mouseClicked(MouseEvent e) {
                String folderStr = jTextField.getText().trim();
                if (0 == folderStr.length()) {
                    JOptionPane.showMessageDialog(jFrame, "Bad Folder Path !", "Warning", JOptionPane.WARNING_MESSAGE);
                    jTextField.grabFocus();
                    return;
                }
                File folder = new File(folderStr);
                if (!folder.exists()) {
                    JOptionPane.showMessageDialog(jFrame, "Bad Folder Path !", "Warning", JOptionPane.WARNING_MESSAGE);
                    jTextField.grabFocus();
                    return;
                }
                renameFolderFiles(folder);
            }
        });
        // 把按钮加入到主窗体中
        jFrame.add(jTextField);
        jFrame.add(jButtonOpen);
        jFrame.add(jButtonRename);
        // 关闭窗体的时候 , 退出程序
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 让窗体变得可见
        jFrame.setVisible(true);
    }

    private static void renameFolderFiles(File folder) {
        // 重命名文件夹
        File[] files = folder.listFiles();
        int index = 0;
        for (File file : files) {
            index++;
            long lastModified = file.lastModified();
            Date date = new Date(lastModified);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
            String modifiedDate = dateFormat.format(date);
            // System.out.println(file.getParent());
            File newFile = new File(file.getParent() + File.separator + modifiedDate + "."
                + file.getName().substring(file.getName().lastIndexOf(".") + 1).toLowerCase());
            // 文件存在且不是自己时 , 重命名后面加上序号
            if (newFile.exists() && !(newFile.getName().equals(file.getName()))) {
                newFile = new File(file.getParent() + File.separator + modifiedDate + index + "."
                    + file.getName().substring(file.getName().lastIndexOf(".") + 1).toLowerCase());
            }
            boolean renameSuccess = file.renameTo(newFile);
            System.out.println(renameSuccess + file.getParent() + File.separator + modifiedDate + "."
                + file.getName().substring(file.getName().lastIndexOf(".") + 1) + "\n");
        }
        JOptionPane.showMessageDialog(null, "Success !", "Warning", JOptionPane.WARNING_MESSAGE);
    }
}