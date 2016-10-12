package ph.com.smesoft.hms.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import ph.com.smesoft.hms.domain.Person;
import ph.com.smesoft.hms.dto.Data;
import ph.com.smesoft.hms.reference.Gender;
import ph.com.smesoft.hms.reference.PersonType;
import ph.com.smesoft.hms.service.PersonService;

import java.util.Arrays;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.format.DateTimeFormat;

@Controller
@RequestMapping("/enroll")
public class EnrollController {

	@Autowired
	PersonService personService;

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute("person_birthdate_date_format",
				DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, Person person) {
		uiModel.addAttribute("person", person.getPvId());
		uiModel.addAttribute("person", person);
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("genders", Arrays.asList(Gender.values()));
		uiModel.addAttribute("persontypes", Arrays.asList(PersonType.values()));
	}

	@RequestMapping(value = "/callEnroll", headers = "Accept=application/json")
	public String enrollpersonForm(Model uiModel) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String url = "http://localhost:8080/hacms/enrollperson/person";
		RestTemplate restTemplate = new RestTemplate();
		String pvId = restTemplate.getForObject(url, String.class);
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			  Data pv_Id = mapper.readValue(pvId, Data.class); 
			  String palmId = pv_Id.getPvId(); 
			 System.out.println("PALM>>>>>"+palmId);
			 
			Person person = new Person();
			person.setPvId(palmId);
			System.out.println("PERSON - PV>>>>>>" + person);
			uiModel.addAttribute("pvId", palmId);
			populateEditForm(uiModel, person);
			return "people/create";

		} catch (Exception e) {
			return "{\"ERROR\":" + e.getMessage() + "\"}";
		}
	}

}