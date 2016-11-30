package ph.com.smesoft.ebms.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.com.smesoft.ebms.domain.State;
import ph.com.smesoft.ebms.repository.StateRepository;

@Service
@Transactional
public class StateServiceImpl implements StateService {
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
    StateRepository stateRepository;

	public long countAllStates() {
        return stateRepository.count();
    }

	public void deleteState(State state) {
        stateRepository.delete(state);
    }

	public State findState(Long id) {
        return stateRepository.findOne(id);
    }

	public List<State> findAllStates() {
        return stateRepository.findAll();
    }

	public List<State> findStateEntries(int firstResult, int maxResults) {
        return stateRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveState(State state) {
        stateRepository.save(state);
    }

	public State updateState(State state) {
        return stateRepository.save(state);
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
	
	public List<State> findAllStatesBySearch(String searchString){
	    TypedQuery<State> searchResult = em.createNamedQuery("findAllStatesBySearch", State.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<State> result=searchResult.getResultList();
	    return result;
	 }
}
