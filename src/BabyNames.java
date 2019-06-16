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
	 * This method returns the rank of the name in the file for the given gender,
	 * where rank 1 is the name with the largest number of births.
	 * If the name is not in the file, then -1 is returned.
	 * 
	 * @param	year
	 * @param	name
	 * @param	gender
	 * @return
	 */
	public int getRank(int year, String name, String gender) {
		
		int rank = -1;
		
		FileResource fileResource = new FileResource("data/us_babynames_by_year/yob" + year + ".csv");

		for (CSVRecord csvCurrentRecord : fileResource.getCSVParser(false)) {
			
			if (csvCurrentRecord.get(NAME).equals(name) && csvCurrentRecord.get(GENDER).equals(gender)) {
				
				if (gender.equals("M")) {
					
					rank = (int) csvCurrentRecord.getRecordNumber() - numberNamesFemale(fileResource);
				} else {
					
					rank = (int) csvCurrentRecord.getRecordNumber();
				}
			}
		}
		
		return rank;
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
	 * Returns the number of male names in the given data file
	 * 
	 * @param	fileResource
	 * @return
	 */
	public int numberNamesMale(FileResource fileResource) {
		
		int numberNamesMale = 0;
		
		for (CSVRecord csvCurrentRecord : fileResource.getCSVParser(false)) {
			
			if (csvCurrentRecord.get(GENDER).equals("M")) {
				numberNamesMale += 1;
			}
		}
		
		return numberNamesMale;
	}
	
	/**
	 * Returns the number of female names in the given data file
	 * 
	 * @param	fileResource
	 * @return
	 */
	public int numberNamesFemale(FileResource fileResource) {
		
		int numberNamesFemale = 0;
		
		for (CSVRecord csvCurrentRecord : fileResource.getCSVParser(false)) {
			
			if (csvCurrentRecord.get(GENDER).equals("F")) {
				numberNamesFemale += 1;
			}
		}
		
		return numberNamesFemale;
	}
	
	/**
	 * Tests {@link #totalBirths(FileResource)}
	 */
	public void testTotalBirths() {
		
		FileResource fileResource = new FileResource("data/us_babynames_test/example-small.csv");
		
		totalBirths(fileResource);
	}
	
	/**
	 * Tests {@link #getRank(int, String, String)}
	 */
	public void testGetRank() {
		
		int rank = getRank(2012, "Mason", "M");
		
		if (rank == -1) {
			
			System.out.println("The given name is not present in the given year file");
		} else {
			
			System.out.println("The rank of the given name in the given year file is " + rank);
		}
	}

	public static void main(String[] args) {
		
		BabyNames babyNames = new BabyNames();
		
		babyNames.testTotalBirths();
		System.out.println();
		babyNames.testGetRank();
		System.out.println();
	}

}
