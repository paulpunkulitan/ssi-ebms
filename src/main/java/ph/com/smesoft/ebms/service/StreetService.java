package ph.com.smesoft.ebms.service;

import java.util.List;

import ph.com.smesoft.ebms.domain.Street;

public interface StreetService {

	public abstract long countAllStreets();


	public abstract void deleteStreet(Street street);


	public abstract Street findStreet(Long id);


	public abstract List<Street> findAllStreets();


	public abstract List<Street> findStreetEntries(int firstResult, int maxResults);


	public abstract void saveStreet(Street street);


	public abstract Street updateStreet(Street street);

    //public abstract List<Floor> findFloorbyFloorNumber(String searchKeyword);
    //public abstract String findFloorbyFloorNumber(String searchString);
	
	public abstract List<Street> findStreetbyStreetNumber(String searchString);
	
	public abstract List<Street> findStreetByBarangayId(Long search);
		

}
