package ph.com.smesoft.hms.service;
import java.util.List;

import ph.com.smesoft.hms.domain.Room;

public interface RoomService {

	public abstract long countAllRooms();


	public abstract void deleteRoom(Room room);


	public abstract Room findRoom(Long id);


	public abstract List<Room> findAllRooms();


	public abstract List<Room> findRoomEntries(int firstResult, int maxResults);


	public abstract void saveRoom(Room room);


	public abstract Room updateRoom(Room room);

}
