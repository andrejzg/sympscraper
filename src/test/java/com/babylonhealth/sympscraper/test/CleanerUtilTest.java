package com.babylonhealth.sympscraper.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.babylonhealth.sympscraper.cleaner.CleanerUtil;

public class CleanerUtilTest {

	@Test
	public void testCleanerUtil() {
		String s1 = "";
		String s2 = "!@#$%-/.";
		String s3 = " Thom!,as!2!!   ";

		assertEquals("", CleanerUtil.simpleCleaner(s1));
		assertEquals("", CleanerUtil.simpleCleaner(s2));
		assertEquals("thomas2", CleanerUtil.simpleCleaner(s3));

	}
}
