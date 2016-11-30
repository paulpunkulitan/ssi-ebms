package ph.com.smesoft.ebms.service;

import java.util.List;

import ph.com.smesoft.ebms.domain.Barangay;

public interface BarangayService {

	public abstract long countAllBarangays();


	public abstract void deleteBarangay(Barangay barangay);


	public abstract Barangay findBarangay(Long id);


	public abstract List<Barangay> findAllBarangays();


	public abstract List<Barangay> findBarangayEntries(int firstResult, int maxResults);


	public abstract void saveBarangay(Barangay barangay);


	public abstract Barangay updateBarangay(Barangay barangay);

    //public abstract List<Floor> findFloorbyFloorNumber(String searchKeyword);
    //public abstract String findFloorbyFloorNumber(String searchString);
	
	public abstract List<Barangay> findBarangaybyBarangayNumber(String searchString);

}
