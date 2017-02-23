package com.babylonhealth.sympscraper.cleaner;

import java.util.List;

import org.jsoup.nodes.Element;

/**
 * Holds static text cleaning methods.
 * 
 * @author Andrej
 *
 */
public class CleanerUtil {

	/**
	 * Returns a cleaned String text block from passed Jsoup elements.
	 * 
	 * @param elements
	 * @return cleaned text
	 */
	public static String simpleCleaner(List<Element> elements) {
		StringBuilder text = new StringBuilder();
		for (Element element : elements) {
			String line = element.text();
			String[] words = line.replaceAll("[^0-9a-zA-Z ]", "").toLowerCase().split("\\s+");
			for (String word : words) {
				text.append(word);
				text.append(" ");
			}
		}
		return text.toString().trim();
	}

	/**
	 * Returns a cleaned String text block from passed Strings.
	 * 
	 * @param elements
	 * @return cleaned text
	 */
	public static String simpleCleaner(String... lines) {
		StringBuilder text = new StringBuilder();
		for (String line : lines) {
			String[] words = line.replaceAll("[^0-9a-zA-Z ]", "").toLowerCase().split("\\s+");
			for (String word : words) {
				text.append(word);
				text.append(" ");
			}
		}
		return text.toString().trim();
	}
	
}
