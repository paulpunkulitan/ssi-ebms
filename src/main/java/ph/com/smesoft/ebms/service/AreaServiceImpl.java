package ph.com.smesoft.ebms.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.com.smesoft.ebms.domain.Area;
import ph.com.smesoft.ebms.repository.AreaRepository;

@Service
@Transactional
public class AreaServiceImpl implements AreaService {
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
    AreaRepository areaRepository;

	public long countAllAreas() {
        return areaRepository.count();
    }

	public void deleteArea(Area area) {
		areaRepository.delete(area);
    }

	public Area findArea(Long id) {
        return areaRepository.findOne(id);
    }

	public List<Area> findAllAreas() {
        return areaRepository.findAll();
    }

	public List<Area> findAreaEntries(int firstResult, int maxResults) {
        return areaRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveArea(Area area) {
		areaRepository.save(area);
    }

	public Area updateArea(Area area) {
        return areaRepository.save(area);
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
	
	public List<Area> findAllAreasBySearch(String searchString){
	    TypedQuery<Area> searchResult = em.createNamedQuery("findStreetByStreetNum", Area.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<Area> result=searchResult.getResultList();
	    return result;
	 }
}
