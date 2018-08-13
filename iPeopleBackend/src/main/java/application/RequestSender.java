package application;

import java.util.logging.Logger;
import org.springframework.web.client.RestTemplate;

public class RequestSender {
	//private final static String testKIM = "322";
	private final static String targetURL = "http://localhost:8080/KIM/";
	private final static Logger log = Logger.getLogger( RequestSender.class.getName());
	
	//private RestTemplate restTemplate;
	private String kim;
	
	RequestSender(String kim){
		this.kim = kim; 
	}
	
	public void PutKIM() {
		log.info("LogInfo: KIM " + kim + " sent");
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put(targetURL+kim, new IPeopleKIM(kim));
	}
}
