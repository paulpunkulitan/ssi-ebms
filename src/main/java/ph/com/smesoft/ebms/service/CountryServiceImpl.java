package ph.com.smesoft.ebms.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.com.smesoft.ebms.domain.Country;
import ph.com.smesoft.ebms.repository.CountryRepository;

@Service
@Transactional
public class CountryServiceImpl implements CountryService {
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
    CountryRepository countryRepository;

	public long countAllCountries() {
        return countryRepository.count();
    }

	public void deleteCountry(Country country) {
        countryRepository.delete(country);
    }

	public Country findCountry(Long id) {
        return countryRepository.findOne(id);
    }

	public List<Country> findAllCountries() {
        return countryRepository.findAll();
    }

	public List<Country> findCountryEntries(int firstResult, int maxResults) {
        return countryRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveCountry(Country country) {
        countryRepository.save(country);
    }

	public Country updateCountry(Country country) {
        return countryRepository.save(country);
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
	
	public List<Country> findCountryBySearch(String searchString){
	    TypedQuery<Country> searchResult = em.createNamedQuery("findCountryBySearch", Country.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<Country> result=searchResult.getResultList();
	    return result;
	 }
}
