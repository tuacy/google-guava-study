package com.tuacy.guava.study.math;

import com.google.common.math.IntMath;
import org.junit.Test;

import java.math.RoundingMode;

/**
 * @name: IntMathTest
 * @author: tuacy.
 * @date: 2019/7/8.
 * @version: 1.0
 * @Description:
 */
public class IntMathTest {

    @Test
    public void ceilingPowerOfTwo() {
        // 返回大于或等于x的最小2的幂。比如x = 5则返回8,x = 3则返回4。这相当于 checkedPow（2，log2（x，CEILING））。
        System.out.println(IntMath.ceilingPowerOfTwo(3));
        // 返回大于或等于x的最小2的幂。比如x = 5则返回4,x = 3则返回2。这相当于 checkedPow（2，log2（x，CEILING））。
        System.out.println(IntMath.floorPowerOfTwo(3));
        // 如果x代表2的幂，则返回true
        System.out.println(IntMath.isPowerOfTwo(0));
        // 返回x的以2为底的对数
        System.out.println(IntMath.log2(8, RoundingMode.DOWN));
        // 返回x的以10为底的对数
        System.out.println(IntMath.log10(10, RoundingMode.DOWN));
        // b的k次幂(b的k次方)
        System.out.println(IntMath.pow(4, 4));
        // 平方根
        System.out.println(IntMath.sqrt(4, RoundingMode.DOWN));
        // 除法
        System.out.println(IntMath.divide(4, 4, RoundingMode.DOWN));
        // 取模 mod(7, 4) == 3、mod(-7, 4) == 1、mod(-1, 4) == 3、mod(-8, 4) == 0、mod(8, 4) == 0
        System.out.println(IntMath.mod(-7, 4));
        // 最大公约数
        System.out.println(IntMath.gcd(4, 4));
        // 在不溢出的前提下返回 a+b
        System.out.println(IntMath.checkedAdd(4, 4));
        // 在不溢出的前提下返回 a-b
        System.out.println(IntMath.checkedSubtract(4, 4));
        // 在不溢出的前提下返回 a*b
        System.out.println(IntMath.checkedMultiply(4, 4));
        // 在不溢出的前提下返回 a的b次幂(a的b次方)
        System.out.println(IntMath.checkedPow(4, 4));
        // 返回a+b 如果溢出返回Integer.MAX_VALUE、如果下溢Integer.MIN_VALUE
        System.out.println(IntMath.saturatedAdd(4, 4));
        // 返回a-b 如果溢出返回Integer.MAX_VALUE、如果下溢Integer.MIN_VALUE
        System.out.println(IntMath.saturatedSubtract(4, 4));
        // 返回a*b 如果溢出返回Integer.MAX_VALUE、如果下溢Integer.MIN_VALUE
        System.out.println(IntMath.saturatedMultiply(4, 4));
        // 返回a的b次幂(a的b次方) 如果溢出返回Integer.MAX_VALUE、如果下溢Integer.MIN_VALUE
        System.out.println(IntMath.saturatedPow(3, 2));
        // 返回 n!
        System.out.println(IntMath.factorial(4));
        // 二项式系数
        System.out.println(IntMath.binomial(4, 4));
        // 算术平均值
        System.out.println(IntMath.mean(4, 4));
        // 是否是素数
        System.out.println(IntMath.isPrime(4));
    }

}
