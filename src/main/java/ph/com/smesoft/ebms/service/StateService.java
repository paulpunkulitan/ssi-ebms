package ph.com.smesoft.ebms.service;

import java.util.List;

import ph.com.smesoft.ebms.domain.State;

public interface StateService {

	public abstract long countAllStates();


	public abstract void deleteState(State state);


	public abstract State findState(Long id);


	public abstract List<State> findAllStates();


	public abstract List<State> findStateEntries(int firstResult, int maxResults);


	public abstract void saveState(State State);


	public abstract State updateState(State state);

    //public abstract List<Floor> findFloorbyFloorNumber(String searchKeyword);
    //public abstract String findFloorbyFloorNumber(String searchString);
	
	public abstract List<State> findAllStatesBySearch(String searchString);
	
	public abstract List<State> findAllStatesByCountryId(Long countryId);

}
