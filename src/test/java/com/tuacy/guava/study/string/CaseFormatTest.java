package com.tuacy.guava.study.string;

import com.google.common.base.CaseFormat;
import org.junit.Test;

/**
 * @name: CaseFormatTest
 * @author: tuacy.
 * @date: 2019/7/5.
 * @version: 1.0
 * @Description: CaseFormat 对应测试类
 */
public class CaseFormatTest {

    @Test
    public void test() {
        // 把字符串“CONSTANT_NAME”转换成"constantName"
        String resultToStr = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "CONSTANT_NAME");
        System.out.println(resultToStr);
    }


}
