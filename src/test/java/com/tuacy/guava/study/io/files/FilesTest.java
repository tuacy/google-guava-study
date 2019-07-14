package com.tuacy.guava.study.io.files;

import com.google.common.base.Charsets;
import com.google.common.graph.Traverser;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.*;
import org.junit.Test;

import java.io.*;
import java.util.List;
import java.util.function.Consumer;

public class FilesTest {

    // Files.newReader() 把文件的内容读到BufferedReader里面去
    @Test
    public void newReader() {
        //　这里，需要换成你电脑存在的地址
        File file = new File("/home/tuacy/github/google-guava-study/src/main/resources" + File.separator + "application.yml");
        try {
            BufferedReader bufferedReader = Files.newReader(file, Charsets.UTF_8);
            List<String> lineList = CharStreams.readLines(bufferedReader);
            for (String lineItem : lineList) {
                System.out.println(lineItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Files.newWriter
    @Test
    public void newWriter() {
        File file = new File("/home/tuacy/github/google-guava-study/src/main/resources" + File.separator + "filewirite.txt");
        try {
            BufferedWriter bufferedWriter = Files.newWriter(file, Charsets.UTF_8);
            bufferedWriter.write("hello word!!!");
//            bufferedWriter.flush();
            Flushables.flushQuietly(bufferedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Files.asByteSink
    @Test
    public void asByteSink() {
        File file = new File("/home/tuacy/github/google-guava-study/src/main/resources" + File.separator + "filewirite.txt");
        try {
            ByteSink byteSink = Files.asByteSink(file, FileWriteMode.APPEND);
            OutputStream outputStream = byteSink.openStream();
            outputStream.write("hello word!!!".getBytes(Charsets.UTF_8));
//            bufferedWriter.flush();
            Flushables.flushQuietly(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 对文件做hash操作
    @Test
    public void hash() {
        File file = new File("/home/tuacy/github/google-guava-study/src/main/resources" + File.separator + "filewirite.txt");
        try {
            HashCode hashCode = Files.asByteSource(file).hash(Hashing.sha256());
            System.out.println(hashCode.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Files.fileTraverser() 用于遍历文件
    @Test
    public void fileTraverser() {
        Traverser<File> traverser = Files.fileTraverser();
        File file = new File("/home/tuacy/github/google-guava-study/src/main/resources");
        Iterable<File> list = traverser.breadthFirst(file);
        list.forEach(new Consumer<File>() {

            @Override
            public void accept(File file) {
                System.out.println(file.getName());
            }
        });
    }

}
