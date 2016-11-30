package ph.com.smesoft.ebms.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.com.smesoft.ebms.domain.Customertype;
import ph.com.smesoft.ebms.repository.CustomertypeRepository;

@Service
@Transactional
public class CustomertypeServiceImpl implements CustomertypeService{
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
    CustomertypeRepository customerTypeRepository;

	public long countAllCustomertypes() {
       return customerTypeRepository.count();
   }

	public void deleteCustomertype(Customertype customerType) {
       customerTypeRepository.delete(customerType);
   }

	public Customertype findCustomertype(Long id) {
       return customerTypeRepository.findOne(id);
   }

	public List<Customertype> findAllCustomertypes() {
       return customerTypeRepository.findAll();
   }
	
	public List<Customertype> findAllCustomertypesName() {
	       return customerTypeRepository.findAll();
	}
	
	public List<Customertype> findCustomertypeEntries(int firstResult, int maxResults) {
       return customerTypeRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
   }

	public void saveCustomertype(Customertype customerType) {
       customerTypeRepository.save(customerType);
   }

	public Customertype updateCustomertype(Customertype customerType) {
       return customerTypeRepository.save(customerType);
   }
	
	/*public List<Floor> findFloorbyFloorNumber(String searchKeyword){
	    TypedQuery<Floor> searchResult = em.createNamedQuery("findFloorByFloorNum", Floor.class);
	    searchResult.setParameter("searchKeyword",'%'+searchKeyword+'%');
	    List<Floor> result=searchResult.getResultList();
	    return result;
	 }*/
/*	public String findFloorbyFloorNumber(String searchString){
	    TypedQuery<String> searchResult = em.createNamedQuery("findFloorByFloorNum", String.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    String result=searchResult.getSingleResult();
	    return result;
	 }*/
	
	public List<Customertype> findCustomertypebyCustomertypeNumber(String searchString){
	    TypedQuery<Customertype> searchResult = em.createNamedQuery("findCustomertypeByCustomertypeNum", Customertype.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<Customertype> result=searchResult.getResultList();
	    return result;
	 }
	

	
	public long checkIfCustomerTypeExist(String searchString){
	    TypedQuery<Customertype> searchResult = em.createNamedQuery("countCustomerType", Customertype.class);
	    searchResult.setParameter("search",searchString);
	    List<Customertype> result = searchResult.getResultList();
	    int count = result.size();
	    return count;
	 }
	
	public boolean checkRegex(String input, String user_pattern){
		Pattern pattern = Pattern.compile(user_pattern);
		Matcher matcher;
		
		  matcher = pattern.matcher(input);
		  return matcher.matches();
	}

	public List<Customertype> filterCustomerType(String searchString) {
		  TypedQuery<Customertype> searchResult = em.createNamedQuery("filterCustomerByCustomerType", Customertype.class);
		    searchResult.setParameter("search", searchString);
		    List<Customertype> result=searchResult.getResultList();
		    return result;
	}
}
