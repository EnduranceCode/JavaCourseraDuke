import java.io.File;

import edu.duke.DirectoryResource;
import edu.duke.FileResource;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class ParsingWeatherData {
	
	/* Fields of the CSV Weather Files */
	static final String TIME_EST				= "TimeEST";
	static final String TEMPERATURE_F			= "TemperatureF";
	static final String DEW_POINT				= "Dew PointF";
	static final String HUMIDITY				= "Humidity";
	static final String SEA_LEVEL_PRESSURE_IN	= "Sea Level PressureIn";
	static final String VISIBILITY_MPH			= "VisibilityMPH";
	static final String WIND_DIRECTION			= "Wind Direction";
	static final String WIND_SPEED_MPH			= "Wind SpeedMPH";
	static final String GUST_SPEED_MPH			= "Gust SpeedMPH";
	static final String PRICIPITATION_IN		= "PrecipitationIn";
	static final String EVENTS					= "Events";
	static final String CONDITIONS				= "Conditions";
	static final String WIND_DIRECTION_DEGRESS	= "WindDirDegrees";
	static final String DATE_UTC				= "DateUTC";
	
	/**
	 * This method returns the CSVRecord with the hottest temperature in the file and thus all
	 * the related information about the hottest temperature,
	 * such as the hour of the coldest temperature.
	 * 
	 * @param	parser
	 * @return
	 */
	public CSVRecord hottestHourInFile(CSVParser parser) {
		
		CSVRecord hottestHourRecord = null;
		
		for (CSVRecord currentRecord : parser) {
			
			hottestHourRecord = getHottestHourRecord(hottestHourRecord, currentRecord);
		}
		
		return hottestHourRecord;
	}
	
	/**
	 * This method returns the CSVRecord with the hottest temperature in a range
	 * of files and thus all the related information about the hottest temperature,
	 * such as the hour of the coldest temperature.
	 * 
	 * @param	parser
	 * @return
	 */
	public CSVRecord hottestHourInManyDays() {
		
		DirectoryResource directoryResource = new DirectoryResource();
		
		CSVRecord hottestHourRecord = null;
		
		for (File file : directoryResource.selectedFiles()) {
			
			FileResource fileResource = new FileResource(file);
			CSVRecord currentRecord = hottestHourInFile(fileResource.getCSVParser());
			
			hottestHourRecord = getHottestHourRecord(hottestHourRecord, currentRecord);
		}
		
		return hottestHourRecord;
	}
	
	/**
	 * This method returns the CSVRecord with the coldest temperature in the file and thus
	 * all the information about the coldest temperature, such as the hour of the coldest temperature
	 * 
	 * @param	parser
	 * @return
	 */
	CSVRecord coldestHourInFile(CSVParser parser) {
		
		CSVRecord coldestHourRecord = null;
		
		for (CSVRecord currentRecord : parser) {
			
			coldestHourRecord = getColdestHourRecord(coldestHourRecord, currentRecord);
		}
		
		return coldestHourRecord;
	}
	
	/**
	 * This method returns the CSVRecord that has the lowest humidity
	 * 
	 * @param	parser
	 * @return
	 */
	public CSVRecord lowestHumidityInFile(CSVParser parser) {
		
		CSVRecord lowestHumidityHourRecord = null;
		
		for (CSVRecord currentRecord : parser) {
			
			lowestHumidityHourRecord = getLowestHumidityHourRecord (lowestHumidityHourRecord, currentRecord);
		}
		
		return lowestHumidityHourRecord;
	}
	
	/**
	 * This method returns a CSVRecord that has the lowest humidity over all the given files
	 * 
	 * @return
	 */
	public CSVRecord lowestHumidityInManyFiles() {
		
		CSVRecord lowestHumidityHourRecord = null;
		
		DirectoryResource directoryResource = new DirectoryResource();
		
		for (File file : directoryResource.selectedFiles()) {
			
			FileResource fileResource = new FileResource(file);
			CSVRecord currentRecord = lowestHumidityInFile(fileResource.getCSVParser());
			
			
			lowestHumidityHourRecord = getLowestHumidityHourRecord(lowestHumidityHourRecord, currentRecord);
		}
		
		return lowestHumidityHourRecord;
	}

	/**
	 * This method chooses, out of two, the record with the hottest temperature
	 * 
	 * @param	firstRecord
	 * @param	secondRecord
	 * @return
	 */
	public CSVRecord getHottestHourRecord(CSVRecord firstRecord, CSVRecord secondRecord) {
		
		if (firstRecord == null) {
			return secondRecord;
		} else {
			double hottestTemperature = Double.parseDouble(firstRecord.get(TEMPERATURE_F));
			double currentMaximTemperature = Double.parseDouble(secondRecord.get(TEMPERATURE_F));
			
			if (currentMaximTemperature > hottestTemperature) {
				return secondRecord;
			} else {
				return firstRecord;
			}
		}
	}
	
	/**
	 * This method chooses, out of two, the record with the coldest temperature
	 * 
	 * @param	firstRecord
	 * @param	secondRecord
	 * @return
	 */
	public CSVRecord getColdestHourRecord(CSVRecord firstRecord, CSVRecord secondRecord) {
		
		if (firstRecord == null) {
			
			if (Double.parseDouble(secondRecord.get(TEMPERATURE_F)) <= -9999) {
				
				return null;
			} else {
				
				return secondRecord;
			}
		} else {
			
			double coldestTemperature = Double.parseDouble(firstRecord.get(TEMPERATURE_F));
			double currentTemperature = Double.parseDouble(secondRecord.get(TEMPERATURE_F));
			
			if ((currentTemperature < coldestTemperature) && (currentTemperature > -9999)) {
				
				return secondRecord;
			} else {
				
				return firstRecord;
			}
		}
	}
	
	/**
	 * This method chooses, out of two, the record with the lowest humidity
	 * 
	 * @param	firstRecord
	 * @param	secondRecord
	 * @return
	 */
	public CSVRecord getLowestHumidityHourRecord(CSVRecord firstRecord, CSVRecord secondRecord) {
		
		if (firstRecord == null) {
			
			if (secondRecord.get(HUMIDITY).equals("N/A")) {
				return null;
			} else {
				
				return secondRecord;
			}
		} else {
			
			if (secondRecord.get(HUMIDITY).equals("N/A")) {
				
				return firstRecord;
			} else {
				
				double lowestHumidity = Double.parseDouble(firstRecord.get(HUMIDITY));
				double currentHumidity = Double.parseDouble(secondRecord.get(HUMIDITY));
				
				if (currentHumidity < lowestHumidity) {
					
					return secondRecord;
				} else {
					
					return firstRecord;
				}
			}
		}
	}
	
	/**
	 * This method returns a string that is the name of the file from
	 * a range of files that has the coldest temperature
	 * 
	 * @return
	 */
	public String fileWithColdestTemperature() {
		
		DirectoryResource directoryResource = new DirectoryResource();
		
		String coldestHourRecordFile = "";
		CSVRecord previousColdestHourRecord = null;
		CSVRecord currentColdestHourRecord = null;
		
		for (File file : directoryResource.selectedFiles()) {
			
			FileResource fileResource = new FileResource(file);
			CSVRecord currentRecord = coldestHourInFile(fileResource.getCSVParser());
			
			currentColdestHourRecord = getColdestHourRecord(currentColdestHourRecord, currentRecord);
			
			if (previousColdestHourRecord != currentColdestHourRecord) {
				
				coldestHourRecordFile = file.getName();
				previousColdestHourRecord = currentColdestHourRecord;
			}
		}
		
		return coldestHourRecordFile;
	}
	
	/**
	 * This method returns a double that represents the average temperature in the file
	 * 
	 * @param	parser
	 * @return
	 */
	public Double averageTemperatureInFile(CSVParser parser) {
		
		Double sumTemperature = 0.0;
		int countTemperature = 0;
		Double averageTemperature = 0.0;
		
		for (CSVRecord currentRecord : parser) {
			
			Double currentTemperature = Double.parseDouble(currentRecord.get(TEMPERATURE_F));
			
			if (currentTemperature != -9999) {
				
				countTemperature += 1;
				sumTemperature = sumTemperature + currentTemperature;
			}
		}
		
		if (countTemperature != 0) {
			averageTemperature = sumTemperature / countTemperature;
		}
		
		return averageTemperature;
	}
	
	/**
	 * Tests {@link #hottestHourInFile(CSVParser)}
	 */
	public void testHottestHourInFile() {
		
		FileResource fileResource = new FileResource("nc_weather/2015/weather-2015-01-01.csv");
		CSVParser parser = fileResource.getCSVParser();
		CSVRecord hottestHourRecord = hottestHourInFile(parser);
		
		System.out.println("Hottest temperature was " + hottestHourRecord.get(TEMPERATURE_F) + " at " + hottestHourRecord.get(TIME_EST));
	}
	
	/**
	 * Tests {@link #coldestHourInFile(CSVParser)}
	 */
	public void testColdestHourInFile() {
		
		FileResource fileResource = new FileResource("nc_weather/2015/weather-2015-01-01.csv");
		CSVParser parser = fileResource.getCSVParser();
		CSVRecord coldestHourRecord = coldestHourInFile(parser);
		
		System.out.println("Coldest temperature was " + coldestHourRecord.get(TEMPERATURE_F) + " at " + coldestHourRecord.get(TIME_EST));
	}
	
	/**
	 * Tests {@link #hottestHourInManyDays()}
	 */
	public void testHottestHourInManyDays() {
		
		CSVRecord hottestHourRecord = hottestHourInManyDays();
		System.out.println("Hottest temperature was " + hottestHourRecord.get(TEMPERATURE_F) + " at " + hottestHourRecord.get(DATE_UTC));
	}
	
	/**
	 * Tests {@link #fileWithColdestTemperature()}
	 */
	public void testFileWithColdestTemperature() {
		
		String coldestDayFile = fileWithColdestTemperature();
		String coldestDayYear = coldestDayFile.substring(8, 12);
		
		FileResource fileResourceColdestHour = new FileResource("nc_weather/" + coldestDayYear + "/" + coldestDayFile);
		CSVParser parser = fileResourceColdestHour.getCSVParser();
		
		System.out.println("Coldest day was in file " + coldestDayFile);
		System.out.println("Coldest temperature on that day was " + coldestHourInFile(parser).get(TEMPERATURE_F));
		
		parser = fileResourceColdestHour.getCSVParser();
		System.out.println("All the Temperatures on the coldest day were:");
		for (CSVRecord currentRecord : parser) {
			System.out.println(currentRecord.get(DATE_UTC) + ": " + currentRecord.get(TEMPERATURE_F));
		}
	}
	
	/**
	 * Tests {@link #lowestHumidityInFile(CSVParser)}
	 */
	public void testLowestHumidityInFile() {
		
		FileResource fileResource = new FileResource("nc_weather/2014/weather-2014-01-20.csv");
		CSVParser parser = fileResource.getCSVParser();
		CSVRecord csvRecord = lowestHumidityInFile(parser);
		
		System.out.println("Lowest Humidity was " + csvRecord.get(HUMIDITY) + " at " + csvRecord.get(DATE_UTC));
	}
	
	/**
	 * Tests {@link #lowestHumidityInManyFiles()}
	 */
	public void testLowestHumidityInManyFiles() {
		
		CSVRecord lowestHumidityHourRecord = lowestHumidityInManyFiles();
		
		System.out.println("Lowest Humidity was " + lowestHumidityHourRecord.get(HUMIDITY) + " at " + lowestHumidityHourRecord.get(DATE_UTC));
	}
	
	/**
	 * Tests {@link #averageTemperatureInFile(CSVParser)}
	 */
	public void testAverageTemperatureInFile() {
		
		FileResource fileResource = new FileResource("nc_weather/2014/weather-2014-01-20.csv");
		CSVParser csvParser = fileResource.getCSVParser();
		
		System.out.println("Average temperature in file is " + averageTemperatureInFile(csvParser));
	}

	public static void main(String[] args) {
		
		ParsingWeatherData parsingWeatherData = new ParsingWeatherData();
		
		parsingWeatherData.testHottestHourInFile();
		System.out.println();
		parsingWeatherData.testHottestHourInManyDays();
		System.out.println();
		parsingWeatherData.testColdestHourInFile();
		System.out.println();
		parsingWeatherData.testFileWithColdestTemperature();
		System.out.println();
		parsingWeatherData.testLowestHumidityInFile();
		System.out.println();
		parsingWeatherData.testLowestHumidityInManyFiles();
		System.out.println();
		parsingWeatherData.testAverageTemperatureInFile();
		System.out.println();
	}
}
