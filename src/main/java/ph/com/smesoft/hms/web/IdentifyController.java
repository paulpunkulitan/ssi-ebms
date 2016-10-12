package ph.com.smesoft.hms.web;

import java.util.Arrays;
import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import ph.com.smesoft.hms.domain.Person;
import ph.com.smesoft.hms.reference.Gender;
import ph.com.smesoft.hms.reference.PersonType;
import ph.com.smesoft.hms.service.PersonService;

@Controller
@RequestMapping("/identify")
public class IdentifyController {
	@Autowired
	PersonService personService;

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute("person_birthdate_date_format",
				DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
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
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String url = "http://localhost:8080/hacms/identifyperson/person";
		RestTemplate restTemplate = new RestTemplate();
		String person = restTemplate.getForObject(url, String.class);
		List<Person> result = personService.findAllPeople();
		String viewtoreturn = "identify/notfound";
		try {
			String validtry = "Enter TRY!!";
			String notFound = "Not Found";
			if (person.equalsIgnoreCase("Invalid")) {
				System.out.println("Not Identified");
				return "identify/notIdentified";
			} else {
				for (int i = 0; i < result.size(); i++) {
					Person aPerson = result.get(i);

					if (person.equalsIgnoreCase(aPerson.getPvId())) {

						if (aPerson.getFirstName() != null) {

							System.out.println("PVID and Person Details found!" + aPerson.getPvId());
							i = result.size();

							addDateTimeFormatPatterns(uiModel);
							uiModel.addAttribute("person", aPerson);
							uiModel.addAttribute("itemId", aPerson.getId());
							viewtoreturn = "people/show";
						}

					}
				}
			}

		} catch (Exception e) {
			return "{\"ERROR\":" + e.getMessage() + "\"}";
		}
		return viewtoreturn;
	}
}
