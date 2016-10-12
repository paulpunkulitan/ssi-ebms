package ph.com.smesoft.hms.web;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import ph.com.smesoft.hms.domain.Accommodation;
import ph.com.smesoft.hms.domain.Floor;
import ph.com.smesoft.hms.domain.Person;
import ph.com.smesoft.hms.domain.Room;
import ph.com.smesoft.hms.reference.Gender;
import ph.com.smesoft.hms.reference.PersonType;
import ph.com.smesoft.hms.service.AccommodationService;
import ph.com.smesoft.hms.service.PersonService;
import ph.com.smesoft.hms.service.RoomService;

@Controller
@RequestMapping("/accommodations")
public class AccommodationController {

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
            //Accommodation accommodation = accommodationService.findAccommodation(id);
        	Person person = new Person();
        	person.setId(1L);
        	person.setFirstName("Kenski");
        	person.setMiddleName("Agpi");
        	person.setLastName("Senados");
        	person.setBirthDate(new Date());
        	person.setPalmusId("PLM-00001A");
        	person.setGender(Gender.Female);
        	person.setPersonType(PersonType.Customer);
        	person.setVersion(1);
        	
        	Floor floor = new Floor();
        	floor.setId(1L);
        	floor.setFloorNumber("FLR-00001A");
        	floor.setDescription("Floors");
        	floor.setVersion(1);
        	
        	Room room = new Room();
        	room.setId(1L);
        	room.setRoomNumber("RM-00001A");
        	room.setDescription("Rooms");
        	room.setFloor(floor);
        	room.setVersion(1);
        	
            Accommodation accommodation = new Accommodation();
            accommodation.setId(1L);
            accommodation.setEndDate(new Date());
            accommodation.setStartDate(new Date());
            accommodation.setPerson(person);
            accommodation.setRoom(room);
        	if (accommodation == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<String>(accommodation.toJson(), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
            List<Accommodation> result = accommodationService.findAllAccommodations();
            return new ResponseEntity<String>(Accommodation.toJsonArray(result), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            Accommodation accommodation = Accommodation.fromJsonToAccommodation(json);
            accommodationService.saveAccommodation(accommodation);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+accommodation.getId().toString()).build().toUriString());
            return new ResponseEntity<String>(headers, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            for (Accommodation accommodation: Accommodation.fromJsonArrayToAccommodations(json)) {
                accommodationService.saveAccommodation(accommodation);
            }
            return new ResponseEntity<String>(headers, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json, @PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            Accommodation accommodation = Accommodation.fromJsonToAccommodation(json);
            accommodation.setId(id);
            if (accommodationService.updateAccommodation(accommodation) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<String>(headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            Accommodation accommodation = accommodationService.findAccommodation(id);
            if (accommodation == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            accommodationService.deleteAccommodation(accommodation);
            return new ResponseEntity<String>(headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@Autowired
    AccommodationService accommodationService;

	@Autowired
    PersonService personService;

	@Autowired
    RoomService roomService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Accommodation accommodation, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, accommodation);
            return "accommodations/create";
        }
        uiModel.asMap().clear();
        accommodationService.saveAccommodation(accommodation);
        return "redirect:/accommodations/" + encodeUrlPathSegment(accommodation.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Accommodation());
        return "accommodations/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("accommodation", accommodationService.findAccommodation(id));
        uiModel.addAttribute("itemId", id);
        return "accommodations/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("accommodations", Accommodation.findAccommodationEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) accommodationService.countAllAccommodations() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("accommodations", Accommodation.findAllAccommodations(sortFieldName, sortOrder));
        }
        addDateTimeFormatPatterns(uiModel);
        return "accommodations/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Accommodation accommodation, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, accommodation);
            return "accommodations/update";
        }
        uiModel.asMap().clear();
        accommodationService.updateAccommodation(accommodation);
        return "redirect:/accommodations/" + encodeUrlPathSegment(accommodation.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, accommodationService.findAccommodation(id));
        return "accommodations/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Accommodation accommodation = accommodationService.findAccommodation(id);
        accommodationService.deleteAccommodation(accommodation);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/accommodations";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("accommodation_startdate_date_format","yyyy-MM-dd");
        uiModel.addAttribute("accommodation_enddate_date_format","yyyy-MM-dd");
    }

	void populateEditForm(Model uiModel, Accommodation accommodation) {
        uiModel.addAttribute("accommodation", accommodation);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("people", personService.findAllPeople());
        uiModel.addAttribute("rooms", roomService.findAllRooms());
    }

	
	
	
	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
}
