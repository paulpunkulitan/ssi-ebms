package ph.com.smesoft.hms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.com.smesoft.hms.domain.Shift;
import ph.com.smesoft.hms.repository.ShiftRepository;

@Service
@Transactional
public class ShiftServiceImpl implements ShiftService {

	@Autowired
    ShiftRepository shiftRepository;

	public long countAllShifts() {
        return shiftRepository.count();
    }

	public void deleteShift(Shift shift) {
        shiftRepository.delete(shift);
    }

	public Shift findShift(Long id) {
        return shiftRepository.findOne(id);
    }

	public List<Shift> findAllShifts() {
        return shiftRepository.findAll();
    }

	public List<Shift> findShiftEntries(int firstResult, int maxResults) {
        return shiftRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveShift(Shift shift) {
        shiftRepository.save(shift);
    }

	public Shift updateShift(Shift shift) {
        return shiftRepository.save(shift);
    }
}
