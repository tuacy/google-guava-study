package com.tuacy.guava.study.service;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.ServiceManager;
import com.google.common.util.concurrent.Uninterruptibles;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @name: AbstractExecutionThreadServiceImplTest
 * @author: tuacy.
 * @date: 2019/6/29.
 * @version: 1.0
 * @Description:
 */
public class ServiceManagerTest {

    @Test
    public void serviceManagerTest() {
        // 定义两个服务
        AbstractExecutionThreadServiceImpl service0 = new AbstractExecutionThreadServiceImpl();
        AbstractScheduledServiceImpl service1 = new AbstractScheduledServiceImpl();
        List<Service> serviceList = Lists.newArrayList(service0, service1);
        // ServiceManager里面管理这两个服务
        ServiceManager serviceManager = new ServiceManager(serviceList);
        // 添加监听
        serviceManager.addListener(new ServiceManager.Listener() {
            @Override
            public void healthy() {
                super.healthy();
                System.out.println("healthy");
            }

            @Override
            public void stopped() {
                super.stopped();
                System.out.println("stopped");
            }

            @Override
            public void failure(Service service) {
                super.failure(service);
                System.out.println("failure");
            }
        });
        // 启动服务，等待所有的服务都达到running状态
        serviceManager.startAsync().awaitHealthy();
        // 等待30s
        Uninterruptibles.sleepUninterruptibly(30, TimeUnit.SECONDS);
        // 停止服务
        serviceManager.stopAsync().awaitStopped();
    }

}
