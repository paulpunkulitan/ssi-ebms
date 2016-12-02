package ph.com.smesoft.ebms.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.com.smesoft.ebms.domain.Street;
import ph.com.smesoft.ebms.repository.StreetRepository;

@Service
@Transactional
public class StreetServiceImpl implements StreetService {
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
    StreetRepository streetRepository;

	public long countAllStreets() {
        return streetRepository.count();
    }

	public void deleteStreet(Street street) {
        streetRepository.delete(street);
    }

	public Street findStreet(Long id) {
        return streetRepository.findOne(id);
    }

	public List<Street> findAllStreets() {
        return streetRepository.findAll();
    }

	public List<Street> findStreetEntries(int firstResult, int maxResults) {
        return streetRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveStreet(Street street) {
        streetRepository.save(street);
    }

	public Street updateStreet(Street street) {
        return streetRepository.save(street);
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
	
	public List<Street> findStreetbyStreetNumber(String searchString){
	    TypedQuery<Street> searchResult = em.createNamedQuery("findStreetByStreetNum", Street.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<Street> result=searchResult.getResultList();
	    return result;
	 }
	
	public List<Street> findStreetByBarangayId(Long search){
		TypedQuery<Street> searchResult = em.createNamedQuery("streetByBarangayId", Street.class);
		searchResult.setParameter("barangayId", search);
		List<Street> result = searchResult.getResultList();
		return result;
	}
}
