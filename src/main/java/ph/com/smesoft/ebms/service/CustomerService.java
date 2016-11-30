package ph.com.smesoft.ebms.service;

import java.util.List;

import ph.com.smesoft.ebms.domain.Customer;


public interface CustomerService {

	public abstract long countAllCustomer();


	public abstract void deleteCustomer(Customer customer);


	public abstract Customer findCustomer(Long id);


	public abstract List<Customer> findAllCustomer();


	public abstract List<Customer> findCustomerEntries(int firstResult, int maxResults);


	public abstract Customer updateCustomer(Customer customer);


	public abstract void saveCustomer(Customer customer);
	
	public abstract List<Customer> findCustomerbyid(String searchString);
	
}