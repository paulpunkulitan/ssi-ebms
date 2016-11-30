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

import ph.com.smesoft.ebms.domain.Industrytype;
import ph.com.smesoft.ebms.repository.IndustrytypeRepository;

@Service
@Transactional
public class IndustrytypeServiceImpl implements IndustrytypeService{
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
   IndustrytypeRepository industryTypeRepository;

	public long countAllIndustrytypes() {
      return industryTypeRepository.count();
  }

	public void deleteIndustrytype(Industrytype industryType) {
      industryTypeRepository.delete(industryType);
  }

	public Industrytype findIndustrytype(Long id) {
      return industryTypeRepository.findOne(id);
  }

	public List<Industrytype> findAllIndustrytypes() {
      return industryTypeRepository.findAll();
  }

	public List<Industrytype> findIndustrytypeEntries(int firstResult, int maxResults) {
      return industryTypeRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
  }

	public void saveIndustrytype(Industrytype industryType) {
      industryTypeRepository.save(industryType);
  }

	public Industrytype updateIndustrytype(Industrytype industryType) {
      return industryTypeRepository.save(industryType);
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
	
	public List<Industrytype> findIndustrytypebyIndustrytypeNumber(String searchString){
	    TypedQuery<Industrytype> searchResult = em.createNamedQuery("findIndustrytypeByIndustrytypeNum", Industrytype.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<Industrytype> result=searchResult.getResultList();
	    return result;
	 }
	
	public long checkIfIndustryTypeExist(String searchString){
	    TypedQuery<Industrytype> searchResult = em.createNamedQuery("countIndustryType", Industrytype.class);
	    searchResult.setParameter("search",searchString);
	    List<Industrytype> result = searchResult.getResultList();
	    int count = result.size();
	    return count;
	 }
	
	public boolean checkRegex(String input, String user_pattern){
		Pattern pattern = Pattern.compile(user_pattern);
		Matcher matcher;
		
		  matcher = pattern.matcher(input);
		  return matcher.matches();
	}
}
