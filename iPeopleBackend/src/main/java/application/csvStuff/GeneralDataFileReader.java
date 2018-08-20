package application.csvStuff;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Properties;

public class GeneralDataFileReader {
	static final String csvDivider = ";";
	static String filepath = "";
	
	static String SetFilePathFromProperty(String property) {
		Properties properties = new Properties();
		try {
			BufferedInputStream stream = new BufferedInputStream(
					new FileInputStream("src\\main\\resources\\filesEndpoints.properties"));
			properties.load(stream);
			stream.close();
		} catch (Exception e) {
		}
		return properties.getProperty(property);
	}
}
