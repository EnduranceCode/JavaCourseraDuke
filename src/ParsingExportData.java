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
	}

	public static void main(String[] args) {
		ParsingExportData parsingExportData = new ParsingExportData();
		parsingExportData.tester();
	}

}
