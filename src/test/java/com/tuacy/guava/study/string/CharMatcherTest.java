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
		// 创建一个匹配数字字符的CharMatcher
		CharMatcher numMatcher = CharMatcher.inRange('0', '9');
		// 匹配判断(false)
		System.out.println(numMatcher.matches('a'));
	}


	@Test
	public void retainFromTest() {
		// 创建一个匹配数字字符的CharMatcher
		CharMatcher numMatcher = CharMatcher.inRange('0', '9');
		// retainFrom保留匹配到的字符(123789)
		System.out.println(numMatcher.retainFrom("123abc789"));
	}

	@Test
	public void countInTest() {
		// 创建匹配任何字符的Matcher
		CharMatcher numMatcher = CharMatcher.any();
		// 返回sequence中匹配到的字符个数(9个)
		int matcherCount = numMatcher.countIn("abc123abc");
		System.out.println("匹配到的字符个数：" + matcherCount);
	}

	@Test
	public void negateTest() {
		// 创建了一个匹配字母的Matcher
		CharMatcher letterMatcher = CharMatcher.inRange('a', 'z')
											.or(CharMatcher.inRange('A', 'Z'));
		// 非字母的Matcher negate()规则相反
		CharMatcher notLetterMatcher = letterMatcher.negate();
		System.out.println(notLetterMatcher.retainFrom("abcABC123"));
	}

	@Test
	public void indexInTest() {

		// 创建了一个只匹配a字母的Matcher
		CharMatcher letterMatcher = CharMatcher.is('a');
		// 非字母的Matcher negate()规则相反
		int aStartIndex = letterMatcher.indexIn("123abcabc");
		int aEndIndex = letterMatcher.lastIndexIn("123abcabc");
		System.out.println("a第一次出现的位置：" + aStartIndex);
		System.out.println("a最后一次出现的位置：" + aEndIndex);
	}
}
