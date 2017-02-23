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

	/**
	 * Returns correct scraper to be used given a url or null otherwise.
	 * 
	 * @param url
	 * @return scraper type, null otherwise
	 * @throws URISyntaxException
	 */
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

	/**
	 * Returns the domain name from a url or null if undefined.
	 * 
	 * @param url
	 * @return domain name or null if undefined
	 * @throws URISyntaxException
	 */
	public static String getDomainName(String url) throws URISyntaxException {
		URI uri = new URI(url);
		String domain = uri.getHost();
		if (domain != null) {
			return domain.startsWith("www.") ? domain.substring(4) : domain;
		}
		return null;
	}

	/**
	 * A method used to extract the (health) condition from a NHS url.
	 * 
	 * @param url
	 * @return health condition
	 * @throws URISyntaxException
	 */
	public static String extractConditionFromUrl(String url) throws URISyntaxException {
		ScraperType scraperType = matchScraperType(url.toLowerCase());

		if (scraperType == ScraperType.NHS_PAGE_SCRAPER) {
			Pattern conditionPattern = Pattern.compile(".+?conditions\\/(.+?)\\/");
			Matcher m = conditionPattern.matcher(url.toLowerCase());

			if (m.find()) {
				return m.group(1).replace("-", " ");
			}

		}

		return null;
	}

}
