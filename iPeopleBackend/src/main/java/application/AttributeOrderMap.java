package application;

import java.util.HashMap;

public class AttributeOrderMap {

	public static HashMap<String, Integer> GetAttributeOrderMap() {
		HashMap<String, Integer> attributeOrder = new HashMap<String, Integer>();
		attributeOrder.put("kim", 0);
		attributeOrder.put("vorname", 1);
		attributeOrder.put("nachname", 2);
		return attributeOrder;
	}
}
