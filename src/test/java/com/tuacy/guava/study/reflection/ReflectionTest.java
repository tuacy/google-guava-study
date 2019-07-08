package com.tuacy.guava.study.reflection;

import com.google.common.reflect.Reflection;
import org.junit.Assert;
import org.junit.Test;

/**
 * @name: ReflectionTest
 * @author: tuacy.
 * @date: 2019/7/6.
 * @version: 1.0
 * @Description:
 */
public class ReflectionTest {

    @Test
    public void packageNameTest() {
        // 获取ReflectTest类对应的包名字
        System.out.println(Reflection.getPackageName(ReflectTest.class));
        System.out.println(Reflection.getPackageName("com.tuacy.guava.study.reflection.ReflectionTest$ReflectTest"));
    }

    @Test
    public void guavaNewProxyTest() {
        AddServiceImpl service = new AddServiceImpl();
        GuavaInvocationHandlerImpl<AddService> addServiceHandler = new GuavaInvocationHandlerImpl<>(AddService.class, service);
        AddService proxy = addServiceHandler.getProxy();
        Assert.assertEquals(3, proxy.add(1, 2));
    }

    @Test
    public void guavaNewProxyTest1() {
        AddServiceImpl service = new AddServiceImpl();
        GuavaAbstractInvocationHandlerImpl<AddService> addServiceHandler = new GuavaAbstractInvocationHandlerImpl<>(AddService.class, service);
        AddService proxy = addServiceHandler.getProxy();
        Assert.assertEquals(3, proxy.add(1, 2));
    }

    @Test
    public void jdkNewProxyTest() {
        AddServiceImpl service = new AddServiceImpl();
        JdkInvocationHandlerImpl<AddService> addServiceHandler = new JdkInvocationHandlerImpl<>(service);
        AddService proxy = addServiceHandler.getProxy();
        Assert.assertEquals(3, proxy.add(1, 2));
    }

    static class ReflectTest {

    }

}
