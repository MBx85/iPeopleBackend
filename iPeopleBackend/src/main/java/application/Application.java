package application;

import java.text.SimpleDateFormat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	private static String DateTimeFormat = "dd.MM.yyyy HH:mm:ss.SSS";
	private static String DateOnlyFormat = "dd.MM.yyyy";
	public static SimpleDateFormat DateTimeFormatter = new SimpleDateFormat(DateTimeFormat); 
	public static SimpleDateFormat DateOnlyFormatter = new SimpleDateFormat(DateOnlyFormat); 
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}
}