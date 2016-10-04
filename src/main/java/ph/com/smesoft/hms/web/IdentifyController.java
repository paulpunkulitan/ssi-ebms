package ph.com.smesoft.hms.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import ph.com.smesoft.hms.domain.Floor;

@Controller
@RequestMapping("/identify")
public class IdentifyController {
	@RequestMapping(value= "/{id}", method= RequestMethod.GET)
	public String identifyForm(@PathVariable("id") long id, Model uiModel)
	{
	 String url = "http://localhost:8080/hms/floors/{id}";
	 RestTemplate restTemplate = new RestTemplate();
	 Floor floor = restTemplate.getForObject(url, Floor.class, id);
	 System.out.println(floor);
     uiModel.addAttribute("floor", floor);
	 return "identify/listidentify";
	}
	
}
