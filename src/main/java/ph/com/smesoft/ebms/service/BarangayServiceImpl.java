package ph.com.smesoft.ebms.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.com.smesoft.ebms.domain.Barangay;
import ph.com.smesoft.ebms.repository.BarangayRepository;

@Service
@Transactional
public class BarangayServiceImpl implements BarangayService {
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
    BarangayRepository barangayRepository;

	public long countAllBarangays() {
        return barangayRepository.count();
    }

	public void deleteBarangay(Barangay barangay) {
        barangayRepository.delete(barangay);
    }

	public Barangay findBarangay(Long id) {
        return barangayRepository.findOne(id);
    }

	public List<Barangay> findAllBarangays() {
        return barangayRepository.findAll();
    }

	public List<Barangay> findBarangayEntries(int firstResult, int maxResults) {
        return barangayRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveBarangay(Barangay barangay) {
        barangayRepository.save(barangay);
    }

	public Barangay updateBarangay(Barangay barangay) {
        return barangayRepository.save(barangay);
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
	
	public List<Barangay> findBarangaybyBarangayNumber(String searchString){
	    TypedQuery<Barangay> searchResult = em.createNamedQuery("findBarangayByBarangayNum", Barangay.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<Barangay> result=searchResult.getResultList();
	    return result;
	 }
	
	public List<Barangay> findAllBarangayByCityId(Long search){
		TypedQuery<Barangay> searchResult = em.createNamedQuery("barangayByCityId", Barangay.class);
		searchResult.setParameter("cityId", search);
		List<Barangay> result = searchResult.getResultList();
		return result;
	}
}
