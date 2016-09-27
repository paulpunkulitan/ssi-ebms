package ph.com.smesoft.hms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.com.smesoft.hms.domain.Room;
import ph.com.smesoft.hms.repository.RoomRepository;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {

	@Autowired
    RoomRepository roomRepository;

	public long countAllRooms() {
        return roomRepository.count();
    }

	public void deleteRoom(Room room) {
        roomRepository.delete(room);
    }

	public Room findRoom(Long id) {
        return roomRepository.findOne(id);
    }

	public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

	public List<Room> findRoomEntries(int firstResult, int maxResults) {
        return roomRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveRoom(Room room) {
        roomRepository.save(room);
    }

	public Room updateRoom(Room room) {
        return roomRepository.save(room);
    }
}
