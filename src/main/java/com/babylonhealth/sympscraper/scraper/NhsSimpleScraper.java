package com.babylonhealth.sympscraper.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Scrapes bullet points from NHS condition pages.
 * 
 * @author Andrej
 *
 */
public class NhsSimpleScraper implements Scraper {

	private static final String SELECTOR_PATTERN = "div.main-content.healthaz-content.clear";

	private final static Logger LOGGER = Logger.getLogger(NhsSimpleScraper.class.getName());

	public ArrayList<Element> scrape(String url) {

		try {
			Elements elements = jSoupScrape(url, SELECTOR_PATTERN);
			if (elements == null) {
				return null;
			}

			// We only want to return a subset of the elements in NHS condition
			// pages (the bullet points)
			ArrayList<Element> scrapedElements = new ArrayList<Element>();

			for (Element element : elements) {
				scrapedElements.add(element);
			}

			return scrapedElements;

		} catch (IOException e) {
			LOGGER.warning(String.format("Could not GET data from: %s", url));
		}

		return null;
	}

	/**
	 * Helper method, returns scraped Jsoup elements given a url or null
	 * otherwise;
	 * 
	 * @param url
	 * @return jsoup elements
	 * @throws IOException
	 */
	private Elements jSoupScrape(String url, String selectorPattern) throws IOException {
		// Connect to the url
		Document doc = Jsoup.connect(url).get();

		// Extract Jsoup elements using the CONTENT_DIV selector pattern
		Element element = doc.select(selectorPattern).first();
		if (element != null) {
			return element.children();
		}
		return null;
	}

}
