package com.tuacy.guava.study.io.morefiles;

import com.google.common.base.Charsets;
import com.google.common.base.Predicate;
import com.google.common.io.CharSource;
import com.google.common.io.CharStreams;
import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MoreFilesTest {

    // MoreFiles.asCharSource()
    @Test
    public void asCharSource() {
        Path path = Paths.get("/home/tuacy/github/google-guava-study/src/main/resources/abc.txt");
        CharSource charSource = MoreFiles.asCharSource(path, Charsets.UTF_8);
        try {
            BufferedReader bufferedReader = charSource.openBufferedStream();
            List<String> lines = CharStreams.readLines(bufferedReader);
            for (String lineItem : lines) {
                System.out.println(lineItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MoreFiles.deleteDirectoryContents() 删除目录里面的文件
    // MoreFiles.deleteRecursively() 删除目录已经目录里面的文件
    @Test
    public void deleteDirectoryContents() {

        Path path = Paths.get("/home/tuacy/github/google-guava-study/src/main/resources/abc");
        try {
            MoreFiles.deleteDirectoryContents(path, RecursiveDeleteOption.ALLOW_INSECURE);
            MoreFiles.deleteDirectoryContents(path, RecursiveDeleteOption.ALLOW_INSECURE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createParentDirectories() {

        Path path = Paths.get("/home/tuacy/github/google-guava-study/src/main/resources/abc/123/789/abc.txt");
        try {
            MoreFiles.createParentDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void isDirectory() {
        Path path = Paths.get("/home/tuacy/github/google-guava-study/src/main/resources");
        Predicate<Path> predicate = MoreFiles.isDirectory();
        System.out.println("是否目录 = " + predicate.apply(path));

    }

}
