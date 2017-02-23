package com.babylonhealth.sympscraper.extractor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
public class SimpleSymptomExtractor implements Extractor {

	private final String SYMPTOMS_FILENAME = "/symptoms.txt";
//			this.getClass().getClassLoader().getResource("symptoms.txt").getFile();
	private final Set<String> symptoms;

	public SimpleSymptomExtractor() {
		this.symptoms = new HashSet<String>();

		// Read in symptoms into the hash set
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(SYMPTOMS_FILENAME)));
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
		// To hold our symptoms
		List<String> extractedSymptoms = new ArrayList<String>();

		// Clean elements using a cleaner
		String text = CleanerUtil.simpleCleaner(elements);

		// This is a very simple symptom extractor - it just matches words found
		// in symptom.txt and returns them
		for (String word : text.split(" ")) {
			if (symptoms.contains(word.trim())) {
				if (!extractedSymptoms.contains(word)) {
					extractedSymptoms.add(word);
				}
			}
		}

		return extractedSymptoms;
	}

}
