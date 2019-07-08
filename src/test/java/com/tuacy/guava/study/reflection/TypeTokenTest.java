package com.tuacy.guava.study.reflection;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @name: TypeTokenTest
 * @author: tuacy.
 * @date: 2019/7/6.
 * @version: 1.0
 * @Description:
 */
public class TypeTokenTest {

    @Test
    public void test() {
        ArrayList<String> stringList = Lists.newArrayList();
        ArrayList<Integer> intList = Lists.newArrayList();
        System.out.println("intList type is " + intList.getClass());
        System.out.println("stringList type is " + stringList.getClass());


        // 认为stringList和intList的类型是一样的。这就是所谓的泛型类型擦除, 泛型String和Integer被檫除了。
        System.out.println(stringList.getClass().isAssignableFrom(intList.getClass()));

        // 定义了一个空的匿名类
        TypeToken<ArrayList<String>> typeToken = new TypeToken<ArrayList<String>>() {
        };
        // TypeToken解析出了泛型参数的具体类型
        TypeToken<?> genericTypeToken = typeToken.resolveType(ArrayList.class.getTypeParameters()[0]);
        System.out.println(genericTypeToken.getType());

    }

}
