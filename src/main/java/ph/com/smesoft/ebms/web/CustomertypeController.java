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

import ph.com.smesoft.ebms.domain.Customertype;
import ph.com.smesoft.ebms.dto.SearchForm;
import ph.com.smesoft.ebms.service.CustomertypeService;

@Controller
@RequestMapping("/customertypes")
public class CustomertypeController {

	@Autowired
    CustomertypeService customertypeService;

	
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Customertype customertype, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		
		if(customertypeService.checkIfCustomerTypeExist(customertype.getCustomerTypeName().trim()) > 0){
			 bindingResult.reject("customertype", "Customer Type already exists.");
			 
			 populateEditForm(uiModel, customertype);
	        	 //uiModel.asMap().clear();
	             
             return "customertypes/create";
        }
	    if(!customertypeService.checkRegex(customertype.getCustomerTypeName().trim(), "^([^0-9]*)$")){
	    	 bindingResult.reject("customertype", "Invalid entry of Characters");
	    	 populateEditForm(uiModel, customertype);
	        	 //uiModel.asMap().clear();
	         return "customertypes/create";
	    } 
	     
		if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, customertype);
            return "customertypes/create";
        }
        
        /*if((int) customertypeService.checkIfCustomerTypeExist(customertype.getCustomerTypeName()) > 0){
            bindingResult.reject("customertype", "Floor Number already exists.");
            return "customertype/create";
        } */
        
        if(!customertypeService.checkRegex(customertype.getCustomerTypeName().trim(), "^([^0-9]*)$")){
        	 populateEditForm(uiModel, customertype);
        	 //uiModel.asMap().clear();
             
        	  return "customertypes/create";
        }
        
        uiModel.asMap().clear();
        customertypeService.saveCustomertype(customertype);
        return "redirect:/customertypes/" + encodeUrlPathSegment(customertype.getId().toString(), httpServletRequest);
    }
	/*@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Customertype customertype, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, customertype);
            return "customertypes/create";
        }
        uiModel.asMap().clear();
        customertypeService.saveCustomertype(customertype);
        return "redirect:/customertypes/" + encodeUrlPathSegment(customertype.getId().toString(), httpServletRequest);
    }*/

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Customertype());
        return "customertypes/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("customertype", customertypeService.findCustomertype(id));
        uiModel.addAttribute("itemId", id);
        return "customertypes/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("customertypes", Customertype.findCustomertypeEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) customertypeService.countAllCustomertypes() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("customertypes", Customertype.findAllCustomertypes(sortFieldName, sortOrder));
        }
        return "customertypes/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Customertype customertype, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, customertype);
            return "customertypes/update";
        }
     
	    if(!customertypeService.checkRegex(customertype.getCustomerTypeName().trim(), "^([^0-9]*)$")){
	    	 bindingResult.reject("customertype", "Invalid entry of Characters");
	    	 populateEditForm(uiModel, customertype);
	        	 //uiModel.asMap().clear();
	         return "customertypes/update";
	    } 
	    
        uiModel.asMap().clear();
        customertypeService.updateCustomertype(customertype);
        return "redirect:/customertypes/" + encodeUrlPathSegment(customertype.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, customertypeService.findCustomertype(id));
        return "customertypes/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Customertype customertype = customertypeService.findCustomertype(id);
        customertypeService.deleteCustomertype(customertype);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/customertypes";
    }

	void populateEditForm(Model uiModel, Customertype customertype) {
        uiModel.addAttribute("customertype", customertype);
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
            Customertype customertype = customertypeService.findCustomertype(id);
            if (customertype == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<String>(customertype.toJson(), headers, HttpStatus.OK);
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
            List<Customertype> result = customertypeService.findAllCustomertypes();
            return new ResponseEntity<String>(Customertype.toJsonArray(result), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            Customertype customertype = Customertype.fromJsonToCustomertype(json);
            customertypeService.saveCustomertype(customertype);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+customertype.getId().toString()).build().toUriString());
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
            for (Customertype customertype: Customertype.fromJsonArrayToCustomertypes(json)) {
                customertypeService.saveCustomertype(customertype);
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
            Customertype customertype = Customertype.fromJsonToCustomertype(json);
            customertype.setId(id);
            if (customertypeService.updateCustomertype(customertype) == null) {
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
            Customertype customertype = customertypeService.findCustomertype(id);
            if (customertype == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            customertypeService.deleteCustomertype(customertype);
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
		uiModel.addAttribute("customertypes", customertypeService.findCustomertypebyCustomertypeNumber(searchForm.getSearchString()));
		return "customertypes/list";
	}
}
