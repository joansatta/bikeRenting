package renting.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProjectPropertiesSingleton {
	
	Map<String,String> inputs;
	static ProjectPropertiesSingleton instance;	
	
	public static synchronized ProjectPropertiesSingleton getInstance() throws IOException{
        if(instance == null){
            instance = new ProjectPropertiesSingleton();
        }
        return instance;
    }
	
	public ProjectPropertiesSingleton() throws IOException {
		inputs = readFile("src\\main\\resources\\inputs.assets");
	}
	
	public String getInput(String name) {
		return inputs.get(name);
	}
	
	
	public static Map<String,String> readFile(String relativePath) throws IOException 
	{
		Map<String,String> properties = new HashMap<String,String>();
		BufferedReader br = Utils.getBufferedReader(relativePath);
		String st; 
		while ((st = br.readLine()) != null) {
			String[] pair = st.split("\\=");
			properties.put(pair[0].trim(), pair[1].trim());
		}
		br.close();
		return properties;
	}



}
