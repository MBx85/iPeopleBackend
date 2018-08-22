package application.csvStuff;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileReaderSupporter {
	String line;
	BufferedReader bufferedReader;
	boolean IsHeaderLine;
	
	FileReaderSupporter(String filepath){
		line = null;
		IsHeaderLine = true;
		try {
		bufferedReader = new BufferedReader(new FileReader(filepath));
		}
		catch(Exception e) {
		}
	}
}
