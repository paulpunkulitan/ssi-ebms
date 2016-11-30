package ph.com.smesoft.ebms.service;

import java.util.List;

import ph.com.smesoft.ebms.domain.Customertype;

public interface CustomertypeService {

		public abstract long countAllCustomertypes();


		public abstract void deleteCustomertype(Customertype barangay);


		public abstract Customertype findCustomertype(Long id);


		public abstract List<Customertype> findAllCustomertypes();

		public abstract List<Customertype> findAllCustomertypesName();
		
		public abstract List<Customertype> findCustomertypeEntries(int firstResult, int maxResults);


		public abstract void saveCustomertype(Customertype barangay);


		public abstract Customertype updateCustomertype(Customertype barangay);

	    //public abstract List<Floor> findFloorbyFloorNumber(String searchKeyword);
	    //public abstract String findFloorbyFloorNumber(String searchString);
		
		public abstract List<Customertype> findCustomertypebyCustomertypeNumber(String searchString);


		public abstract long checkIfCustomerTypeExist(String customerType);
		
		public boolean checkRegex(String input, String user_pattern);
		
		public abstract List<Customertype> filterCustomerType(String searchString);

}
