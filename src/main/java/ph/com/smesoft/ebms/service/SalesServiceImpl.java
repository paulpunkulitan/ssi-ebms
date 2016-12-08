package ph.com.smesoft.ebms.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.com.smesoft.ebms.domain.Sales;
import ph.com.smesoft.ebms.repository.SalesRepository;

@Service
@Transactional
public class SalesServiceImpl implements SalesService {
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
    SalesRepository salesRepository;

	public long countAllSales() {
        return salesRepository.count();
    }

	public void deleteSales(Sales sales) {
		salesRepository.delete(sales);
    }

	public Sales findSales(Long id) {
        return salesRepository.findOne(id);
    }

	public List<Sales> findAllSales() {
        return salesRepository.findAll();
    }

	public List<Sales> findSalesEntries(int firstResult, int maxResults) {
        return salesRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveSales(Sales sales) {
		salesRepository.save(sales);
    }

	public Sales updateSales(Sales sales) {
        return salesRepository.save(sales);
    }
	
	
	public List<Sales> findAllSalesBySearch(String searchString){
	    TypedQuery<Sales> searchResult = em.createNamedQuery("findStreetByStreetNum", Sales.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<Sales> result=searchResult.getResultList();
	    return result;
	 }


}
