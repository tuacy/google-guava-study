package com.tuacy.guava.study.reflection;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import org.junit.Test;

import java.io.IOException;

/**
 * @name: ClassPathTest
 * @author: tuacy.
 * @date: 2019/7/8.
 * @version: 1.0
 * @Description: ClassPath测试类
 */
public class ClassPathTest {


    @Test
    public void classPathTest() {
        try {
            ClassPath classpath = ClassPath.from(ClassPathBase.class.getClassLoader());
            // getTopLevelClassesRecursive
            ImmutableSet<ClassPath.ClassInfo> topClassList =  classpath.getTopLevelClassesRecursive("com.tuacy.guava.study");
            if (topClassList != null && !topClassList.isEmpty()) {
                for (ClassPath.ClassInfo classInfoItem : topClassList) {
                    System.out.println(classInfoItem.toString());
                }
            }
            // getResources
            ImmutableSet<ClassPath.ResourceInfo> resourceList =  classpath.getResources();
            if (resourceList != null && !resourceList.isEmpty()) {
                for (ClassPath.ResourceInfo resourceItem : resourceList) {
                    System.out.println(resourceItem.url().toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClassPathBase {

    }

}
