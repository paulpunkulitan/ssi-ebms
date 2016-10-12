package ph.com.smesoft.hms.web;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import ph.com.smesoft.hms.domain.Floor;
import ph.com.smesoft.hms.domain.Person;
import ph.com.smesoft.hms.domain.Room;
import ph.com.smesoft.hms.domain.Shift;
import ph.com.smesoft.hms.reference.Gender;
import ph.com.smesoft.hms.reference.PersonType;
import ph.com.smesoft.hms.service.FloorService;
import ph.com.smesoft.hms.service.PersonService;
import ph.com.smesoft.hms.service.RoomService;
import ph.com.smesoft.hms.service.ShiftService;

@Controller
@RequestMapping("/shifts")
public class ShiftController {

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
            //Shift shift = shiftService.findShift(id);
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
        	
        	Floor floors = new Floor();
        	floors.setId(1L);
        	floors.setFloorNumber("FLR-00001A");
        	floors.setDescription("Floors");
        	floors.setVersion(1);
        	
        	Room rooms = new Room();
        	rooms.setId(1L);
        	rooms.setRoomNumber("RM-00001A");
        	rooms.setDescription("Rooms");
        	rooms.setFloor(floors);
        	rooms.setVersion(1);
        	
        	Set<Floor> titles1 = new HashSet<Floor>(); 
            titles1.add(floors);  
            
            Set<Room> titles2 = new HashSet<Room>(); 
            titles2.add(rooms);  
            
        	Shift shift = new Shift();
        	shift.setId(1L);
        	shift.setPerson(person);
        	shift.setFloors(titles1);
        	shift.setRooms(titles2);
        	shift.setShiftDate(new Date());
        	shift.setVersion(1);
        	
            if (shift == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<String>(shift.toJson(), headers, HttpStatus.OK);
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
            List<Shift> result = shiftService.findAllShifts();
            return new ResponseEntity<String>(Shift.toJsonArray(result), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            Shift shift = Shift.fromJsonToShift(json);
            shiftService.saveShift(shift);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+shift.getId().toString()).build().toUriString());
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
            for (Shift shift: Shift.fromJsonArrayToShifts(json)) {
                shiftService.saveShift(shift);
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
            Shift shift = Shift.fromJsonToShift(json);
            shift.setId(id);
            if (shiftService.updateShift(shift) == null) {
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
            Shift shift = shiftService.findShift(id);
            if (shift == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            shiftService.deleteShift(shift);
            return new ResponseEntity<String>(headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@Autowired
    ShiftService shiftService;

	@Autowired
    FloorService floorService;

	@Autowired
    PersonService personService;

	@Autowired
    RoomService roomService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Shift shift, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, shift);
            return "shifts/create";
        }
        uiModel.asMap().clear();
        shiftService.saveShift(shift);
        return "redirect:/shifts/" + encodeUrlPathSegment(shift.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Shift());
        return "shifts/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("shift", shiftService.findShift(id));
        uiModel.addAttribute("itemId", id);
        return "shifts/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("shifts", Shift.findShiftEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) shiftService.countAllShifts() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("shifts", Shift.findAllShifts(sortFieldName, sortOrder));
        }
        addDateTimeFormatPatterns(uiModel);
        return "shifts/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Shift shift, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, shift);
            return "shifts/update";
        }
        uiModel.asMap().clear();
        shiftService.updateShift(shift);
        return "redirect:/shifts/" + encodeUrlPathSegment(shift.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, shiftService.findShift(id));
        return "shifts/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Shift shift = shiftService.findShift(id);
        shiftService.deleteShift(shift);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/shifts";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("shift_shiftdate_date_format", "yyyy-MM-dd hh:mm:ss a");
    }
	//@DateTimeFormat(pattern = "MMMM dd, yyyy")

	void populateEditForm(Model uiModel, Shift shift) {
        uiModel.addAttribute("shift", shift);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("floors", floorService.findAllFloors());
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
