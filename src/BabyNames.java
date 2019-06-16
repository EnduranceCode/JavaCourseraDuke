import java.io.File;

import edu.duke.DirectoryResource;
import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class BabyNames {
	
	/* CSV data file fields */
	static final int NAME = 0;
	static final int GENDER = 1;
	static final int QUANTITY = 2;
	
	/**
	 * Print the number of girls names, the number of boys names and the total names in the file
	 * 
	 * @param	fileResource
	 */
	public void totalBirths (FileResource fileResource) {
		
		System.out.println("Total number of births:\t" + numberBirthsTotal(fileResource)); 
		System.out.println("Number of boys births:\t" + numberBirthsMale(fileResource)); 
		System.out.println("Number of girls births:\t" + numberBirthsFemale(fileResource)); 
	}
	
	/**
	 * Calculate the total number of births registered in the given data file
	 * 
	 * @param	fileResource
	 * @return
	 */
	public int numberBirthsTotal(FileResource fileResource) {
		
		int numberBirthsTotal = 0;
		
		for (CSVRecord csvCurrentRecord : fileResource.getCSVParser(false)) {
			
			numberBirthsTotal += Integer.parseInt(csvCurrentRecord.get(QUANTITY));
		}
		
		return numberBirthsTotal;
	}
	
	/**
	 * Calculate the total number of males births registered in the given data file
	 * 
	 * @param	fileResource
	 * @return
	 */
	public int numberBirthsMale(FileResource fileResource) {
		
		int numberBirthsMale = 0;
		
		for (CSVRecord csvCurrentRecord : fileResource.getCSVParser(false)) {
			
			if (csvCurrentRecord.get(GENDER).equals("M")) {
				
				numberBirthsMale += Integer.parseInt(csvCurrentRecord.get(QUANTITY));
			}
		}
		
		return numberBirthsMale;
	}
	
	/**
	 * Calculate the total number of females births registered in the given data file
	 * 
	 * @param	fileResource
	 * @return
	 */
	public int numberBirthsFemale(FileResource fileResource) {
		
		int numberBirthsFemale = 0;
		
		for (CSVRecord csvCurrentRecord : fileResource.getCSVParser(false)) {
			
			if (csvCurrentRecord.get(GENDER).equals("F")) {
				
				numberBirthsFemale += Integer.parseInt(csvCurrentRecord.get(QUANTITY));
			}
		}
		
		return numberBirthsFemale;
	}
	
	/**
	 * Tests {@link #totalBirths(FileResource)}
	 */
	public void testTotalBirths() {
		
		FileResource fileResource = new FileResource("data/us_babynames_test/example-small.csv");
		
		totalBirths(fileResource);
	}

	public static void main(String[] args) {
		
		BabyNames babyNames = new BabyNames();
		
		babyNames.testTotalBirths();
		System.out.println();
	}

}
