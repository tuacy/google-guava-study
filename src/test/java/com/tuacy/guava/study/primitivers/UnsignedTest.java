package com.tuacy.guava.study.primitivers;

import com.google.common.primitives.UnsignedInteger;
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

        UnsignedInteger unsignedInteger = UnsignedInteger.fromIntBits(-10);

        System.out.println("value = " + unsignedInteger.intValue());

        Integer integer = Integer.parseUnsignedInt("2147483648", 10);
        System.out.println("unsigned int = " + integer.intValue());

    }

}
