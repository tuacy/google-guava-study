package com.tuacy.guava.study.string;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @name: SplitterTest
 * @author: tuacy.
 * @date: 2019/6/28.
 * @version: 1.0
 * @Description: Splitter的使用
 */
public class SplitterTest {


	@Test
	public void splitTest() {
		Iterable<String> iterableList = Splitter.on(',').trimResults() // 移除前面和后面的空白
												.omitEmptyStrings() // 去掉null
												.split("foo,bar,,   qux");
		List<String> resultList = Lists.newArrayList(iterableList);
		for (String item : resultList) {
			System.out.println(item);
		}
	}

	/**
	 * splitToList 最终直接返回List
	 */
	@Test
	public void splitToListTest() {
		List<String> resultList = Splitter.on(',').trimResults().omitEmptyStrings().splitToList("foo,bar,,   qux");
		for (String item : resultList) {
			System.out.println(item);
		}
	}

	/**
	 * MapSplitter
	 */
	@Test
	public void mapSplitterTest() {
		String source = "key0:value0#key1:value1";
		Map<String, String> resultMap = Splitter.on("#").withKeyValueSeparator(":").split(source);
		for (Map.Entry<String, String> entry : resultMap.entrySet()) {
			System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
		}
	}


}
