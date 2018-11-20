package renting.service;

import java.io.BufferedReader;
import java.io.IOException;

import renting.utils.Utils;

public class GenericService {

	

	public static String getLine(String path,String rentalPeriod) throws IOException 
	{
		BufferedReader br = Utils.getBufferedReader(path);
		String st; 
		while ((st = br.readLine()) != null) {
			if(st.contains(rentalPeriod)){
				br.close();
				return st;				
			}
		}		
		br.close();
		return "";
	} 

	public static String parseLine(String line,String del1, String del2){
		return line.split(del1)[1].split(del2)[0];
	}
}
