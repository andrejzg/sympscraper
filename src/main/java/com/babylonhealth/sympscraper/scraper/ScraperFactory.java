package com.babylonhealth.sympscraper.scraper;

/**
 * Defines a factory which return the appropriate scraper given a ScraperType enum.
 * 
 * @author Andrej
 *
 */
public class ScraperFactory {
	public static Scraper make(ScraperType scraperType) {		
		switch (scraperType) {
		case NHS_PAGE_SCRAPER:
			return (Scraper) new NhsSimpleScraper();
		default:
			return null;
		}
	}
}
