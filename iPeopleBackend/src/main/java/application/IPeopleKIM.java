package application;

public class IPeopleKIM {
	private String kim;
	private String vorname;
	private String nachname;
	
	IPeopleKIM(String kim){
		this.kim = kim;	
		// this is for testing
		this.vorname = "MMarkus";
		this.nachname = "BBrand";
	}
	
	public String getKIM() {
		return kim;
	}
}
