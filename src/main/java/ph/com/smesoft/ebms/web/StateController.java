package ph.com.smesoft.ebms.web;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import ph.com.smesoft.ebms.domain.Country;
import ph.com.smesoft.ebms.domain.State;
import ph.com.smesoft.ebms.dto.SearchForm;
import ph.com.smesoft.ebms.service.CountryService;
import ph.com.smesoft.ebms.service.StateService;

@Controller
@RequestMapping("/states")
public class StateController {

	@Autowired
    StateService stateService;

	@Autowired
    CountryService countryService;

	long recId;
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid State state, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
        	populateForm(uiModel, state);
            return "states/create";
        }
        uiModel.asMap().clear();
        stateService.saveState(state);
        return "redirect:/states/" + encodeUrlPathSegment(state.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
		populateForm(uiModel, new State());
		return "states/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("state", stateService.findState(id));
        uiModel.addAttribute("itemId", id);
        return "states/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("states", State.findStateEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) stateService.countAllStates() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("states", State.findAllStates(sortFieldName, sortOrder));
        }
        return "states/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid State state, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, state);
            return "states/update";
        }
        uiModel.asMap().clear();
        stateService.updateState(state);
        return "redirect:/states/" + encodeUrlPathSegment(state.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, stateService.findState(id));
        return "states/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        State state = stateService.findState(id);
        stateService.deleteState(state);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/states";
    }

	void populateEditForm(Model uiModel, State state) {
        uiModel.addAttribute("state", state);
        uiModel.addAttribute("countries", countryService.findAllCountries());
        
    }
	
	void populateForm(Model uiModel, State state) {
        	uiModel.addAttribute("state", state);
            uiModel.addAttribute("countries", countryService.findAllCountries());
    }
	/*String roomNumber;
	public void populateRoomNumber(Model uiModel, Room room, String floor) {
		if (floor != null){
        	String floorNumber = floor;
            String sub;
             
            int add = 1;
            int min = 1;
            int max = 9;
            int count = (int) roomService.countRoomsbyFloorNumber(floorNumber);
            add += count;
            
            if(floorNumber.startsWith("00")) {
    	       	 System.out.println("Starts with 00");
    	       	 sub = floorNumber.substring(2);
    	       	 System.out.println("After 2 zeros"+ sub);
           	 	 
    	       	if(add >= min && add <= max){
    	       		System.out.println("Count Room by Floor Number - 1 digit" + "" + add);
    	       		roomNumber = sub + "0" + add;
    	       		System.out.println("Room Number for 1 digit" + "" + roomNumber);
    	       		room.setRoomNumber(roomNumber);
    	       		uiModel.addAttribute("room", room);
    	            uiModel.addAttribute("floors", floorService.findAllFloors());
    	       	}else{
    	       		roomNumber = sub + add;
    	       		System.out.println("Room Number for 2 digit" + "" + roomNumber);
    	       		room.setRoomNumber(roomNumber);
    	       		uiModel.addAttribute("room", room);
    	            uiModel.addAttribute("floors", floorService.findAllFloors());
    	       	}
           }else if(floorNumber.startsWith("0")){
            	sub = floorNumber.substring(1);
           	 	System.out.println("After 1 zero"+ sub);
            	System.out.println("Starts with");
            	
            	if(add >= min && add <= max){
    	       		System.out.println("ELSE IF Count Room by Floor Number - 1 digit" + "" + add);
    	       		roomNumber = sub + "0" + add;
    	       		System.out.println("ELSE IF Room Number for 1 digit" + "" + roomNumber);
    	       		room.setRoomNumber(roomNumber);
    	       		uiModel.addAttribute("room", room);
    	            uiModel.addAttribute("floors", floorService.findAllFloors());
    	       	}else{
    	       		roomNumber = sub + add;
    	       		System.out.println("ELSE IF Room Number for 2 digit" + "" + roomNumber);
    	       		room.setRoomNumber(roomNumber);
    	       		uiModel.addAttribute("room", room);
    	            uiModel.addAttribute("floors", floorService.findAllFloors());
    	       	}
            }else{
            	System.out.println("Floor Number w/o zero"+ " " +floorNumber);
            	
            	if(add >= min && add <= max){
    	       		System.out.println("ELSE Count Room by Floor Number - 1 digit" + "" + add);
    	       		roomNumber = floorNumber + "0" + add;
    	       		System.out.println("ELSE Room Number for 1 digit" + "" + roomNumber);
    	       		room.setRoomNumber(roomNumber);
    	       		uiModel.addAttribute("room", room);
    	            uiModel.addAttribute("floors", floorService.findAllFloors());
    	       	}else{
    	       		roomNumber = floorNumber + add;
    	       		System.out.println("ELSE Room Number for 2 digit" + "" + roomNumber);
    	       		room.setRoomNumber(roomNumber);
    	       		uiModel.addAttribute("room", room);
    	            uiModel.addAttribute("floors", floorService.findAllFloors());
    	       	}
            }
        }else{
        	uiModel.addAttribute("room", room);
            uiModel.addAttribute("floors", floorService.findAllFloors());
        }
	}*/
	
	
