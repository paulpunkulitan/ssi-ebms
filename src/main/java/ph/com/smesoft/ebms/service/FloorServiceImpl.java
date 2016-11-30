package ph.com.smesoft.ebms.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.com.smesoft.ebms.domain.Floor;
import ph.com.smesoft.ebms.repository.FloorRepository;

@Service
@Transactional
public class FloorServiceImpl implements FloorService {
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
    FloorRepository floorRepository;

	public long countAllFloors() {
        return floorRepository.count();
    }

	public void deleteFloor(Floor floor) {
        floorRepository.delete(floor);
    }

	public Floor findFloor(Long id) {
        return floorRepository.findOne(id);
    }

	public List<Floor> findAllFloors() {
        return floorRepository.findAll();
    }

	public List<Floor> findFloorEntries(int firstResult, int maxResults) {
        return floorRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveFloor(Floor floor) {
        floorRepository.save(floor);
    }

	public Floor updateFloor(Floor floor) {
        return floorRepository.save(floor);
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
	
	public List<Floor> findFloorbyFloorNumber(String searchString){
	    TypedQuery<Floor> searchResult = em.createNamedQuery("findFloorByFloorNum", Floor.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<Floor> result=searchResult.getResultList();
	    return result;
	 }
}
