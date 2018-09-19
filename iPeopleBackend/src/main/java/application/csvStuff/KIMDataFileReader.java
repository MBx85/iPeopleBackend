package application.csvStuff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;
import application.Application;
import application.IPeopleKIM;
import application.csvStuff.FileReaderSupporter;

public class KIMDataFileReader extends GeneralDataFileReader {
	private static final String FilePathProperty = "kim.datasource";
	private static final String BufferFilePathProperty = "kimbuffer.datasource";
	private static final Logger log = Logger.getLogger(KIMDataFileReader.class.getName());
	
	public static IPeopleKIM GetKimFromFile(String kim) {
		IPeopleKIM kimObject = new IPeopleKIM();
		try {
			filepath = SetFilePathFromProperty(FilePathProperty);
			FileReaderSupporter frs = new FileReaderSupporter(filepath);

			while (((frs.line = frs.bufferedReader.readLine()) != null)) {
				if (!frs.IsHeaderLine) // because header line should not be read
				{
					String[] values = frs.line.split(csvDivider);
					if (values[0].equals(kim)) {
						kimObject = WriteKimAttrsFromCsvStringArray(values);
					}
				} else
					frs.IsHeaderLine = false; // set during first iteration
			}
			frs.bufferedReader.close();
		} catch (Exception e) {
		}
		return kimObject;
	}
	
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

	private static IPeopleKIM WriteKimAttrsFromCsvStringArray(String[] arr) {
		IPeopleKIM kimObject = new IPeopleKIM();
		kimObject.setKIM(arr[0]);
		kimObject.setVorname(arr[1]);
		kimObject.setNachname(arr[2]);
		return kimObject;
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
		//sb.append(Application.DateOnlyFormatter.format(kimObj.getGeburtstag()));
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

		return newKim;
	}

	public static ArrayList<IPeopleKIM> GetRefreshedKIMSinceDate(Date inputDate) {
		filepath = SetFilePathFromProperty(FilePathProperty);
		FileReaderSupporter frs = new FileReaderSupporter(filepath);
		SimpleDateFormat sdf = new SimpleDateFormat(Application.GetDateTimeFormat());
		ArrayList<IPeopleKIM> kimList = new ArrayList<IPeopleKIM>();

		try {
			while (((frs.line = frs.bufferedReader.readLine()) != null)) {
				if (!frs.IsHeaderLine) // because header line should not be read
				{
					String[] values = frs.line.split(csvDivider);
					if (sdf.parse(values[4]).after(inputDate)) // values[4] is saveDate in csv
					{
						IPeopleKIM kim = new IPeopleKIM(values[0]);
						kimList.add(kim);
					}
				} else
					frs.IsHeaderLine = false;
			}
		} catch (Exception e) {
		}
		return kimList;
	}
}
