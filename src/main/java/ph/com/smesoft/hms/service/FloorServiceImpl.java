package ph.com.smesoft.hms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.com.smesoft.hms.domain.Floor;
import ph.com.smesoft.hms.repository.FloorRepository;

@Service
@Transactional
public class FloorServiceImpl implements FloorService {

	@Autowired
    FloorRepository floorRepository;

	public long countAllFloors() {
        return floorRepository.count();
    }

	public void deleteFloor(Floor floor) {
        floorRepository.delete(floor);
    }

	public Floor findFloor(Long id) {
        return floorRepository.findOne(id);
    }

	public List<Floor> findAllFloors() {
        return floorRepository.findAll();
    }

	public List<Floor> findFloorEntries(int firstResult, int maxResults) {
        return floorRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveFloor(Floor floor) {
        floorRepository.save(floor);
    }

	public Floor updateFloor(Floor floor) {
        return floorRepository.save(floor);
    }
}
