package com.babylonhealth.sympscraper.scraper;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Useful static utility methods for scrapers to use.
 * 
 * @author Andrej
 *
 */
public class ScraperUtil {
	private static final String[] supportedUrls = { "nhs.uk" };

	public static ScraperType matchScraperType(String url) throws URISyntaxException {
		url = url.toLowerCase();
		String domain = getDomainName(url);

		if (!Arrays.asList(supportedUrls).contains(domain)) {
			return null;
		}

		else if (url.toLowerCase().contains("nhs.uk/conditions/")) {
			return ScraperType.NHS_PAGE_SCRAPER;
		}

		return null;
	}

	public static String getDomainName(String url) throws URISyntaxException {
		URI uri = new URI(url);
		String domain = uri.getHost();
		return domain.startsWith("www.") ? domain.substring(4) : domain;
	}

	public static String extractConditionFromUrl(String url) throws URISyntaxException {
		ScraperType scraperType = matchScraperType(url);

		if (scraperType == ScraperType.NHS_PAGE_SCRAPER) {
			Pattern conditionPattern = Pattern.compile(".+?conditions\\/(.+?)\\/");
			Matcher m = conditionPattern.matcher(url);

			if (m.find()) {
				return m.group(1).toLowerCase().replace("-", " ");
			}

		}

		return null;
	}

}
