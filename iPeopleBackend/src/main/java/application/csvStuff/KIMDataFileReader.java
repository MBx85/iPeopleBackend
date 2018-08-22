package application.csvStuff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;
import application.Application;
import application.IPeopleKIM;

public class KIMDataFileReader extends GeneralDataFileReader {
	private static final String FilePathProperty = "kim.datasource";
	private static final String BufferFilePathProperty = "kimbuffer.datasource";
	private static final Logger log = Logger.getLogger(KIMDataFileReader.class.getName());

	public static void PutIntoFile(IPeopleKIM kimObj) {
		try {
			filepath = SetFilePathFromProperty(FilePathProperty);
			final String bufferFilePath = SetFilePathFromProperty(BufferFilePathProperty);
			BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath)); // Always wrap FileReader in
																							// BufferedReader.
			String line = null, kim = kimObj.getKIM();
			boolean IsHeaderLine = true, KimInserted = false;
			FileWriter bufferWriter = new FileWriter(bufferFilePath);

			while (((line = bufferedReader.readLine()) != null)) {
				if (!IsHeaderLine) // because header line should not be read
				{
					String[] values = line.split(csvDivider);
					if (values[0].equals(kim)) {// Kim ist die erste Spalte
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
			log.info("Exception caught during file writing: " + e.getMessage());
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
		sb.append(Application.DateOnlyFormatter.format(kimObj.getGeburtstag()));
		sb.append(csvDivider);
		sb.append(Application.DateTimeFormatter.format(new Date())); // saveDate
		return sb.toString();
	}

	public static String GetUnunsedKIM() {
		/*
		 * write all current kims in array create random kim check if kim is in array
		 * --> if yes --> check again --> if no --> return kim
		 */
		filepath = SetFilePathFromProperty(FilePathProperty);
		FileReaderSupporter frs = new FileReaderSupporter(filepath);
		ArrayList<String> kimArray = new ArrayList<String>();

		try {
			while (((frs.line = frs.bufferedReader.readLine()) != null)) {
				if (!frs.IsHeaderLine) // because header line should not be read
				{
					String[] values = frs.line.split(csvDivider);
					kimArray.add(values[0]); // add kim of current line to array
				} else
					frs.IsHeaderLine = false; // set during first iteration
			}
			frs.bufferedReader.close();
		} catch (Exception e) {
		}

		String newKim = "";
		boolean KimAlreadyPresent = false;

		do {
			newKim = String.format("%06d", Integer.parseInt((new Integer((int) (Math.random() * 1E6))).toString()));
			// 6stellige, zufällige int als Kim, linksseitig aufgefüllt mit Nullen
			if (KimAlreadyPresent)
				KimAlreadyPresent = false;
			for (String s : kimArray) {
				if (s.equals(newKim)) {
					KimAlreadyPresent = true;
					continue;
				}
			}
		} while (KimAlreadyPresent);

		return newKim; // ToDo: left padding with zeros
	}
}
