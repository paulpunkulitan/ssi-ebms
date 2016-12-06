package ph.com.smesoft.ebms.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.com.smesoft.ebms.domain.Brand;
import ph.com.smesoft.ebms.repository.BrandRepository;
import ph.com.smesoft.ebms.domain.Brand;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {
	
	@PersistenceContext
	public EntityManager em;
	
	
	@Autowired
	BrandRepository brandRepository;
	
	public long countAllBrands() {
        return brandRepository.count();
    }

	public void deleteBrand(Brand brand) {
        brandRepository.delete(brand);
    }

	public Brand findBrand(Long id) {
        return brandRepository.findOne(id);
    }

	public List<Brand> findAllBrands() {
        return brandRepository.findAll();
    }

	public List<Brand> findBrandEntries(int firstResult, int maxResults) {
        return brandRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveBrand(Brand brand) {
        brandRepository.save(brand);
    }

	public Brand updateBrand(Brand brand) {
        return brandRepository.save(brand);
    }
	
	public List<Brand> findBrandbyBrandNumber(String searchString){
	    TypedQuery<Brand> searchResult = em.createNamedQuery("findBrandByBrandNum", Brand.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<Brand> result=searchResult.getResultList();
	    return result;
	 }


	

}
