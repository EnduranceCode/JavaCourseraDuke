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
			double currentMinimTemperature = Double.parseDouble(secondRecord.get(TEMPERATURE_F));
			
			if ((currentMinimTemperature < coldestTemperature) && (currentMinimTemperature > -9999)) {
				return secondRecord;
			} else {
				return firstRecord;
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
	
	public void testHottestHourInFile() {
		
		FileResource fileResource = new FileResource("nc_weather/2015/weather-2015-01-01.csv");
		CSVParser parser = fileResource.getCSVParser();
		CSVRecord hottestHourRecord = hottestHourInFile(parser);
		
		System.out.println("Hottest temperature was " + hottestHourRecord.get(TEMPERATURE_F) + " at " + hottestHourRecord.get(TIME_EST));
	}
	
	public void testColdestHourInFile() {
		
		FileResource fileResource = new FileResource("nc_weather/2014/weather-2014-01-30.csv");
		CSVParser parser = fileResource.getCSVParser();
		CSVRecord coldestHourRecord = coldestHourInFile(parser);
		
		System.out.println("Coldest temperature was " + coldestHourRecord.get(TEMPERATURE_F) + " at " + coldestHourRecord.get(TIME_EST));
	}
	
	public void testHottestHourInManyDays() {
		
		CSVRecord hottestHourRecord = hottestHourInManyDays();
		System.out.println("Hottest temperature was " + hottestHourRecord.get(TEMPERATURE_F) + " at " + hottestHourRecord.get(DATE_UTC));
	}
	
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

	public static void main(String[] args) {
		
		ParsingWeatherData parsingWeatherData = new ParsingWeatherData();
		
		parsingWeatherData.testHottestHourInFile();
		parsingWeatherData.testHottestHourInManyDays();
		parsingWeatherData.testColdestHourInFile();
		parsingWeatherData.testFileWithColdestTemperature();
		;
	}
}
