package ph.com.smesoft.ebms.service;

import java.util.List;

import ph.com.smesoft.ebms.domain.Brand;

public interface BrandService {
	public abstract long countAllBrands();


	public abstract void deleteBrand(Brand brand);


	public abstract Brand findBrand(Long id);


	public abstract List<Brand> findAllBrands();


	public abstract List<Brand> findBrandEntries(int firstResult, int maxResults);


	public abstract void saveBrand(Brand brand);


	public abstract Brand updateBrand(Brand brand);
	
	public abstract List<Brand> findBrandbyBrandNumber(String searchString);


}