package com.tuacy.guava.study.service;

import com.google.common.util.concurrent.AbstractExecutionThreadService;
import com.google.common.util.concurrent.Uninterruptibles;

import java.util.concurrent.TimeUnit;

/**
 * @name: AbstractExecutionThreadServiceImpl
 * @author: tuacy.
 * @date: 2019/6/29.
 * @version: 1.0
 * @Description:
 */
public class AbstractExecutionThreadServiceImpl extends AbstractExecutionThreadService {

	private volatile boolean running = true; //声明一个状态

	@Override
	protected void startUp() {
		//TODO: 做一些初始化操作
	}

	@Override
	public void run() {
		// 具体需要实现的业务逻辑，会在线程中执行
		while (running) {
			try {
				// 等待2s
				Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
				System.out.println("do our work.....");
			} catch (Exception e) {
				//TODO: 处理异常，这里如果抛出异常，会使服务状态变为failed同时导致任务终止。
			}
		}
	}

	@Override
	protected void triggerShutdown() {
		//TODO: 如果我们的run方法中有无限循环啥的，可以在这里置状态，让退出无限循环，，stopAsync()里面会调用到该方法
		running = false; //这里我们改变状态值，run方法中就能够得到响应。=
	}

	@Override
	protected void shutDown() throws Exception {
		//TODO: 可以做一些清理操作，比如关闭连接啥的。shutDown() 是在线程的具体实现里面调用的
	}

}
