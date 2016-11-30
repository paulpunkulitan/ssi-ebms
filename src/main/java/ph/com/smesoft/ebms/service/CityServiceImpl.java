package ph.com.smesoft.ebms.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.com.smesoft.ebms.domain.City;
import ph.com.smesoft.ebms.repository.CityRepository;

@Service
@Transactional
public class CityServiceImpl implements CityService {
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
    CityRepository cityRepository;

	public long countAllCities() {
        return cityRepository.count();
    }

	public void deleteCity(City city) {
        cityRepository.delete(city);
    }

	public City findCity(Long id) {
        return cityRepository.findOne(id);
    }

	public List<City> findAllCities() {
        return cityRepository.findAll();
    }

	public List<City> findCityEntries(int firstResult, int maxResults) {
        return cityRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveCity(City city) {
        cityRepository.save(city);
    }

	public City updateCity(City city) {
        return cityRepository.save(city);
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
	
	public List<City> findAllCitiesBySearch(String searchString){
	    TypedQuery<City> searchResult = em.createNamedQuery("findAllCitiesBySearch", City.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<City> result=searchResult.getResultList();
	    return result;
	 }
}
