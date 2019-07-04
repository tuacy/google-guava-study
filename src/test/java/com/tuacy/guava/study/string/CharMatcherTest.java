package com.tuacy.guava.study.string;

import com.google.common.base.CharMatcher;

import org.junit.Test;

/**
 * @name: CharMatcherTest
 * @author: tuacy.
 * @date: 2019/7/4.
 * @version: 1.0
 * @Description: CharMatcher的使用
 */
public class CharMatcherTest {

	@Test
	public void matcherNumCharTest() {
		CharMatcher numMatcher = CharMatcher.inRange('0', '9');
		numMatcher.matches('3');
	}

}
