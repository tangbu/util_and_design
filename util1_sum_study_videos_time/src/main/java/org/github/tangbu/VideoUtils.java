package org.github.tangbu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tangbu
 */
public class VideoUtils {
    public static void printTime(long millisecond) {
        int totalSecond = (int) (millisecond / 1000);

        int hours = totalSecond / 3600;
        int minuteNum = (totalSecond - hours * 3600) / 60;
        int secondNum = totalSecond % 60;
        System.out.println("总时长是:" + hours + "时" + minuteNum + "分" + secondNum + "秒");

    }

    public static List<File> getFileList(File dir) {
        List<File> files = new ArrayList<>();
        //获取指定文件夹下的所有文件
        File[] fis = dir.listFiles();
        if (fis != null) {
            for (File fi : fis) {
                if (fi.isDirectory()) {
                    files.addAll(getFileList(fi));
                } else {
                    files.add(fi);
                }
            }
        }
        return files;
    }

}
