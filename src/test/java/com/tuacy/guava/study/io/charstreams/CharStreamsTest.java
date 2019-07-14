package com.tuacy.guava.study.io.charstreams;

import com.google.common.io.CharStreams;
import com.google.common.io.LineProcessor;
import com.google.common.io.Resources;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.util.List;

public class CharStreamsTest {

    // CharStreams.copy() 字符流拷贝
    @Test
    public void copy() {
        URL url = Resources.getResource("application.yml");
        File f = new File(url.getFile());    // 声明File对象
        try {
            BufferedReader in = new BufferedReader(new FileReader(f));
            StringBuilder stringBuilder = new StringBuilder();
            CharStreams.copy(in, stringBuilder);
            System.out.println(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // CharStreams.readLines() 一行，一行的读取数据
    @Test
    public void readLines() {
        URL url = Resources.getResource("application.yml");
        File f = new File(url.getFile());    // 声明File对象
        try {
            BufferedReader in = new BufferedReader(new FileReader(f));
            List<String> lineList = CharStreams.readLines(in);
            for (String lineItem : lineList) {
                System.out.println(lineItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // CharStreams.readLines(),并且交给LineProcessor处理
    @Test
    public void readLines2() {
        URL url = Resources.getResource("application.yml");
        File f = new File(url.getFile());    // 声明File对象
        try {
            BufferedReader in = new BufferedReader(new FileReader(f));
            List<String> lineList = CharStreams.readLines(in, new LineProcessor<List<String>>() {
                List<String> resultList = Lists.newArrayList();
                @Override
                public boolean processLine(String line) throws IOException {
                    resultList.add(line);
                    return true;
                }

                @Override
                public List<String> getResult() {
                    return resultList;
                }
            });
            // 打印结果
            for (String lineItem : lineList) {
                System.out.println(lineItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
