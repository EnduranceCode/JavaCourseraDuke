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
		
		if (countTemperature == 0) {
			
			averageTemperature = -9999.0;
		} else {
				
			averageTemperature = sumTemperature / countTemperature;
		}
		
		return averageTemperature;
	}
	
	/**
	 * This method returns a double that represents the average temperature of only those temperatures
	 * when the humidity was greater than or equal to the given value
	 * 
	 * @param	parser
	 * @param	value
	 * @return
	 */
	public Double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value) {
		
		Double sumTemperatureWithHightHumidity = 0.0;
		int countTemperatureWithHightHumidity = 0;
		Double averageTemperatureWithHighHumidity = 0.0;
		
		for (CSVRecord currentRecord : parser) {
			
			Double currentTemperature = Double.parseDouble(currentRecord.get(TEMPERATURE_F));
			Double currentHumidity = Double.parseDouble(currentRecord.get(HUMIDITY));
			
			if (currentTemperature != -9999 && currentHumidity > value) {
				
				countTemperatureWithHightHumidity += 1;
				sumTemperatureWithHightHumidity += currentTemperature;
			}
		}
		
		if (countTemperatureWithHightHumidity == 0) {
			
			averageTemperatureWithHighHumidity = -9999.0;
		} else {
			
			averageTemperatureWithHighHumidity = sumTemperatureWithHightHumidity / countTemperatureWithHightHumidity;
		}
		
		return averageTemperatureWithHighHumidity;
	}
	
	/**
	 * Tests {@link #hottestHourInFile(CSVParser)}
	 */
	public void testHottestHourInFile() {
		
		FileResource fileResource = new FileResource("nc_weather/2014/weather-2014-05-01.csv");
		CSVParser parser = fileResource.getCSVParser();
		CSVRecord hottestHourRecord = hottestHourInFile(parser);
		
		System.out.println("Hottest temperature was " + hottestHourRecord.get(TEMPERATURE_F) + " at " + hottestHourRecord.get(DATE_UTC));
	}
	
	/**
	 * Tests {@link #coldestHourInFile(CSVParser)}
	 */
	public void testColdestHourInFile() {
		
		FileResource fileResource = new FileResource("nc_weather/2014/weather-2014-05-01.csv");
		CSVParser parser = fileResource.getCSVParser();
		CSVRecord coldestHourRecord = coldestHourInFile(parser);
		
		System.out.println("Coldest temperature was " + coldestHourRecord.get(TEMPERATURE_F) + " at " + coldestHourRecord.get(DATE_UTC));
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
		
		FileResource fileResource = new FileResource("nc_weather/2014/weather-2014-04-01.csv");
		CSVParser parser = fileResource.getCSVParser();
		CSVRecord csvRecord = lowestHumidityInFile(parser);
		
		System.out.println("Lowest Humidity was " + csvRecord.get(HUMIDITY) + " at " + csvRecord.get(DATE_UTC));
		
		fileResource = new FileResource("nc_weather/2014/weather-2014-06-29.csv");
		parser = fileResource.getCSVParser();
		csvRecord = lowestHumidityInFile(parser);
		
		System.out.println("FINAL QUIZ - QUESTION 4: Lowest Humidity was " + csvRecord.get(HUMIDITY) + " at " + csvRecord.get(DATE_UTC));
		
		fileResource = new FileResource("nc_weather/2014/weather-2014-07-22.csv");
		parser = fileResource.getCSVParser();
		csvRecord = lowestHumidityInFile(parser);
		
		System.out.println("FINAL QUIZ - QUESTION 5: Lowest Humidity was " + csvRecord.get(HUMIDITY) + " at " + csvRecord.get(DATE_UTC));
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
		
		FileResource fileResource = new FileResource("nc_weather/2014/weather-2014-06-01.csv");
		CSVParser csvParser = fileResource.getCSVParser();
		
		System.out.println("Average temperature in file is " + averageTemperatureInFile(csvParser));
		
		fileResource = new FileResource("nc_weather/2013/weather-2013-08-10.csv");
		csvParser = fileResource.getCSVParser();
		
		System.out.println("FINAL QUIZ - QUESTION 8: Average temperature in file is " + averageTemperatureInFile(csvParser));
	}
	
	/**
	 * Tests {@link #averageTemperatureWithHighHumidityInFile(CSVParser, int)}
	 */
	public void testAverageTemperatureWithHighHumidityInFile() {
		
		final int VALUE = 80; 
		
		FileResource file20140120 = new FileResource("nc_weather/2014/weather-2014-01-20.csv");
		FileResource file20140320 = new FileResource("nc_weather/2014/weather-2014-03-30.csv");
		FileResource file20130902 = new FileResource("nc_weather/2013/weather-2013-09-02.csv");
		
		CSVParser parser20140120 = file20140120.getCSVParser();
		CSVParser parser20140320 = file20140320.getCSVParser();
		CSVParser parser20130902 = file20130902.getCSVParser();
		
		Double averageTemperature20140120 = averageTemperatureWithHighHumidityInFile(parser20140120, VALUE);
		Double averageTemperature20140320 = averageTemperatureWithHighHumidityInFile(parser20140320, VALUE);
		Double averageTemperature20130902 = averageTemperatureWithHighHumidityInFile(parser20130902, VALUE);
		
		if (averageTemperature20140120 == -9999) {
			
			System.out.println("No temperatures with that humidity");
		} else {
			
			System.out.println("Average Temp when high Humidity is " + averageTemperature20140120);
		}
		System.out.println();
		if (averageTemperature20140320 == -9999) {
			
			System.out.println("No temperatures with that humidity");
		} else {
			
			System.out.println("Average Temp when high Humidity is " + averageTemperature20140320);
		}
		System.out.println();
		if (averageTemperature20130902 == -9999) {
			
			System.out.println("FINAL QUIZ - QUESTION 9: No temperatures with that humidity");
		} else {
			
			System.out.println("FINAL QUIZ - QUESTION 9: Average Temp when high Humidity is " + averageTemperature20130902);
		}
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
		parsingWeatherData.testAverageTemperatureWithHighHumidityInFile();
		System.out.println();
	}
}
