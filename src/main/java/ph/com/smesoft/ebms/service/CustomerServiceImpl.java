package ph.com.smesoft.ebms.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.com.smesoft.ebms.domain.Customer;

import ph.com.smesoft.ebms.repository.CustomerRepository;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
	
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
	CustomerRepository customerRepository;
	
	public long countAllCustomer() {
        return customerRepository.count();
    }
	
	public void deleteCustomer(Customer customer) {
		customerRepository.delete(customer);
    }
	
	public Customer findCustomer(Long id) {
        return customerRepository.findOne(id);
    }

	public List<Customer> findAllCustomer() {
        return customerRepository.findAll();
    }

	public List<Customer> findCustomerEntries(int firstResult, int maxResults) {
        return customerRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveCustomer(Customer Customer) {
		customerRepository.save(Customer);
    }

	public Customer updateCustomer(Customer Customer) {
        return customerRepository.save(Customer);
    }
	
	public List<Customer> findCustomerbyid(String searchString){
	    TypedQuery<Customer> searchResult = em.createNamedQuery("findCustomerByid", Customer.class);
	    searchResult.setParameter("search",'%'+searchString+'%');
	    List<Customer> result=searchResult.getResultList();
	    return result;
	 }
	
	
}
