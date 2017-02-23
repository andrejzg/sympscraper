# sympscraper - a lightweight symprom scraper

**sympscraper** is a Java command line library that allows you to scrape and extract symptom entities from medical web pages.

## Example Usage
**sympscraper** accepts urls with its -u flag. Multiple urls can be processed if a file containing them is passed using the -i flag.

```shell
java -jar sympscraper-0.0.1.jar -u http://www.nhs.uk/conditions/sleep-paralysis/Pages/Introduction.aspx

condition: 	sleep paralysis
url:		http://www.nhs.uk/conditions/sleep-paralysis/Pages/Introduction.aspx
symptoms:
[1]		paralysis
[2]		hallucination
[3]		frightened

java -jar sympscraper-0.0.1.jar my_urls.txt

...

```

## Installation

Simply download **sympscraper.0.0.1.jar** from this repository. 

To build from source, download the repository to a local machine and run `mvn build`.

## How sympscraper works

