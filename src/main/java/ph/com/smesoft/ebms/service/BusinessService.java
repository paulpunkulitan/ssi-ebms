package ph.com.smesoft.ebms.service;

import java.util.List;

import ph.com.smesoft.ebms.domain.Business;

public interface BusinessService {
	public abstract long countAllBusinesss();


	public abstract void deleteBusiness(Business barangay);


	public abstract Business findBusiness(Long id);


	public abstract List<Business> findAllBusinesses();


	public abstract List<Business> findBusinessEntries(int firstResult, int maxResults);


	public abstract void saveBusiness(Business barangay);


	public abstract Business updateBusiness(Business barangay);

    //public abstract List<Floor> findFloorbyFloorNumber(String searchKeyword);
    //public abstract String findFloorbyFloorNumber(String searchString);
	
	public abstract List<Business> findBusinessbyBusinessNumber(String searchString);
	
	public abstract long checkIfBusinessExist(String business);

}
