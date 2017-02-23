# sympscraper - a lightweight symptom scraper

**sympscraper** is a Java command line library that allows you to scrape and extract symptom entities from medical web pages.

## Example Usage
**sympscraper** accepts urls with its -u flag. Multiple urls can be processed if a file containing them is passed using the -i flag.

```shell
$ java -jar sympscraper-0.0.1.jar -u http://www.nhs.uk/conditions/sleep-paralysis/Pages/Introduction.aspx

condition: 	sleep paralysis
url:		http://www.nhs.uk/conditions/sleep-paralysis/Pages/Introduction.aspx
symptoms:
[1]			paralysis
[2]			hallucination
[3]			frightened

$ java -jar sympscraper-0.0.1.jar -i my_urls.txt

...

```

## Installation

1. Download this repository to a local machine.
2. cd into `symscraper/`
3. run `mvn package`
4. cd into 'target/'
5. Run `$ java -jar sympscraper-0.0.1-jar-with-dependencies.jar -u http://www.nhs.uk/Conditions/Glue-ear/Pages/Symptoms.aspx`.1
1. Download this repository to a local machine.
To build from source, download the repository to a local machine and run `mvn build`.

## How sympscraper works

**sympscraper** can be thought of as a scraping framework and information extraction skeleton framework. 

`scrape -> clean -> extract`

Its three main components are **scrapers** which scrape content from web pages into some intermediate form, **cleaners** which clean the output of the scrapers and **extractors** which extract information.

All three components are decoupled to allow for great extensibility. We describe each in turn:

`scrape`

Scrapers all extend the *Scraper* interface which defines a *scrape(url)* method. Each scraper belongs to a 'category' or 'type' of scrapers defined in the *ScraperType* enum. The category of a scraper defines which URLs it can be used for. 

The *matchScraperType(url)* method in *ScraperUtil()* returns the correct category of scrapers.

All scrapers are initialised by the *ScraperFactory* which defines a method *make(scraperType)* which returns the correct scraper given the type. 

The rationale behind this is that **(a)** one might want to develop different scrapers for different domains (e.g. we implement a *NhsSimpleScraper* which scrapes NHS web pages), and - it follows - **(b)** there should be a smart way of matching the correct scraper to a url.

`clean`

Cleaner methods are currently implemented as static methods in *CleanerUtil*. For example *simpleCleaner()* removes anything other than alphanumeric characters.

`extract`

Extractors all extend the *Extractor* interface which defines a *extract()* method. We defined a *SimpleSymptomExtractor* which matches text scraped a cleaned from a web page to a list of known symptoms (found via wikipedia). 

### More complicated extractors

The problem with symptom entities is that:

1. they can be multi-word
2. they have many synonyms
3. they are tied to the underlying condition to a varying degree

A more principled solution would deduplicate symptoms using something akin to WordNet synsets.

A way of training a model to detect symptoms in text might be to use a list of symptoms to highlight symptoms in as much text as possible and thus 'seed' a training set with with a model could be trained to extract symptoms in a way similar to named entity recognition. 
