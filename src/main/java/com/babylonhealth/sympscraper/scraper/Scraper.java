package com.babylonhealth.sympscraper.scraper;

import java.util.List;

import org.jsoup.nodes.Element;

/**
 * Scraper interface all concrete scrapers must implement.
 * 
 * @author Andrej
 *
 */
public interface Scraper {
	/**
	 * Scrapes url and returns Jsoup elements.
	 * 
	 * @param url
	 * @return list of Jsoup elements
	 */
	public List<Element> scrape(String url);
}
