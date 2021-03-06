package com.babylonhealth.sympscraper;

import java.io.BufferedReader;
import java.io.FileReader;
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

import com.babylonhealth.sympscraper.extractor.SimpleSymptomExtractor;
import com.babylonhealth.sympscraper.scraper.Scraper;
import com.babylonhealth.sympscraper.scraper.ScraperFactory;
import com.babylonhealth.sympscraper.scraper.ScraperType;
import com.babylonhealth.sympscraper.scraper.ScraperUtil;

/**
 * SymScraper extracts symptom entities from online web pages.
 *
 * @author Andrej
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
		String urlFile = cmd.getOptionValue("input");

		try {
			if (url != null) {
				printConsoleOutput(url);
			}
			if (urlFile != null) {
				// Read in file and print symptoms to console
				BufferedReader br = new BufferedReader(new FileReader(urlFile));
				String line;
				while ((line = br.readLine()) != null) {
					printConsoleOutput(line);
					System.out.println("\n"); // newline
				}
				br.close();
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
			formatter.printHelp("scraper", options);
		}

	}

	private static void printConsoleOutput(String url) throws URISyntaxException {
		if (url != null) {

			// Find the correct scraper for passed url
			ScraperType scraperType = ScraperUtil.matchScraperType(url);

			if (scraperType == null) {
				return;
			}

			// Scrape the url (with the correct scraper) into Jsoup elements
			Scraper scraper = ScraperFactory.make(scraperType);
			List<Element> elements = scraper.scrape(url);

			if (elements == null) {
				return;
			}

			// Get condition
			String condition = ScraperUtil.extractConditionFromUrl(url);

			// Get symptoms
			SimpleSymptomExtractor extractor = new SimpleSymptomExtractor();
			List<String> symptoms = extractor.extract(elements);

			// Print the condition
			System.out.println(String.format("condition: \t%s", condition));
			
			// Print url
			System.out.println(String.format("url:\t\t%s", url));

			// Print the symptoms
			System.out.println("symptoms: ");
			int nSymptom = 1;
			for (String symptom : symptoms) {
				System.out.println(String.format("[%d]\t\t%s", nSymptom, symptom));
				nSymptom++;
			}

		}
	}

	private static Options initializeOptions() {
		// Command line options
		Options options = new Options();

		// If a user wants to pass a single url they can use -u
		Option urlOption = new Option("u", "url", true, "input url");
		urlOption.setRequired(false);
		options.addOption(urlOption);

		// If a user wants to pass a number of urls they can point the program
		// to a .txt files with one url per line using the -i flag
		Option inputOption = new Option("i", "input", true, "input urls file");
		inputOption.setRequired(false);
		options.addOption(inputOption);

		return options;

	}
}
