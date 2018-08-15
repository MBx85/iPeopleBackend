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
					String csvKim = values[CSVSupporter.GetAttributeCSVArrayPos("kim")];
					if (csvKim.equals(kim)) {
						/* überschreibe die Zeile im CSV */
						bufferWriter.write(WriteCSVLine(kimObj) + "\r\n");
						KimInserted = true;
					} else {
						bufferWriter.write(line + "\r\n");
						/* schreibe die Zeile ins File, da InputKIM!=csvKIM */
					}
				} else {
					bufferWriter.write(line + "\r\n");
					IsHeaderLine = false; // set during first iteration
				}
			}

			if (!KimInserted) {
				/* Append InputKIM to CSV */
				bufferWriter.write(WriteCSVLine(kimObj) + "\r\n");
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
		dataFile.delete();
		bufferFile.renameTo(dataFile);
	}

	private static String WriteCSVLine(IPeopleKIM kimObj) {
		StringBuffer sb = new StringBuffer();
		sb.append(kimObj.getKIM());
		sb.append(csvDivider);
		sb.append(kimObj.getVorname());
		sb.append(csvDivider);
		sb.append(kimObj.getNachname());
		sb.append(csvDivider);
		// sb.append(kimObj.get) Geburtstag
		return sb.toString();
	}
}
