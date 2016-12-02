package ph.com.smesoft.ebms.service;

import java.util.List;

import ph.com.smesoft.ebms.domain.Area;

public interface AreaService {

	public abstract long countAllAreas();


	public abstract void deleteArea(Area area);


	public abstract Area findArea(Long id);


	public abstract List<Area> findAllAreas();


	public abstract List<Area> findAreaEntries(int firstResult, int maxResults);


	public abstract void saveArea(Area area);


	public abstract Area updateArea(Area area);

    //public abstract List<Floor> findFloorbyFloorNumber(String searchKeyword);
    //public abstract String findFloorbyFloorNumber(String searchString);
	
	public abstract List<Area> findAllAreasBySearch(String searchString);
	
	public abstract List<Area> findAllAreasByStreetId(Long search);

}
