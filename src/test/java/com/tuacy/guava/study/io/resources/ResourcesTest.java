package com.tuacy.guava.study.io.resources;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.LineProcessor;
import com.google.common.io.Resources;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @name: ResourcesTest
 * @author: tuacy.
 * @date: 2019/7/11.
 * @version: 1.0
 * @Description:
 */
public class ResourcesTest {

    // Resources.getResource()
    @Test
    public void getResource() {
        System.out.println(Resources.getResource("application.yml"));
        // 起始路径不一样
        System.out.println(Resources.getResource(ResourcesTest.class, "ResourcesTest.class"));
    }

    // Resources.readLines()
    @Test
    public void readLines() {

        // 我们把application.yml文件的内容读取出来
        URL url = Resources.getResource("application.yml");
        try {
            // Resources.readLines
            List<String> lineList = Resources.readLines(url, Charsets.UTF_8);
            for (String lineItem : lineList) {
                System.out.println(lineItem);
            }
            // Resources.readLines +
            List<String> lineList2 = Resources.readLines(url, Charsets.UTF_8, new LineProcessor<List<String>>() {
                List<String> lines = Lists.newArrayList();
                @Override
                public boolean processLine(String line) throws IOException {
                    lines.add(line);
                    return true;
                }

                @Override
                public List<String> getResult() {
                    return lines;
                }
            });
            for (String lineItem : lineList2) {
                System.out.println(lineItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
