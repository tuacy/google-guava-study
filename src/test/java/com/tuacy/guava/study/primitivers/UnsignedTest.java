package com.tuacy.guava.study.primitivers;

import com.google.common.primitives.UnsignedLong;
import org.junit.Test;

/**
 * @name: UnsignedTest
 * @author: tuacy.
 * @date: 2019/7/6.
 * @version: 1.0
 * @Description:
 */
public class UnsignedTest {

    @Test
    public void unsignedIntegerTest() {

        UnsignedLong unsignedInteger = UnsignedLong.valueOf("11111111111111111111111111111111", 2);
        System.out.println("value = " + unsignedInteger.longValue());

        Integer integer = Integer.parseUnsignedInt("01111111111111111111111111111111", 2);
        System.out.println("unsigned int = " + integer.intValue());

    }

}
