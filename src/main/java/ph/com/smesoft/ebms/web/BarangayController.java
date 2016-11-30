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

import ph.com.smesoft.ebms.domain.Barangay;
import ph.com.smesoft.ebms.dto.SearchForm;
import ph.com.smesoft.ebms.service.BarangayService;
import ph.com.smesoft.ebms.service.CityService;
import ph.com.smesoft.ebms.service.CountryService;
import ph.com.smesoft.ebms.service.StateService;

@Controller
@RequestMapping("/barangays")
public class BarangayController {

	@Autowired
    BarangayService barangayService;
	
	@Autowired
	CityService cityService;
	
	@Autowired
	StateService stateService;
	
	@Autowired
	CountryService countryService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Barangay barangay, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, barangay);
            return "barangays/create";
        }
        uiModel.asMap().clear();
        barangayService.saveBarangay(barangay);
        return "redirect:/barangays/" + encodeUrlPathSegment(barangay.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Barangay());
        return "barangays/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("barangay", barangayService.findBarangay(id));
        uiModel.addAttribute("itemId", id);
        return "barangays/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("barangays", Barangay.findBarangayEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) barangayService.countAllBarangays() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("barangays", Barangay.findAllBarangays(sortFieldName, sortOrder));
        }
        return "barangays/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Barangay barangay, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, barangay);
            return "barangays/update";
        }
        uiModel.asMap().clear();
        barangayService.updateBarangay(barangay);
        return "redirect:/barangays/" + encodeUrlPathSegment(barangay.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, barangayService.findBarangay(id));
        return "barangays/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Barangay barangay = barangayService.findBarangay(id);
        barangayService.deleteBarangay(barangay);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/barangays";
    }

	void populateEditForm(Model uiModel, Barangay barangay) {
        uiModel.addAttribute("barangay", barangay);
        uiModel.addAttribute("cities", cityService.findAllCities());
   //     uiModel.addAttribute("countries", countryService.findAllCountries());
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
            Barangay barangay = barangayService.findBarangay(id);
            if (barangay == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<String>(barangay.toJson(), headers, HttpStatus.OK);
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
            List<Barangay> result = barangayService.findAllBarangays();
            return new ResponseEntity<String>(Barangay.toJsonArray(result), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            Barangay barangay = Barangay.fromJsonToBarangay(json);
            barangayService.saveBarangay(barangay);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+barangay.getId().toString()).build().toUriString());
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
            for (Barangay barangay: Barangay.fromJsonArrayToBarangays(json)) {
                barangayService.saveBarangay(barangay);
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
            Barangay barangay = Barangay.fromJsonToBarangay(json);
            barangay.setId(id);
            if (barangayService.updateBarangay(barangay) == null) {
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
            Barangay barangay = barangayService.findBarangay(id);
            if (barangay == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            barangayService.deleteBarangay(barangay);
            return new ResponseEntity<String>(headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
/*
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public List<Floor> listofFloor(@RequestParam("floorNumber") String searchKeyword) {
		System.out.println("First");
		List<Floor> searchResult = floorService.findFloorbyFloorNumber(searchKeyword);
		System.out.println("2nd");
		System.out.println("THird" + searchResult);
		return searchResult;
	}*/
	
	/*@RequestMapping(value = "/search", method = { RequestMethod.GET })
	public ResponseEntity<String> listofFloor(@ModelAttribute("SearchCriteria") SearchForm searchForm) {
		HttpHeaders headers = new HttpHeaders();
		System.out.println("First");
		String searchResult = floorService.findFloorbyFloorNumber(searchForm.getSearchString());
		System.out.println("2nd");
		System.out.println("THird" + searchResult);
		//return searchResult;
		return new ResponseEntity<String>(searchResult,headers, HttpStatus.OK);
	}*/
	
	@RequestMapping(value = "/search", method = { RequestMethod.GET })
	public String listofCity(@ModelAttribute("SearchCriteria") SearchForm searchForm, Model uiModel) {
		uiModel.addAttribute("barangays", barangayService.findBarangaybyBarangayNumber(searchForm.getSearchString()));
		return "barangays/list";
	}
	
	
}
