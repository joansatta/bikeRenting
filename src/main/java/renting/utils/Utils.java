package renting.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Calendar;
import java.util.Date;

public class Utils {


	public static BufferedReader getBufferedReader(String relativePath) throws FileNotFoundException {
		File file;
		BufferedReader br;		
		try {
			file = new File(relativePath); 
			br = new BufferedReader(new FileReader(file)); 
		} catch (Exception e) {
			file = new File (new File("").getAbsolutePath()+relativePath);
			br = new BufferedReader(new FileReader(file)); 
		}
		return br;
	} 
	
	public static Date addHoursToDate(Date date,int timePeriodInHours) {
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.HOUR_OF_DAY, timePeriodInHours); 
		return cal.getTime();
	}
}
