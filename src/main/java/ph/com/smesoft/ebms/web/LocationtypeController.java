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
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import ph.com.smesoft.ebms.domain.Locationtype;
import ph.com.smesoft.ebms.dto.SearchForm;
import ph.com.smesoft.ebms.service.LocationtypeService;

@Controller
@RequestMapping("/locationtypes")
public class LocationtypeController {

	@Autowired
    LocationtypeService locationtypeService;
	
////////////CREATE FLOOR
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Locationtype location, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
       
		if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, location);
            return "locationtypes/create";
        }
		
		if(locationtypeService.checkIfLocationTypeExist(location.getLocationTypeName().trim()) > 0){
        	 populateEditForm(uiModel, location);
        	 bindingResult.reject("industrytype", "Location Type Already Exist");
         	
	        	 //uiModel.asMap().clear();
	             
        	System.out.println("meron na");
             return "locationtypes/create";
        }
		 
		
		if(!locationtypeService.checkRegex(location.getLocationTypeName().trim(), "^([^0-9]*)$")){
        	 populateEditForm(uiModel, location);
        	 //uiModel.asMap().clear();
        	 bindingResult.reject("industrytype", "Invalid entry of Characters");
         	
             
        	  return "locationtypes/create";
        }
        
        uiModel.asMap().clear();
        locationtypeService.saveLocationType(location);
        return "redirect:/locationtypes/" + encodeUrlPathSegment(location.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Locationtype());
        return "locationtypes/create";
    }

//////////SHOW SPECIFIC FLOOR
	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("locationtype", locationtypeService.findLocationType(id));
        uiModel.addAttribute("itemId", id);
        return "locationtypes/show";
    }

//////////SHOW LIST OF FLOOR
	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("locationtypes", Locationtype.findLocationtypeEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) locationtypeService.countAllLocationTypes() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("locationtype", Locationtype.findAllLocationtypes(sortFieldName, sortOrder));
        }
        return "locationtypes/list";
    }

/////////////UPDATE SPECIFIC FLOOR	
	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Locationtype location, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, location);
            return "locationtypes/update";
        }
        uiModel.asMap().clear();
        locationtypeService.updateLocationType(location);
        return "redirect:/locationtypes/" + encodeUrlPathSegment(location.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, locationtypeService.findLocationType(id));
        return "locationtypes/update";
    }

//////////////DELETE SPECIFIC FLOOR	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Locationtype location = locationtypeService.findLocationType(id);
        locationtypeService.deleteLocationType(location);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/locationtypes";
    }

	void populateEditForm(Model uiModel, Locationtype location) {
        uiModel.addAttribute("locationtype", location);
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

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
            Locationtype location = locationtypeService.findLocationType(id);
            if (location == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<String>(location.toJson(), headers, HttpStatus.OK);
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
            List<Locationtype> result = locationtypeService.findAllLocationTypes();
            return new ResponseEntity<String>(Locationtype.toJsonArray(result), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            Locationtype location = Locationtype.fromJsonToLocationtype(json);
            locationtypeService.saveLocationType(location);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+location.getId().toString()).build().toUriString());
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
            for (Locationtype location: Locationtype.fromJsonArrayToLocationtypes(json)) {
                locationtypeService.saveLocationType(location);
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
            Locationtype location = Locationtype.fromJsonToLocationtype(json);
            location.setId(id);
            if (locationtypeService.updateLocationType(location) == null) {
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
            Locationtype location = locationtypeService.findLocationType(id);
            if (location == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            locationtypeService.deleteLocationType(location);
            return new ResponseEntity<String>(headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
/*
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public List<Locationtype> listofLocationtype(@RequestParam("locationNumber") String searchKeyword) {
		System.out.println("First");
		List<Locationtype> searchResult = locationtypeService.findLocationtypebyLocationtypeNumber(searchKeyword);
		System.out.println("2nd");
		System.out.println("THird" + searchResult);
		return searchResult;
	}*/
	
	/*@RequestMapping(value = "/search", method = { RequestMethod.GET })
	public ResponseEntity<String> listofLocationtype(@ModelAttribute("SearchCriteria") SearchForm searchForm) {
		HttpHeaders headers = new HttpHeaders();
		System.out.println("First");
		String searchResult = locationtypeService.findLocationtypebyLocationtypeNumber(searchForm.getSearchString());
		System.out.println("2nd");
		System.out.println("THird" + searchResult);
		//return searchResult;
		return new ResponseEntity<String>(searchResult,headers, HttpStatus.OK);
	}*/
	
	@RequestMapping(value = "/search", method = { RequestMethod.GET })
	public String listofLocationtype(@ModelAttribute("SearchCriteria") SearchForm searchForm, Model uiModel) {
		uiModel.addAttribute("locationtypes", locationtypeService.findLocationtypeByLocationtypeNum(searchForm.getSearchString()));
		return "locationtypes/list";
	}
}
