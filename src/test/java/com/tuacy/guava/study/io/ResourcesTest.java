package com.tuacy.guava.study.io;

import com.google.common.io.Resources;
import org.junit.Test;

/**
 * @name: ResourcesTest
 * @author: tuacy.
 * @date: 2019/7/11.
 * @version: 1.0
 * @Description:
 */
public class ResourcesTest {

    @Test
    public void getResourceTest() {
        System.out.println(Resources.getResource("application.yml"));

        System.out.println(Resources.getResource(ResourcesTest.class, "./application.yml"));
    }

}
