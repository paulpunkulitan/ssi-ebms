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

import ph.com.smesoft.ebms.domain.Locationtype;
import ph.com.smesoft.ebms.repository.LocationtypeRepository;

@Service 
@Transactional
public class LocationtypeServiceImpl implements LocationtypeService{
	
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
	LocationtypeRepository locationTypeRepository;
	
	public long countAllLocationTypes() {
        return locationTypeRepository.count();
    }
	
	public void deleteLocationType(Locationtype LocationType) {
		locationTypeRepository.delete(LocationType);
    }
	
	public Locationtype findLocationType(Long id) {
        return locationTypeRepository.findOne(id);
    }

	public List<Locationtype> findAllLocationTypes() {
        return locationTypeRepository.findAll();
    }

	public List<Locationtype> findLocationTypeEntries(int firstResult, int maxResults) {
        return locationTypeRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveLocationType(Locationtype LocationType) {
		locationTypeRepository.save(LocationType);
    }

	public Locationtype updateLocationType(Locationtype LocationType) {
        return locationTypeRepository.save(LocationType);
    }
	
	public List<Locationtype> findLocationtypeByLocationtypeNum(String searchString){
	    TypedQuery<Locationtype> searchResult = em.createNamedQuery("findLocationtypeByLocationtypeNum", Locationtype.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<Locationtype> result=searchResult.getResultList();
	    return result;
	 }


	public long checkIfLocationTypeExist(String search) {
		
		TypedQuery<Locationtype> searchResult = em.createNamedQuery("countLocationType", Locationtype.class);
	    searchResult.setParameter("search",search);
	    List<Locationtype> result = searchResult.getResultList();
	    int count = result.size();
	    return count;
	}

	public boolean checkRegex(String input, String user_pattern) {
		Pattern pattern = Pattern.compile(user_pattern);
		Matcher matcher;
		
		  matcher = pattern.matcher(input);
		  return matcher.matches();
	}
}
