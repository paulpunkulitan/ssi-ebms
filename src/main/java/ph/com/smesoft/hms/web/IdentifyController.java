package ph.com.smesoft.hms.web;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.SerializationFeature;

import ph.com.smesoft.hms.domain.Floor;
import ph.com.smesoft.hms.domain.Person;
import ph.com.smesoft.hms.dto.Data;
import ph.com.smesoft.hms.reference.Gender;
import ph.com.smesoft.hms.reference.PersonType;
import ph.com.smesoft.hms.service.PersonService;


@Controller
@RequestMapping("/identify")
public class IdentifyController {
	@Autowired
	PersonService personService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String identifyForm(@PathVariable("id") long id, Model uiModel) {
		String url = "http://localhost:8080/hms/floors/{id}";
		RestTemplate restTemplate = new RestTemplate();
		Floor floor = restTemplate.getForObject(url, Floor.class, id);
		System.out.println(floor);
		uiModel.addAttribute("floor", floor);
		return "identify/listidentify";
	}

	@RequestMapping(params = "form")
	public ResponseEntity<String> identifypostForm() {
		System.out.println("1");
		String url = "http://localhost:8080/hacms/palmuslog";
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "1");
		map.put("pvId", "PV-001");
		map.put("palmusId", "PLM-001");
		map.put("is_Authenticated", "Yes");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(map.toString(), headers);

		System.out.println("2");
		RestTemplate restTemplate = new RestTemplate();
		MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
		jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		restTemplate.getMessageConverters().add(jsonHttpMessageConverter);
		System.out.println("3");

		ResponseEntity<String> palmuslog = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		// System.out.println(palmuslog);
		System.out.println(palmuslog.getBody());
		return new ResponseEntity<String>(HttpStatus.CREATED);

	}
	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("person_birthdate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }
	
	void populateEditForm(Model uiModel, Person person) {
        uiModel.addAttribute("person", person);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("genders", Arrays.asList(Gender.values()));
        uiModel.addAttribute("persontypes", Arrays.asList(PersonType.values()));
    }

	@SuppressWarnings("unused")
	@RequestMapping(value = "/callIdentify", headers = "Accept=application/json")
	public String identifypersonForm(Model uiModel) {
		System.out.println("HMS-1");
		HttpHeaders headers = new HttpHeaders();
		System.out.println("HMS-2");
		headers.add("Content-Type", "application/json; charset=utf-8");
		System.out.println("HMS-3");
		String url = "http://localhost:8080/hacms/identifyperson/person";
		System.out.println("HMS-4");
		RestTemplate restTemplate = new RestTemplate();
		System.out.println("HMS-5");
		String person = restTemplate.getForObject(url, String.class);
		System.out.println("HMS-6");
		System.out.println("result 1" +person);
		// return new ResponseEntity<String>(person,headers, HttpStatus.OK);
		System.out.println("HMS-7");
		// ObjectMapper mapper = new ObjectMapper();
		System.out.println("result 2" +person);
		List<Person> result = personService.findAllPeople();
		System.out.println(result);
		System.out.println("result 3" +person);
		System.out.println(""+person);
		String viewtoreturn = "identify/notfound";
		try {
			 
			//Person aPerson = null;
			String validtry = "Enter TRY!!";
			// String invalid = "Invalid";
			String notFound = "Not Found";
			System.out.println("HMS-13");
			// Person passResult = new Person();

			
			if(person.equalsIgnoreCase("Invalid")) {
				System.out.println("Not Identified");
				return "identify/notIdentified";
			} else {
				for (int i = 0; i < result.size(); i++) {
					Person aPerson = result.get(i);
					System.out.println("Pasok Dito i : " + i);
					
					if (person.equalsIgnoreCase(aPerson.getPvId())) {
						
						if (aPerson.getFirstName() != null) {
						
							System.out.println("PVID and Person Details found!" + aPerson.getPvId());
							i = result.size();
							//return "identify/identified";
							
							
					        addDateTimeFormatPatterns(uiModel);
					        uiModel.addAttribute("person", aPerson);
					        uiModel.addAttribute("itemId", aPerson.getId());
					        viewtoreturn = "people/show";
						}
					}
				}
			}
			
			
		} catch (Exception e) {
			System.out.println("ERROR");
			return "{\"ERROR\":" + e.getMessage() + "\"}";
		}

        return viewtoreturn;

	
	
	}
	/*@RequestMapping(value="/callIdentify",headers = "Accept=application/json")
	public ResponseEntity<String> identifypersonForm()
	{
	HttpHeaders headers = new HttpHeaders();
	System.out.println("Enter 2");
	headers.add("Content-Type", "application/json; charset=utf-8");
	 System.out.println("hms-1");
	 String url = "http://localhost:8080/hacms/identifyperson/person";
	 System.out.println("hms-2");
	 RestTemplate restTemplate = new RestTemplate();
	 System.out.println("hms-3");
	 String person = restTemplate.getForObject(url, String.class);
	 return new ResponseEntity<String>(person,headers, HttpStatus.OK); 
	}*/

}
