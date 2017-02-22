package com.babylonhealth.sympscraper.extractor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Element;

import com.babylonhealth.sympscraper.cleaner.CleanerUtil;

/**
 * A concrete symptom extracting Extractor which (using extract()) returns a
 * list of symptoms given a list of Jsoup elements.
 * 
 * @author Andrej
 *
 */
public class SymptomExtractor implements Extractor {

	private final String SYMPTOMS_FILENAME = "resources/symptoms.txt";
	private final Set<String> symptoms;

	public SymptomExtractor() {
		this.symptoms = new HashSet<String>();

		// Read in symptoms into the hash set
		try {
			BufferedReader br = new BufferedReader(new FileReader(SYMPTOMS_FILENAME));
			String line;
			while ((line = br.readLine()) != null) {
				symptoms.add(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> extract(List<Element> elements) {
		List<String> extractedSymptoms = new ArrayList<String>();

		// Clean elements using a cleaner
		String text = CleanerUtil.simpleCleaner(elements);

		// This is a very simple symptom extractor - it just matches words found
		// in symptom.txt and return them
		for (String word : text.split(" ")) {
			if (symptoms.contains(word)) {
				extractedSymptoms.add(word);
			}
		}
		
		return extractedSymptoms;
	}

}
