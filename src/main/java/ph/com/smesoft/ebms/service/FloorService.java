package ph.com.smesoft.ebms.service;
import java.util.List;

import ph.com.smesoft.ebms.domain.Floor;

public interface FloorService {

	public abstract long countAllFloors();


	public abstract void deleteFloor(Floor floor);


	public abstract Floor findFloor(Long id);


	public abstract List<Floor> findAllFloors();


	public abstract List<Floor> findFloorEntries(int firstResult, int maxResults);


	public abstract void saveFloor(Floor floor);


	public abstract Floor updateFloor(Floor floor);

    //public abstract List<Floor> findFloorbyFloorNumber(String searchKeyword);
  //  public abstract String findFloorbyFloorNumber(String searchString);
	public abstract List<Floor> findFloorbyFloorNumber(String searchString);

}
