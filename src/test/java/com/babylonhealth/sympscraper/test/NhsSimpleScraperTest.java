package com.babylonhealth.sympscraper.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.jsoup.nodes.Element;
import org.junit.Test;

import com.babylonhealth.sympscraper.scraper.Scraper;
import com.babylonhealth.sympscraper.scraper.ScraperFactory;
import com.babylonhealth.sympscraper.scraper.ScraperType;

public class NhsSimpleScraperTest {

	@Test
	public void testLiExtraction() {
		String url1 = "http://nhs.uk/Conditions/Obsessive-compulsive-disorder/Pages/Symptoms.aspx";
		
		Scraper scraper = ScraperFactory.make(ScraperType.NHS_PAGE_SCRAPER);
		List<Element> elements = scraper.scrape(url1);
		
		assertEquals(false, elements.isEmpty());
	}
	
	

}
