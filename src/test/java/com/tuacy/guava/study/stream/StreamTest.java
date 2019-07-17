package com.tuacy.guava.study.stream;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @name: StreamTest
 * @author: tuacy.
 * @date: 2019/7/16.
 * @version: 1.0
 * @Description:
 */
public class StreamTest {

    @Test
    public void collectionStream() {

        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
        // 使用List创建一个流对象
        Stream<Integer> stream = list.stream();
        // TODO: 对流对象做处理
    }

    @Test
    public void collectionParallelStream() {

        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
        // 使用List创建一个流对象
        Stream<Integer> stream = list.parallelStream();
        // TODO: 对流对象做处理
    }

    @Test
    public void arrayStream() {

        int[] intArray = new int[10];
        for (int index = 0; index < intArray.length; index++) {
            intArray[index] = index;
        }
        // 使用数组创建一个流对象
        IntStream stream = Arrays.stream(intArray);
        // TODO: 对流对象做处理
    }

    @Test
    public void bufferedReaderStream() {

        File file = new File("/home/tuacy/github/google-guava-study/src/main/resources/application.yml");
        try {
            // 把文件里面的内容一行一行的读出来
            BufferedReader in = new BufferedReader(new FileReader(file));
            // 生成一个Stream对象
            Stream<String> stream = in.lines();
            // TODO: 对流对象做处理
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fileWalkStream() {
        Path path = Paths.get("D:\\job\\git\\google-guava-study\\src\\main\\resources");
        try {
            // 第二个参数用于指定遍历几层
            Stream<Path> stream = Files.walk(path, 2);
            // TODO: 对流对象做处理
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fileLineStream() {
        Path path = Paths.get("D:\\job\\git\\google-guava-study\\src\\main\\resources\\application.yml");
        try {
            // 生成一个Stream对象
            Stream<String> stream = Files.lines(path);
            // TODO: 对流对象做处理
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fileFindStream() {
        Path path = Paths.get("D:\\job\\git\\google-guava-study\\src\\main\\resources");
        try {
            // 找到指定path下的所有不是目录的文件
            Stream<Path> stream = Files.find(path, 2, (path1, basicFileAttributes) -> {
                // 过滤掉目录文件
                return !basicFileAttributes.isDirectory();
            });
            // TODO: 对流对象做处理
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fileListStream() {
        Path path = Paths.get("D:\\job\\git\\google-guava-study\\src\\main\\resources");
        try {
            // 找到指定path下的所有的文件
            Stream<Path> stream = Files.list(path);
            // TODO: 对流对象做处理
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void reduce() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        // 所有的元素相加，在加上20
        Integer reduceValue = stream.reduce(20, new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                System.out.println(integer);
                System.out.println(integer2);
                return integer + integer2;
            }
        });
        System.out.println(reduceValue);
    }

}
