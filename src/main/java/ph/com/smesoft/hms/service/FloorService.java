package ph.com.smesoft.hms.service;

import java.util.List;

import ph.com.smesoft.hms.domain.Floor;

public interface FloorService {
	
	public abstract long countAllFloors();


	public abstract void deleteFloor(Floor floor);


	public abstract Floor findFloor(Long id);


	public abstract List<Floor> findAllFloors();


	public abstract List<Floor> findFloorEntries(int firstResult, int maxResults);


	public abstract void saveFloor(Floor floor);


	public abstract Floor updateFloor(Floor floor);
}
