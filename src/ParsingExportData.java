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
	
	public void tester() {
		FileResource fileResource = new FileResource("exports_small.csv");
		CSVParser parser = fileResource.getCSVParser();
		
		System.out.println(countryInfo(parser, "Germany"));
		
	}

	public static void main(String[] args) {
		ParsingExportData parsingExportData = new ParsingExportData();
		parsingExportData.tester();
	}

}
