package ph.com.smesoft.hms.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ph.com.smesoft.hms.domain.Person;
import ph.com.smesoft.hms.repository.PersonRepository;

@Controller
@RequestMapping("/authorize")
public class AuthorizeController {
	
	@Autowired
    PersonRepository personRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	@RequestMapping(value = "/person", headers = "Accept=application/json")
	public ResponseEntity<String> authorizePersonForm() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		try {
			/*String pvId = "PV-0001";
			TypedQuery<String> query = em.createQuery("select PERSONTYPE from PERSON a where a.PVID = :pvId", String.class);
			query.setParameter("pvId", pvId);
			//Person pv = personRepository.findByPvId(pvId);
			//String ptype = @Query("SELECT personType from Person where pvId = " + pvId);
			Object ptype = query.getSingleResult();
			System.out.println(ptype);
			String personType = ptype.toString();
			System.out.println(personType);*/
			String answer = "Yes";
			//System.out.println(pv);
			
			System.out.println(answer);
			return new ResponseEntity<String>(answer, headers, HttpStatus.OK);
			
		} catch (Exception e) {
			System.out.println("Enter error");
			return new ResponseEntity<String>("{\"ERROR\":" + e.getMessage() + "\"}", headers,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
