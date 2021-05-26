package org.github.tangbu;

import it.sauronsoftware.jave.Encoder;

import java.io.File;
import java.util.List;

/**
 * @author tangbu
 */
public class TestClass {
    public static void main(String[] args) {
        List<File> allFiles = VideoUtils.getFileList(new File("/home/tangbu/Desktop/ningdun/学习资料/慕课网"));
        long totalMillisecond = getTotalMillisecond(allFiles);
        VideoUtils.printTime(totalMillisecond);

    }

    public static long getTotalMillisecond(List<File> files) {
        long ls = 0;
        for (File file : files) {
            Encoder encoder = new Encoder();
            try {
                it.sauronsoftware.jave.MultimediaInfo m = encoder.getInfo(file);
                ls = ls + m.getDuration();
            } catch (Exception e) {
                System.out.println("文件获取时长出错:" + file.getName() + "  处理:" + "跳过获取下一个时间");
            }
        }
        return ls;
    }
}
