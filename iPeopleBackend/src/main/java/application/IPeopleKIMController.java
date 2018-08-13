package application;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class IPeopleKIMController {
	
	@RequestMapping(value = "/IPeopleKIM/{kim}", method = RequestMethod.GET)
	public void TestSendRequest(@PathVariable("kim") String kim) {
			RequestSender RS = new RequestSender(kim);
			RS.PutKIM();
	}
}
