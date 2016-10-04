package ph.com.smesoft.hms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.com.smesoft.hms.domain.Door;
import ph.com.smesoft.hms.repository.DoorRepository;

@Service
@Transactional
public class DoorServiceImpl implements DoorService {

	@Autowired
    DoorRepository doorRepository;

	public long countAllDoors() {
        return doorRepository.count();
    }

	public void deleteDoor(Door door) {
        doorRepository.delete(door);
    }

	public Door findDoor(Long id) {
        return doorRepository.findOne(id);
    }

	public List<Door> findAllDoors() {
        return doorRepository.findAll();
    }

	public List<Door> findDoorEntries(int firstResult, int maxResults) {
        return doorRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveDoor(Door door) {
        doorRepository.save(door);
    }

	public Door updateDoor(Door door) {
        return doorRepository.save(door);
    }
}
