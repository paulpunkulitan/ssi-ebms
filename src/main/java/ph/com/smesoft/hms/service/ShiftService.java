package ph.com.smesoft.hms.service;
import java.util.List;

import ph.com.smesoft.hms.domain.Shift;

public interface ShiftService {

	public abstract long countAllShifts();


	public abstract void deleteShift(Shift shift);


	public abstract Shift findShift(Long id);


	public abstract List<Shift> findAllShifts();


	public abstract List<Shift> findShiftEntries(int firstResult, int maxResults);


	public abstract void saveShift(Shift shift);


	public abstract Shift updateShift(Shift shift);

}
