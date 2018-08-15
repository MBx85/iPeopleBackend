package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class KIMDataFileReader {
	private static final String filepath = "datasource\\Kimdata.csv"; // in Properties File auslagern?
	private static final String bufferFilePath = "datasource\\KimdataBuffer.csv";
	private static final String csvDivider = ";";

	static void PutIntoFile(IPeopleKIM kimObj) {

		try {
			FileReader fileReader = new FileReader(filepath);
			BufferedReader bufferedReader = new BufferedReader(fileReader); // Always wrap FileReader in BufferedReader.
			String line = null, kim = kimObj.getKIM();
			boolean IsHeaderLine = true, KimInserted = false;
			FileWriter bufferWriter = new FileWriter(bufferFilePath);

			while (((line = bufferedReader.readLine()) != null)) {
				if (!IsHeaderLine) // because header line should not be read
				{
					String[] values = line.split(csvDivider);
					String tempKim = values[CSVSupporter.GetAttributeCSVArrayPos("kim")];
					if (tempKim.equals(kim)) {
						/* überschreibe die Zeile im CSV */
						KimInserted = true;
					} else {
						bufferWriter.write(line + "\r\n");
						/* füge als letzte Zeile an */
					}
				} else {
					bufferWriter.write(line + "\r\n");
					IsHeaderLine = false; // set during first iteration
				}
			}
			
			if(!KimInserted) {
				
			}
			
			bufferedReader.close();
			bufferWriter.close();
			RenameAndCleanUpFiles(filepath, bufferFilePath);
		} catch (Exception e) {
		}

	}

	private static void RenameAndCleanUpFiles(String PathDataFile, String PathBufferFile) {
		File bufferFile = new File(PathBufferFile);
		File dataFile = new File(PathDataFile);
		bufferFile.renameTo(dataFile);
		try {
			FileWriter CleanUpper = new FileWriter(PathBufferFile);
			CleanUpper.write("");
			CleanUpper.close();
		} catch (Exception e) {
		}
	}
}
