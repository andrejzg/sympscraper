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
public class NhsScraper implements Scraper {

	private static final String CONTENT_DIV = "div.main-content.healthaz-content.clear";

	private final static Logger LOGGER = Logger.getLogger(NhsScraper.class.getName());

	public ArrayList<Element> scrape(String url) {

		try {
			// Connect to the url
			Document doc = Jsoup.connect(url).get();

			// Extract Jsoup elements using the CONTENT_DIV selector pattern
			Elements elements = doc.select(CONTENT_DIV).first().children();

			// We only want to return a subset of the elements in NHS condition
			// pages (the bullet points)
			ArrayList<Element> scrapedElements = new ArrayList<Element>();

			// A flag indicating whether to add the current element to
			// scrapedElements
			boolean isScraping = false;

			for (Element element : elements) {
				if (element.tag().toString().equals("h2") && element.text().contains("Symptoms")) {
					isScraping = true;
				} else if (element.tag().toString().equals("h2") && !element.text().contains("Symptoms")) {
					isScraping = false;
					continue;
				}

				// Scrape the bullet points
				if (isScraping) {
					if (element.tag().toString().equals("ul")) {
						for (Element li : element.children()) {
							scrapedElements.add(li);
						}
					}
				}
			}

			return scrapedElements;

		} catch (IOException e) {
			LOGGER.warning(String.format("Could not GET data from: %s", url));
		}

		return null;
	}

}
