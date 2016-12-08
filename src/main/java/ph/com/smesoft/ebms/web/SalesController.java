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

import com.google.gson.Gson;

import ph.com.smesoft.ebms.domain.Floor;
import ph.com.smesoft.ebms.domain.ItemCategory;
import ph.com.smesoft.ebms.domain.SubCategory;
import ph.com.smesoft.ebms.domain.Area;
import ph.com.smesoft.ebms.domain.Sales;
import ph.com.smesoft.ebms.dto.SearchForm;
import ph.com.smesoft.ebms.service.SalesService;
import ph.com.smesoft.ebms.service.CustomerService;
import ph.com.smesoft.ebms.service.FloorService;
import ph.com.smesoft.ebms.service.ItemcategoryService;
import ph.com.smesoft.ebms.service.ProductService;
import ph.com.smesoft.ebms.service.SubcategoryService;

@Controller
@RequestMapping("/sales")
public class SalesController {

	@Autowired
    SalesService salesService;
	@Autowired
	ItemcategoryService itemCategoryService;
	@Autowired
	SubcategoryService subCategoryService;
	@Autowired
	CustomerService customerService;
	@Autowired
	ProductService productService;
	

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Sales sales, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, sales);
            return "sales/create";
        }
        uiModel.asMap().clear();
        salesService.saveSales(sales);
        return "redirect:/sales/" + encodeUrlPathSegment(sales.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Sales());
        return "sales/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("sales", salesService.findSales(id));
        uiModel.addAttribute("itemId", id);
        return "sales/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("sales", Sales.findSalesEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) salesService.countAllSales() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("sales", Sales.findAllSales(sortFieldName, sortOrder));
        }
        return "sales/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Sales sales, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm2(uiModel, sales);
            return "sales/update";
        }
        uiModel.asMap().clear();
        salesService.updateSales(sales);
        return "redirect:/sales/" + encodeUrlPathSegment(sales.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm2(uiModel, salesService.findSales(id));
        return "sales/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Sales sales = salesService.findSales(id);
        salesService.deleteSales(sales);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/sales";
    }

	void populateEditForm(Model uiModel, Sales sales) {
        uiModel.addAttribute("sales", sales);
        uiModel.addAttribute("customer", customerService.findAllCustomer());
        uiModel.addAttribute("products", productService.findAllProducts());

      //  uiModel.addAttribute("subcategory", subCategoryService.findAllSubCategory());
    }

	void populateEditForm2(Model uiModel, Sales sales) {
        uiModel.addAttribute("sales", sales);
        uiModel.addAttribute("customer", customerService.findAllCustomer());
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
            Sales sales = salesService.findSales(id);
            if (sales == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<String>(sales.toJson(), headers, HttpStatus.OK);
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
            List<Sales> result = salesService.findAllSales();
            return new ResponseEntity<String>(Sales.toJsonArray(result), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            Sales sales = Sales.fromJsonToSales(json);
            salesService.saveSales(sales);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+sales.getId().toString()).build().toUriString());
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
            for (Sales sales: Sales.fromJsonArrayToSales(json)) {
            	salesService.saveSales(sales);
                
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
           
            Sales sales = Sales.fromJsonToSales(json);
            sales.setId(id);
            
            if (salesService.updateSales(sales) == null) {
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
            Sales sales =  salesService.findSales(id);
            if (sales == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            salesService.deleteSales(sales);
            
            return new ResponseEntity<String>(headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 

	@RequestMapping(value = "/search", method = { RequestMethod.GET })
	 public String listofFloor(@ModelAttribute("SearchCriteria") SearchForm searchForm, Model uiModel) {
		uiModel.addAttribute("sales", salesService.findAllSalesBySearch(searchForm.getSearchString()));
		return "sales/list";
	}

}
