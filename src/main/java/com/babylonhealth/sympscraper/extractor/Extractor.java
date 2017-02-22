package com.babylonhealth.sympscraper.extractor;

import java.util.Collection;
import java.util.List;

import org.jsoup.nodes.Element;

/**
 * Extractor interface all concrete Extractors must implement.
 * 
 * @author Andrej
 *
 */
public interface Extractor {
	/**
	 * Extracts strings from Jsoup elements.
	 * 
	 * @param elements
	 * @return
	 */
	public Collection<String> extract(List<Element> elements);
}
