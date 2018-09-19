package application;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import application.csvStuff.KIMDataFileReader;

@RestController
public class IPeopleKIMController {

	private static final Logger log = Logger.getLogger(IPeopleKIMController.class.getName());

	@GetMapping("/IPeopleKIM/{kim}") 
	public IPeopleKIM GetKim(@PathVariable("kim") String kim) {
		IPeopleKIM kimObj = KIMDataFileReader.GetKimFromFile(kim);
		log.info("KIM " + kimObj.getKIM() + " returned");
		return kimObj;
	}

	@CrossOrigin(origins = "http://localhost:8091")
	@PutMapping("/IPeopleKIM/{kim}")
	public void PutKIM(@PathVariable String kim, @RequestBody IPeopleKIM input) {
		log.info("PUT REQUEST RECEIVED --- KIM: " + input.getKIM() + ", Nachname: " + input.getNachname()
				+ " , Vorname: " + input.getVorname() + " ");
		KIMDataFileReader.PutIntoFile(input);
	}

	@CrossOrigin(origins = "http://localhost:8091")
	@GetMapping("/IPeopleKIM/newKim")
	public IPeopleKIM GetNewKim() {
		String ReturnKIM = KIMDataFileReader.GetUnunsedKIM();
		log.info("Unused KIM " + ReturnKIM + " returned");
		return new IPeopleKIM(ReturnKIM); // works but returns whole KIM Object :-(
	}

	@CrossOrigin(origins = "http://localhost:8091")
	@GetMapping("/IPeopleKIM/ChangedKims")
	public ResponseEntity<List<String>> getRefreshedKims(@RequestParam(value = "date", required = true) String inputDate) {
		log.info("Changed Kims called");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			Date date = sdf.parse(inputDate);
			List<String> kimStrings = new ArrayList<String>();
			List<IPeopleKIM> ar = KIMDataFileReader.GetRefreshedKIMSinceDate(date);
			for (IPeopleKIM ik : ar) {
				kimStrings.add(ik.getKIM());
			}
			return new ResponseEntity<List<String>>(kimStrings, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
}
