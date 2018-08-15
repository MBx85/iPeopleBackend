package application;

import java.util.Date;

public class IPeopleKIM {
	private String kim;
	private String vorname;
	private String nachname;
	private Date saveDate;

	IPeopleKIM() {
	}

	IPeopleKIM(String kim) {
		this.kim = kim;
	}

	public String getKIM() {
		return kim;
	}

	public String getNachname() {
		return nachname;
	}

	public String getVorname() {
		return vorname;
	}

	public Date getSaveDate() {
		return saveDate;
	}
	
	public void setKIM(String kim) {
		this.kim = kim;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
		
	public void setSaveDate(Date date) {
		this.saveDate = date;
	}
}
