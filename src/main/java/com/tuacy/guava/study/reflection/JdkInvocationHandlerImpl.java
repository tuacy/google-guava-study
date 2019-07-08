package com.tuacy.guava.study.reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @name: JdkInvocationHandlerImpl
 * @author: tuacy.
 * @date: 2019/7/8.
 * @version: 1.0
 * @Description: JDK版本的newProxyInstance()实现。Proxy.newProxyInstance()
 */
public class JdkInvocationHandlerImpl<T> implements InvocationHandler {

    /**
     * 目标对象
     */
    private T targetObject;

    public JdkInvocationHandlerImpl(T target) {
        this.targetObject = target;
    }

    /**
     * 绑定关系，也就是关联到哪个接口（与具体的实现类绑定）的哪些方法将被调用时，执行invoke方法。
     */
    public AddService getProxy() {
        //该方法用于为指定类装载器、一组接口及调用处理器生成动态代理类实例
        //第一个参数指定产生代理对象的类加载器，需要将其指定为和目标对象同一个类加载器
        //第二个参数要实现和目标对象一样的接口，所以只需要拿到目标对象的实现接口
        //第三个参数表明这些被拦截的方法在被拦截时需要执行哪个InvocationHandler的invoke方法
        //根据传入的目标返回一个代理对象
        return (AddService) Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),
                targetObject.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 打印参数信息列表
        for (Object arg : args) {
            System.out.println(arg);
        }
        Object ret;
        try {
            /*原对象方法调用前处理日志信息*/
            System.out.println("方法开始执行-->>");
            //调用目标方法
            ret = method.invoke(targetObject, args);
            /*原对象方法调用后处理日志信息*/
            System.out.println("方法执行成功-->>");
        } catch (Exception e) {
            // 执行方法过程中出现异常
            e.printStackTrace();
            System.out.println("方法执行失败-->>");
            throw e;
        }
        return ret;
    }
}
