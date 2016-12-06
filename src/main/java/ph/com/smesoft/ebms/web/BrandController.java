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
import ph.com.smesoft.ebms.domain.Brand;
import ph.com.smesoft.ebms.dto.SearchForm;
import ph.com.smesoft.ebms.service.BrandService;
import ph.com.smesoft.ebms.service.FloorService;
import ph.com.smesoft.ebms.service.ItemcategoryService;
import ph.com.smesoft.ebms.service.SubcategoryService;

@Controller
@RequestMapping("/brand")
public class BrandController {

	@Autowired
    BrandService brandService;
	@Autowired
	ItemcategoryService itemCategoryService;
	@Autowired
	SubcategoryService subCategoryService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Brand brand, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, brand);
            return "brand/create";
        }
        uiModel.asMap().clear();
        brandService.saveBrand(brand);
        return "redirect:/brand/" + encodeUrlPathSegment(brand.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Brand());
        return "brand/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("brand", brandService.findBrand(id));
        uiModel.addAttribute("itemId", id);
        return "brand/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("brand", Brand.findBrandNameEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) brandService.countAllBrands() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("brand", Brand.findAllBrand(sortFieldName, sortOrder));
        }
        return "brand/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Brand brand, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm2(uiModel, brand);
            return "brand/update";
        }
        uiModel.asMap().clear();
        brandService.updateBrand(brand);
        return "redirect:/brand/" + encodeUrlPathSegment(brand.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm2(uiModel, brandService.findBrand(id));
        return "brand/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Brand brand = brandService.findBrand(id);
        brandService.deleteBrand(brand);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/brand";
    }

	void populateEditForm(Model uiModel, Brand brand) {
        uiModel.addAttribute("brand", brand);
        uiModel.addAttribute("category", itemCategoryService.findAllItemCategory());
      //  uiModel.addAttribute("subcategory", subCategoryService.findAllSubCategory());
    }

	void populateEditForm2(Model uiModel, Brand brand) {
        uiModel.addAttribute("brand", brand);
        uiModel.addAttribute("category", itemCategoryService.findAllItemCategory());
        uiModel.addAttribute("subcategory", subCategoryService.findAllSubCategory());
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
            Brand brand = brandService.findBrand(id);
            if (brand == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<String>(brand.toJson(), headers, HttpStatus.OK);
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
            List<Brand> result = brandService.findAllBrands();
            return new ResponseEntity<String>(Brand.toJsonArray(result), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            Brand brand = Brand.fromJsonToBrand(json);
            brandService.saveBrand(brand);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+brand.getId().toString()).build().toUriString());
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
            for (Brand brand: Brand.fromJsonArrayToBrand(json)) {
            	brandService.saveBrand(brand);
                
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
           
            Brand brand = Brand.fromJsonToBrand(json);
            brand.setId(id);
            
            if (brandService.updateBrand(brand) == null) {
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
            Brand brand =  brandService.findBrand(id);
            if (brand == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            brandService.deleteBrand(brand);
            
            return new ResponseEntity<String>(headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 

	@RequestMapping(value = "/search", method = { RequestMethod.GET })
	 public String listofFloor(@ModelAttribute("SearchCriteria") SearchForm searchForm, Model uiModel) {
		uiModel.addAttribute("brand", brandService.findBrandbyBrandNumber(searchForm.getSearchString()));
		return "brand/list";
	}
	
	@RequestMapping(value="/{categoryId}", method = RequestMethod.GET)
	public ResponseEntity<String> getSubCategoryByCategoryId(@PathVariable Integer categoryId, Model uiModel){
		HttpHeaders headers = new HttpHeaders();
		Long categoryIdtoLong  = Long.valueOf(categoryId.longValue());
		List<SubCategory> subCategoryName = subCategoryService.findSubCategoryByCategoryId(categoryIdtoLong);
		String json = new Gson().toJson(subCategoryName);
		
		return new ResponseEntity<String>(json, headers, HttpStatus.OK);
	}
}
