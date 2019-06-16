import java.io.File;

import org.apache.commons.csv.CSVRecord;

import edu.duke.DirectoryResource;
import edu.duke.FileResource;

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
		
		/* The default value is set to -1 because it is the value that method should return
		 * if the given name and gender aren't found in the given year
		 */
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
	 * This method returns the name of the person in the file at this rank, for the given gender,
	 * where rank 1 is the name with the largest number of births.
	 * If the rank does not exist in the file, then “NO NAME” is returned.
	 * 
	 * @param	year
	 * @param	rank
	 * @param	gender
	 * @return
	 */
	public String getName(int year, int rank, String gender) {
		
		/* The default value is set to 'NO NAME' because it is the value that method should return
		 * if the given rank and gender aren't found in the given year
		 */
		String name = "NO NAME";
		
		FileResource fileResource = new FileResource("data/us_babynames_by_year/yob" + year + ".csv");
		
		int recordNumber = 0;
		
		if (gender.equals("M")) {
			
			recordNumber = numberNamesFemale(fileResource) + rank;
		} else {
			
			recordNumber = rank;
		}
		
		for (CSVRecord csvCurrentRecord : fileResource.getCSVParser(false)) {
			
			if ((int) csvCurrentRecord.getRecordNumber() == recordNumber) {
				
				name = csvCurrentRecord.get(NAME);
			}
		}
		
		return name;
	}
	
	/**
	 * This method determines what name would have been named if they were born in a different year,
	 * based on the same popularity
	 * 
	 * @param	name
	 * @param	year
	 * @param	newYear
	 * @param	gender
	 * @return
	 */
	public String whatIsNameInYear(String name, int year, int newYear, String gender) {
		
		/* Set the default value for the methor renurt if no name is found */
		String newName = "There is no name with the same ranking in the given new year";
		
		FileResource fileResource = new FileResource("data/us_babynames_by_year/yob" + year + ".csv");
		
		int rank = 0;
		
		for (CSVRecord csvCurrentRecord : fileResource.getCSVParser(false)) {
			
			if (csvCurrentRecord.get(NAME).equals(name) && csvCurrentRecord.get(GENDER).equals(gender)) {
				if (gender.equals("M")) {
					
					rank = (int) csvCurrentRecord.getRecordNumber() - numberNamesFemale(fileResource);
				} else {
					
					rank = (int) csvCurrentRecord.getRecordNumber();
				}
			}
		}
		
		newName = getName(newYear, rank, gender);
		
		return newName;
	}
	
	public int yearOfHighestRank(String name, String gender) {
		
		/* The default value is set to -1 because it is the value that method should return
		 * if the given name and gender aren't found in the given files
		 */
		int yearOfHighestRank = -1;
		
		DirectoryResource directoryResource = new DirectoryResource();
		
		for (File file : directoryResource.selectedFiles()) {
			
			int currentYear = Integer.parseInt(file.getName().substring(3,7));
			
			int currentRank = getRank(currentYear, name, gender);
			
			if (currentRank > yearOfHighestRank) {
				
				yearOfHighestRank = currentYear;
			}
		}
		
		return yearOfHighestRank;
	}
	
	/**
	 * This method selects a range of files to process and returns a double representing the
	 * average rank of the name and gender over the selected files.
	 * It returns -1.0 if the name is not ranked in any of the selected files.
	 * 
	 * @param name
	 * @param gender
	 * @return
	 */
	public double getAverageRank(String name, String gender) {
		
		/* The default value is set to -1.0 because it is the value that method should return
		 * if the given name and gender aren't found in the given files
		 */
		double averageRank = -1.0;
		
		int sumRank = 0;
		int countYears = 0;
		
		DirectoryResource directoryResource = new DirectoryResource();
		
		for (File file : directoryResource.selectedFiles()) {
			
			int currentYear = Integer.parseInt(file.getName().substring(3, 7));
			
			int currentRank = getRank(currentYear, name, gender);
			
			if (currentRank != -1) {
				
				sumRank += currentRank;
				countYears += 1;
			}
		}
		
		if (countYears != 0) {
			
			averageRank = (double) sumRank / countYears;
		}
		
		return averageRank;
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
	
	/**
	 * Tests {@link #getName(int, int, String)}
	 */
	public void testGetName() {
		
		String name = getName(2012, 2, "M");
		
		System.out.println("The name with the given rank in the given file is " + name);
	}
	
	/**
	 * Tests {@link #whatIsNameInYear(String, int, int, String)}
	 * 
	 * @param	name
	 * @param	year
	 * @param	newYear
	 * @param	gender
	 */
	public void testWhatIsNameInYear(String name, int year, int newYear, String gender) {
		
		String newName = whatIsNameInYear(name, year, newYear, gender);
		
		System.out.println(name + " born in " + year + " would be " + newName + " if she was born in " + newYear);
	}
	
	public void testYearOfHighestRank(String name, String gender) {
		
		int yearOfHighestRank = yearOfHighestRank(name, gender);
		
		System.out.println("The of highest rank, in the given files, of the name " + name + " with gender " + gender + " is " + yearOfHighestRank);
	}
	
	public void testGetAverageRank(String name, String gender) {
		
		double averageRank = getAverageRank(name, gender);
		
		if (averageRank == -1.0) {
			
			System.out.println("The given name and gender isn't ranked in the given files");
		} else {
			System.out.println("The average rank for " + name + " of the gender " + gender + " is " + averageRank);
		}
	}

	public static void main(String[] args) {
		
		BabyNames babyNames = new BabyNames();
		
		babyNames.testTotalBirths();
		System.out.println();
		babyNames.testGetRank();
		System.out.println();
		babyNames.testGetName();
		System.out.println();
		babyNames.testWhatIsNameInYear("Isabella", 2012, 2014, "F");
		System.out.println();
		babyNames.testYearOfHighestRank("Mason", "M");
		System.out.println();
		babyNames.testGetAverageRank("Mason", "M");
		System.out.println();
		babyNames.testGetAverageRank("Jacob", "M");
		System.out.println();
	}

}
