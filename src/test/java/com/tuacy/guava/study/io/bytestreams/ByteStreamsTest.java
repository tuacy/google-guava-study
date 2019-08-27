package com.tuacy.guava.study.io.bytestreams;

import com.google.common.io.ByteStreams;
import com.google.common.io.Resources;
import org.junit.Test;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;

public class ByteStreamsTest {

    @Test
    public void byteTest() {

        byte[] ivalue = new byte[1];
        ivalue[0] = (byte) 170;
        System.out.println(ivalue[0] == (byte)0xaa);
//        String binaryString = Integer.toBinaryString(ivalue);
        System.out.println(new BigInteger(1, ivalue).toString(16));

//        System.out.println(0b10101010);

    }


    // ByteStreams.copy()方法,数据拷贝
    @Test
    public void copy() {
        URL url = Resources.getResource("application.yml");
        File f = new File(url.getFile());    // 声明File对象

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (inputStream == null) {
            return;
        }

        try {
            OutputStream outputStream = new FileOutputStream("/home/tuacy/github/google-guava-study/src/main/resources" + File.separator + "abc.txt");
            // 把InputStream里面的内容写入到OutputStream里面去
            ByteStreams.copy(inputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ByteStreams.toByteArray()方法，把InputStream里面的数据读到数组里面去
    @Test
    public void toByteArray() {
        URL url = Resources.getResource("application.yml");
        File f = new File(url.getFile());    // 声明File对象
        // InputStream
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (inputStream == null) {
            return;
        }
        try {
            // InputStream里面的内容读到byte数组里面去
            byte[] byteArrary = ByteStreams.toByteArray(inputStream);
            System.out.println(new String(byteArrary));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // ByteStreams.read() 把
    @Test
    public void read() {
        URL url = Resources.getResource("application.yml");
        File f = new File(url.getFile());    // 声明File对象
        // InputStream
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (inputStream == null) {
            return;
        }
        try {
            byte[] byteArray = new byte[1024];
            int readLength = ByteStreams.read(inputStream, byteArray, 0, 1024);
            System.out.println("读取都的数据长度 = " + readLength);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
