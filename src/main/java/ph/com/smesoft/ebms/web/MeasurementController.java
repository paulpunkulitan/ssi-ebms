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

import ph.com.smesoft.ebms.domain.Measurement;
import ph.com.smesoft.ebms.dto.SearchForm;
import ph.com.smesoft.ebms.service.BusinessService;
import ph.com.smesoft.ebms.service.MeasurementService;

@Controller
@RequestMapping("/measurements")
public class MeasurementController {

	@Autowired
    MeasurementService measurementService;
	
	@Autowired
	BusinessService businessService;
	
////////////CREATE FLOOR
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Measurement measurement, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
       
		if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, measurement);
            return "measurements/create";
        }
		
		if(measurementService.checkIfMeasurementExist(measurement.getMeasurementName().trim()) > 0){
        	 populateEditForm(uiModel, measurement);
        	 bindingResult.reject("measurement", "Measurement Already Exist");
         	
	        	 //uiModel.asMap().clear();
	             
        	System.out.println("meron na");
             return "measurements/create";
        }
		 
		
		if(!measurementService.checkRegex(measurement.getMeasurementName().trim(), "^([^0-9]*)$")){
        	 populateEditForm(uiModel, measurement);
        	 //uiModel.asMap().clear();
        	 bindingResult.reject("measurement", "Invalid entry of Characters");
         	
             
        	  return "measurements/create";
        }
        
        uiModel.asMap().clear();
        measurementService.saveMeasurement(measurement);
        return "redirect:/measurements/" + encodeUrlPathSegment(measurement.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Measurement());
        return "measurements/create";
    }

//////////SHOW SPECIFIC FLOOR
	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("measurement", measurementService.findMeasurement(id));
        uiModel.addAttribute("itemId", id);
        return "measurements/show";
    }

//////////SHOW LIST OF FLOOR
	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("measurements", Measurement.findMeasurementEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) measurementService.countAllMeasurements() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("measurements", Measurement.findAllMeasurements(sortFieldName, sortOrder));
        }
        return "measurements/list";
    }

/////////////UPDATE SPECIFIC FLOOR	
	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Measurement measurement, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, measurement);
            return "measurements/update";
        }
        uiModel.asMap().clear();
        measurementService.updateMeasurement(measurement);
        return "redirect:/measurements/" + encodeUrlPathSegment(measurement.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, measurementService.findMeasurement(id));
        return "measurements/update";
    }

//////////////DELETE SPECIFIC FLOOR	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Measurement measurement = measurementService.findMeasurement(id);
        measurementService.deleteMeasurement(measurement);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/measurements";
    }

	void populateEditForm(Model uiModel, Measurement measurement) {
        uiModel.addAttribute("measurement", measurement);
        uiModel.addAttribute("businesses", businessService.findAllBusinesses());
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
            Measurement measurement = measurementService.findMeasurement(id);
            if (measurement == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<String>(measurement.toJson(), headers, HttpStatus.OK);
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
            List<Measurement> result = measurementService.findAllMeasurements();
            return new ResponseEntity<String>(Measurement.toJsonArray(result), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            Measurement measurement = Measurement.fromJsonToMeasurement(json);
            measurementService.saveMeasurement(measurement);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+measurement.getId().toString()).build().toUriString());
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
            for (Measurement measurement: Measurement.fromJsonArrayToMeasurements(json)) {
                measurementService.saveMeasurement(measurement);
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
            Measurement measurement = Measurement.fromJsonToMeasurement(json);
            measurement.setId(id);
            if (measurementService.updateMeasurement(measurement) == null) {
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
            Measurement measurement = measurementService.findMeasurement(id);
            if (measurement == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            measurementService.deleteMeasurement(measurement);
            return new ResponseEntity<String>(headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
/*
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public List<Measurement> listofMeasurement(@RequestParam("measurementNumber") String searchKeyword) {
		System.out.println("First");
		List<Measurement> searchResult = measurementService.findMeasurementbyMeasurementNumber(searchKeyword);
		System.out.println("2nd");
		System.out.println("THird" + searchResult);
		return searchResult;
	}*/
	
	/*@RequestMapping(value = "/search", method = { RequestMethod.GET })
	public ResponseEntity<String> listofMeasurement(@ModelAttribute("SearchCriteria") SearchForm searchForm) {
		HttpHeaders headers = new HttpHeaders();
		System.out.println("First");
		String searchResult = measurementService.findMeasurementbyMeasurementNumber(searchForm.getSearchString());
		System.out.println("2nd");
		System.out.println("THird" + searchResult);
		//return searchResult;
		return new ResponseEntity<String>(searchResult,headers, HttpStatus.OK);
	}*/
	
	@RequestMapping(value = "/search", method = { RequestMethod.GET })
	public String listofMeasurement(@ModelAttribute("SearchCriteria") SearchForm searchForm, Model uiModel) {
		uiModel.addAttribute("measurements", measurementService.findMeasurementByMeasurementNum(searchForm.getSearchString()));
		return "measurements/list";
	}
}
