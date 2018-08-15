package application;

import java.util.logging.Logger;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IPeopleKIMController {
	
	private static final Logger log = Logger.getLogger( IPeopleKIMController.class.getName() );

	@GetMapping("/IPeopleKIM/{kim}")
	public String TestReturn(@PathVariable("kim") String kim) {
		RequestSender RS = new RequestSender(kim);
		RS.PutKIM();
		return kim;
	}
	
	@CrossOrigin(origins = "http://localhost:8091")
	@PutMapping("/IPeopleKIM/{kim}")
	public void PutKIM(@PathVariable String kim, @RequestBody IPeopleKIM input) {
		log.info("PUT REQUEST RECEIVED --- KIM: " + input.getKIM() + ", Nachname: " 
	+ input.getNachname() + " , Vorname: " + input.getVorname());
		KIMDataFileReader.PutIntoFile(input);
	}
	
}
