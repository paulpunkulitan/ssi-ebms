package ph.com.smesoft.ebms.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.com.smesoft.ebms.domain.Product;
import ph.com.smesoft.ebms.repository.ProductRepository;
@Service
@Transactional
public class ProductServiceImpl implements ProductService{
	
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
	ProductRepository productRepository;
	
	public long countAllProducts() {
        return productRepository.count();
    }
	
	public void deleteProduct(Product Product) {
		productRepository.delete(Product);
    }
	
	public Product findProduct(Long id) {
        return productRepository.findOne(id);
    }

	public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

	public List<Product> findProductEntries(int firstResult, int maxResults) {
        return productRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveProduct(Product Product) {
		productRepository.save(Product);
    }

	public Product updateProduct(Product Product) {
        return productRepository.save(Product);
    }
	
	public List<Product> findProductBySearch(String searchString){
	    TypedQuery<Product> searchResult = em.createNamedQuery("findProductByProductNum", Product.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<Product> result=searchResult.getResultList();
	    return result;
	 }



	

	
}
