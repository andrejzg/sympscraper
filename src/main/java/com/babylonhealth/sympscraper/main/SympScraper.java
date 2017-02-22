package com.babylonhealth.sympscraper.main;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.jsoup.nodes.Element;

import com.babylonhealth.sympscraper.extractor.SymptomExtractor;
import com.babylonhealth.sympscraper.scraper.Scraper;
import com.babylonhealth.sympscraper.scraper.ScraperFactory;
import com.babylonhealth.sympscraper.scraper.ScraperType;
import com.babylonhealth.sympscraper.scraper.ScraperUtil;

/**
 * Hello world!
 *
 */
public class SympScraper {
	public static void main(String[] args) throws IOException {

		// Initialize command line option definitions
		Options options = initializeOptions();

		// Initialize command line parser
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;

		// Parse provided options
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("scraper", options);

			System.exit(1);
			return;
		}

		// The passed url 
		String url = cmd.getOptionValue("url");

		if (url != null) {
			try {
				// Find the correct scraper for passed url
				ScraperType scraperType = ScraperUtil.matchScraperType(url);
				
				// Scrape the url (with the correct scraper) into Jsoup elements
				Scraper scraper = ScraperFactory.make(scraperType);
				List<Element> elements = scraper.scrape(url);

				// Get condition
				String condition = ScraperUtil.extractConditionFromUrl(url);

				// Get symptoms
				SymptomExtractor extractor = new SymptomExtractor();
				List<String> symptoms = extractor.extract(elements);

				// Print the condition
				System.out.println(String.format("condition: \n%s\n", condition));

				// Print the symptoms
				System.out.println("symptoms: ");
				int nSymptom = 1;
				for (String symptom : symptoms) {
					System.out.println(String.format("[%d]\t%s", nSymptom, symptom));
					nSymptom++;
				}

			} catch (URISyntaxException e) {
				System.out.println(e.getMessage());
				formatter.printHelp("scraper", options);

				System.exit(1);
				return;
			}
			System.exit(1);
			return;
		}

	}

	private static Options initializeOptions() {
		Options options = new Options();

		Option urlOption = new Option("u", "url", true, "input url");
		urlOption.setRequired(true);
		options.addOption(urlOption);

		return options;

	}
}
