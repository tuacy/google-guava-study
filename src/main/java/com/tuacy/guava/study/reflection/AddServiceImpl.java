package com.tuacy.guava.study.reflection;

/**
 * @name: AddServiceImpl
 * @author: tuacy.
 * @date: 2019/7/8.
 * @version: 1.0
 * @Description:
 */
public class AddServiceImpl implements AddService {
    @Override
    public int add(int a, int b) {
        return a + b;
    }
}
