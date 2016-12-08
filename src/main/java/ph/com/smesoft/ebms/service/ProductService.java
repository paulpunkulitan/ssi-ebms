package ph.com.smesoft.ebms.service;
import java.util.List;

import ph.com.smesoft.ebms.domain.Product;

public interface ProductService {

	public abstract long countAllProducts();


	public abstract void deleteProduct(Product product);


	public abstract Product findProduct(Long id);


	public abstract List<Product> findAllProducts();


	public abstract List<Product> findProductEntries(int firstResult, int maxResults);


	public abstract void saveProduct(Product products);


	public abstract Product updateProduct(Product products);

	
	public abstract List<Product> findProductBySearch(String searchString);

}