/*	//Pass floor Id"/{id}"
	@RequestMapping(value="/{floorId}", method=RequestMethod.GET)
	 public String passFloorId(@PathVariable Integer floorId) {
		Long floorIdtoLong = Long.valueOf(floorId.longValue());
        System.out.println("Floor Id onchange " + "" + floorIdtoLong);
        recId = floorIdtoLong;
        Model uiModel = null;
        Room room = null;
        Floor floor = floorService.findFloor(floorIdtoLong);
        String floorNum = floor.getFloorNumber();
        System.out.println("Sec Floor Information " + "" + floor);
        System.out.println("Floor Number info" + "" + floorNum);
       // populateRoomNumber(uiModel, room, floorNum);
        System.out.println("populateRoomNumber " + roomNumber);
		return roomNumber;
    }*/
	/*
	@RequestMapping(value="/{floorId}", method=RequestMethod.GET)
	 public ResponseEntity<String> passFloorId(@PathVariable Integer floorId) {
		HttpHeaders headers = new HttpHeaders();
		Long floorIdtoLong = Long.valueOf(floorId.longValue());
       System.out.println("Floor Id onchange " + "" + floorIdtoLong);
       recId = floorIdtoLong;
       Model uiModel = null;
       Room room = null;
       Floor floor = floorService.findFloor(floorIdtoLong);
       String floorNum = floor.getFloorNumber();
       System.out.println("Sec Floor Information " + "" + floor);
       System.out.println("Floor Number info" + "" + floorNum);
       //populateRoomNumber(uiModel, room, floorNum);
       String roomNumber = roomService.populateRoomNumber(floorNum);
       System.out.println("populateRoomNumber " + roomNumber);
		//return roomNumber;
       return new ResponseEntity<String>(roomNumber, headers, HttpStatus.OK);
   }
	*/
	
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

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
            State state = stateService.findState(id);
            if (state == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<String>(state.toJson(), headers, HttpStatus.OK);
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
            List<State> result = stateService.findAllStates();
            return new ResponseEntity<String>(State.toJsonArray(result), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            State state = State.fromJsonToState(json);
            stateService.saveState(state);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+state.getId().toString()).build().toUriString());
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
            for (State state: State.fromJsonArrayToStates(json)) {
                stateService.saveState(state);
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
            State state = State.fromJsonToState(json);
            state.setId(id);
            if (stateService.updateState(state) == null) {
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
            State state = stateService.findState(id);
            if (state == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            stateService.deleteState(state);
            return new ResponseEntity<String>(headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@RequestMapping(value = "/search", method = { RequestMethod.GET })
	public String listofPersons(@ModelAttribute("SearchCriteria") SearchForm searchForm,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size ,Model uiModel) {
		
			String search = searchForm.getSearchString();
			if(search != null && !search.isEmpty() && !search.trim().isEmpty()){
				uiModel.addAttribute("states",stateService.findAllStatesBySearch(searchForm.getSearchString()));
				return "states/list";
			}
			else{
				uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
				uiModel.addAttribute("size", (size == null) ? "20" : size.toString());
				return "redirect:/states";
			}
	}
	
	
}
