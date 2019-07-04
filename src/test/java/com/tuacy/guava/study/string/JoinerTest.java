package com.tuacy.guava.study.string;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @name: JoinerTest
 * @author: tuacy.
 * @date: 2019/6/28.
 * @version: 1.0
 * @Description:  Joiner的使用
 */
public class JoinerTest {


	@Test
	public void joinTest() {
		List<String> stringSrc = Lists.newArrayList("C", "Android", "Java");
		String resultString  = Joiner.on("; ")
									 .join(stringSrc);
		System.out.println(resultString);
	}

	/**
	 * useForNull 用指定的值来替换null
	 */
	@Test
	public void useForNullTest() {
		String resultString = Joiner.on("; ")
									.useForNull("abc")
									.join("C", null, "Android", "Java");
		System.out.println(resultString);
	}

	/**
	 * skipNulls 跳过null
	 */
	@Test
	public void skipNullsTest() {
		String resultString = Joiner.on("; ")
									.skipNulls()
									.join("C", null, "Android", "Java");
		System.out.println(resultString);
	}

	/**
	 * withKeyValueSeparator
	 */
	@Test
	public void withKeyValueSeparatorTest() {
		Map<String, String> mapSrc = new HashMap<>();
		mapSrc.put("key0", "value0");
		mapSrc.put("key1", "value1");
		String resultString = Joiner.on("; ")
									.withKeyValueSeparator("&")
									.join(mapSrc);
		System.out.println(resultString);
	}

}
