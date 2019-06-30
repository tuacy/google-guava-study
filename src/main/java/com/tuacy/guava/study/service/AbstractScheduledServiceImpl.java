package com.tuacy.guava.study.service;

import com.google.common.util.concurrent.AbstractScheduledService;

import java.util.concurrent.TimeUnit;

/**
 * @name: AbstractScheduledServiceImpl
 * @author: tuacy.
 * @date: 2019/6/29.
 * @version: 1.0
 * @Description:
 */
public class AbstractScheduledServiceImpl extends AbstractScheduledService {


	@Override
	protected void startUp() throws Exception {
		//TODO: 做一些初始化操作
	}

	@Override
	protected void shutDown() throws Exception {
		//TODO: 可以做一些清理操作，比如关闭连接啥的。shutDown() 是在线程的具体实现里面调用的
	}

	@Override
	protected void runOneIteration() throws Exception {
		// 每次周期任务的执行逻辑
		try {
			System.out.println("do work....");
		} catch (Exception e) {
			//TODO: 处理异常，这里如果抛出异常，会使服务状态变为failed同时导致任务终止。
		}
	}

	@Override
	protected Scheduler scheduler() {
		// 5s执行一次的Scheduler
		return Scheduler.newFixedDelaySchedule(1, 5, TimeUnit.SECONDS);
	}
}
