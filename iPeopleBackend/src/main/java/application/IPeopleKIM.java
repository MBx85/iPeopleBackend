package application;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IPeopleKIM {
	private String kim;
	private String vorname;
	private String nachname;
	private Date geburtstag;
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

	public Date getGeburtstag() {
		return geburtstag;
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

	public void setGeburtstag(Date geburtstag) {
			this.geburtstag = geburtstag;
	}

	public void setSaveDate(Date date) {
		this.saveDate = date;
	}
}
