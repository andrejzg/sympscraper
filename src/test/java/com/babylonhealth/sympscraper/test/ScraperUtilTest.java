package com.babylonhealth.sympscraper.test;

import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;

import org.junit.Test;

import com.babylonhealth.sympscraper.scraper.ScraperType;
import com.babylonhealth.sympscraper.scraper.ScraperUtil;

public class ScraperUtilTest {
	@Test
	public void testMatchScraperType() throws URISyntaxException {

		String correctUrl = "http://www.nhs.uk/Conditions/Obsessive-compulsive-disorder/Pages/Symptoms.aspx";
		String wrongUrl1 = "http://www.nhs.uk/";
		String wrongUrl2 = "http://www.google.com/";

		assertEquals(ScraperType.NHS_PAGE_SCRAPER, ScraperUtil.matchScraperType(correctUrl));
		assertEquals(null, ScraperUtil.matchScraperType(wrongUrl1));
		assertEquals(null, ScraperUtil.matchScraperType(wrongUrl2));

	}

	@Test
	public void testGetDomainName() throws URISyntaxException {
		String url1 = "http://nhs.uk/Conditions/Obsessive-compulsive-disorder/Pages/Symptoms.aspx";
		String url2 = "http://www.nhs.co.uk/";
		String url3 = "http://aaa/bbb";
		String url4 = "xxxxx";

		assertEquals("nhs.uk", ScraperUtil.getDomainName(url1));
		assertEquals("nhs.co.uk", ScraperUtil.getDomainName(url2));
		assertEquals("aaa", ScraperUtil.getDomainName(url3));
		assertEquals(null, ScraperUtil.getDomainName(url4));
	}

	@Test
	public void testExtractCondition() throws URISyntaxException {
		String url1 = "http://nhs.uk/Conditions/Obsessive-compulsive-disorder/Pages/Symptoms.aspx";
		String url2 = "http://www.google.com";

		assertEquals("obsessive compulsive disorder", ScraperUtil.extractConditionFromUrl(url1));
		assertEquals(null, ScraperUtil.extractConditionFromUrl(url2));
	}

}
