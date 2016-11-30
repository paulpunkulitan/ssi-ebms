package ph.com.smesoft.ebms.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.com.smesoft.ebms.domain.Business;
import ph.com.smesoft.ebms.domain.Customertype;
import ph.com.smesoft.ebms.repository.BusinessRepository;

@Service
@Transactional
public class BusinessServiceImpl implements BusinessService{
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
   BusinessRepository businessRepository;

	public long countAllBusinesss() {
       return businessRepository.count();
   }

	public void deleteBusiness(Business business) {
       businessRepository.delete(business);
   }

	public Business findBusiness(Long id) {
       return businessRepository.findOne(id);
   }

	public List<Business> findAllBusinesses() {
       return businessRepository.findAll();
   }

	public List<Business> findBusinessEntries(int firstResult, int maxResults) {
       return businessRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
   }

	public void saveBusiness(Business business) {
       businessRepository.save(business);
   }

	public Business updateBusiness(Business business) {
       return businessRepository.save(business);
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
	
	public List<Business> findBusinessbyBusinessNumber(String searchString){
	    TypedQuery<Business> searchResult = em.createNamedQuery("findBusinessByBusinessNum", Business.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<Business> result=searchResult.getResultList();
	    return result;
	 }

	public long checkIfBusinessExist(String business) {

           TypedQuery<Business> searchResult = em.createNamedQuery("countBusiness", Business.class);
		    searchResult.setParameter("search",business);
		    List<Business> result = searchResult.getResultList();
		    int count = result.size();
		    return count;	
	}
	
	
}
