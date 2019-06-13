import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class ParsingExportData {
	
	/**
	 * This method returns a string of information about a country
	 * 
	 * @param	parser
	 * @param	country
	 * @return
	 */
	String countryInfo (CSVParser parser, String country) {
		
		for (CSVRecord record : parser) {
			
			String countryName = record.get("Country");
			
			if (countryName.contains(country)) {
				return country + " : " + record.get("Exports") + " : " + record.get("Value (dollars)");
			}
		}
		
		return "NOT FOUND";
	}
	
	/**
	 * This method prints the names of all the countries that have both exportItem1 and exportItem2 as export items
	 * 
	 * @param	parser
	 * @param	exportItem1
	 * @param	exportItem2
	 */
	public void listExportersTwoProducts (CSVParser parser, String exportItem1, String exportItem2) {
		
		for (CSVRecord record : parser) {
			
			String exports = record.get("Exports");
			
			if (exports.contains(exportItem1) && exports.contains(exportItem2)) {
				System.out.println(record.get("Country"));
			}
		}
	}
	
	/**
	 * This method returns the number of countries that export a given item
	 * 
	 * @param parser
	 * @param exportItem
	 * @return
	 */
	public int numberOfExporters(CSVParser parser, String exportItem) {
		
		int numberOfExporters = 0;
		
		for (CSVRecord record : parser) {
			
			String exports = record.get("Exports");
			
			if (exports.contains(exportItem)) {
				numberOfExporters += 1;
			}
		}
		
		return numberOfExporters;
	}
	
	/**
	 * This method prints the names of countries and their Value amount for all countries whose
	 * Value (dollars) string is longer than the amount string
	 * @param parser
	 * @param amount
	 */
	public void bigExporters(CSVParser parser, String amount) {
		
		for (CSVRecord record : parser) {
			
			String value = record.get("Value (dollars)");
			
			if (value.length() > amount.length()) {
				System.out.println(record.get("Country") + " " + value);
			}
		}
		
	}
	
	public void tester() {
		FileResource fileResource = new FileResource("exports_small.csv");
		CSVParser parser = fileResource.getCSVParser();
		
		System.out.println("CountryInfo() Output:");
		System.out.println(countryInfo(parser, "Germany"));
		System.out.println("\n");
		
		parser = fileResource.getCSVParser();

		System.out.println("listExportersTwoProducts() Output:");
		listExportersTwoProducts(parser, "gold", "diamonds");
		System.out.println("\n");
		
		parser = fileResource.getCSVParser();

		System.out.println("numberOfExporters() Output:");
		System.out.println(numberOfExporters(parser, "gold"));
		System.out.println("\n");
		
		parser = fileResource.getCSVParser();

		System.out.println("bigExporters() Output:");
		bigExporters(parser, "$999,999,999");
		System.out.println("\n");
		
		FileResource fileResourceQuiz = new FileResource("exportdata.csv");
		CSVParser parserQuiz = fileResourceQuiz.getCSVParser();
		
		System.out.println("listExportersTwoProducts() Output for the Quiz:");
		listExportersTwoProducts(parserQuiz, "gold", "diamonds");
		System.out.println("\n");
		
		parserQuiz = fileResourceQuiz.getCSVParser();

		System.out.println("numberOfExporters() Output for the Quiz:");
		System.out.println(numberOfExporters(parserQuiz, "gold"));
		System.out.println("\n");
		
		parserQuiz = fileResourceQuiz.getCSVParser();
		
		System.out.println("listExportersTwoProducts() Output for the Quiz:");
		System.out.println(countryInfo(parserQuiz, "Nauru"));
		System.out.println("\n");
		
		parserQuiz = fileResourceQuiz.getCSVParser();

		System.out.println("bigExporters() Output for the Quiz:");
		bigExporters(parserQuiz, "$999,999,999,999");
		System.out.println("\n");
	}

	public static void main(String[] args) {
		ParsingExportData parsingExportData = new ParsingExportData();
		parsingExportData.tester();
	}

}
