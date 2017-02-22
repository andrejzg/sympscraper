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
	 * Returns a String text block of cleaned and concatinated Jsoup elements.
	 * 
	 * @param elements
	 * @return cleaned text
	 */
	public static String simpleCleaner(List<Element> elements) {
		StringBuilder text = new StringBuilder();
		for (Element element : elements) {
			String line = element.text();
			String[] words = line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
			for (String word : words) {
				text.append(word);
				text.append(" ");
			}
		}
		return text.toString();
	}

}
