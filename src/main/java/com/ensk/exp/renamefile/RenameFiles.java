package com.ensk.exp.renamefile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RenameFiles {

    public static void main(String[] args) {

        File srcDir = new File("E:/Photos/Life/2019");
        File[] files = srcDir.listFiles();
        Integer index = 0;
        for (File file : files) {
            index++;
            Long lastModified = file.lastModified();
            Date date = new Date(lastModified);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
            String modifiedDate = dateFormat.format(date);
            System.out.println(file.getParent());
            File newFile = new File(file.getParent() + File.separator + modifiedDate + "."
                + file.getName().substring(file.getName().lastIndexOf(".") + 1).toLowerCase());
            // 文件存在且不是自己时 , 重命名后面加上序号
            if (newFile.exists() && !(newFile.getName().equals(file.getName()))) {
                newFile = new File(file.getParent() + File.separator + modifiedDate + index + "."
                    + file.getName().substring(file.getName().lastIndexOf(".") + 1).toLowerCase());
            }
            boolean renameSuccess = file.renameTo(newFile);
            System.out.println(renameSuccess + file.getParent() + File.separator + modifiedDate + "."
                + file.getName().substring(file.getName().lastIndexOf(".") + 1));
        }

    }

}
