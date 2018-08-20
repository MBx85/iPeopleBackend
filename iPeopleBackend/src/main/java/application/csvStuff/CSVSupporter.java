package application.csvStuff;

import java.util.HashMap;

import application.AttributeOrderMap;

public class CSVSupporter {
	static int GetAttributeCSVArrayPos(String attribute) {
		HashMap<String, Integer> AttrMap = AttributeOrderMap.GetAttributeOrderMap();
		int CSVArrayPos = -1;

		for (HashMap.Entry<String, Integer> entry : AttrMap.entrySet()) {
			if (entry.getKey().equals(attribute)) {
				CSVArrayPos = entry.getValue();
				break;
			}
		}
		return CSVArrayPos;
	}
}
