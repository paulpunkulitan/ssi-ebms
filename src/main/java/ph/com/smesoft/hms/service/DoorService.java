package ph.com.smesoft.hms.service;
import java.util.List;
import ph.com.smesoft.hms.domain.Door;

public interface DoorService {

	public abstract long countAllDoors();


	public abstract void deleteDoor(Door door);


	public abstract Door findDoor(Long id);


	public abstract List<Door> findAllDoors();


	public abstract List<Door> findDoorEntries(int firstResult, int maxResults);


	public abstract void saveDoor(Door door);


	public abstract Door updateDoor(Door door);

}
