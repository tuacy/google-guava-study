package com.tuacy.guava.study.reflection;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.Parameter;
import com.google.common.reflect.TypeToken;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.junit.Test;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @name: InvokableTest
 * @author: tuacy.
 * @date: 2019/7/6.
 * @version: 1.0
 * @Description:
 */
public class InvokableTest {

    @Test
    public void invokableTest() {
        // 对象
        InvokableInstance object = new InvokableInstance(10);
        // 获去对象对应的类
        Class clazz = object.getClass();
        Method[] invokableSourceList = clazz.getMethods();
//        Constructor[] invokableSourceList =  clazz.getConstructors();
        if (invokableSourceList.length > 0) {
            for (Method item : invokableSourceList) {
                System.out.println("========================================");
                // 把Method包装成Invokable
                Invokable methodInvokable = Invokable.from(item);
                // 方法名字
                System.out.println("方法名字：" + methodInvokable.getName());
                // getDeclaringClass() 获取定义该方法的类
                System.out.println("定义该方法的类：" + methodInvokable.getDeclaringClass());
                // getOwnerType() 获取定义该方法的class的包装对象Type
                System.out.println("定义该方法的类：" + methodInvokable.getOwnerType().getType());
                // isOverridable() 该方法是否可以重写
                System.out.println("是否可以重写：" + methodInvokable.isOverridable());
                // isVarArgs() 该方法是否可变参数
                System.out.println("是否可变参数：" + methodInvokable.isVarArgs());
                // getReturnType() 该方法返回值类型
                System.out.println("返回值类型：" + methodInvokable.getReturnType().getType());
                // getParameters() 获取参数
                ImmutableList<Parameter> parameterList = methodInvokable.getParameters();
                for (int index = 0; index < parameterList.size(); index++) {
                    System.out.println("方法参数" + index + ": " + parameterList.get(index).getType());
                }
                // getExceptionTypes() 获取异常类
                ImmutableList<TypeToken> exceptionList = methodInvokable.getExceptionTypes();
                for (int index = 0; index < exceptionList.size(); index++) {
                    System.out.println("异常类" + index + ": " + exceptionList.get(index).getType());
                }
                // getAnnotatedReturnType()
                AnnotatedType annotatedType = methodInvokable.getAnnotatedReturnType();
                System.out.println("annotatedType: " + annotatedType.getType());
            }
        }
    }

    /**
     * 一个测试用类
     */
    class InvokableInstance {

        /**
         * 构造函数
         */
        public InvokableInstance(int a) {

        }

        /**
         * 仅仅是用来测试的一个方法
         */
        @CanIgnoreReturnValue
        public void sum(int a, int b) throws NullPointerException {
//            return a + b;
        }

    }

}
