package ph.com.smesoft.ebms.service;

import java.util.List;

import ph.com.smesoft.ebms.domain.SubCategory;

public interface SubcategoryService {
	
	public abstract long countAllSubCategory();
	
	public abstract void deleteSubCategory(SubCategory SubCategory);
	
	public abstract SubCategory findSubCategory(Long id);
	
	public abstract List<SubCategory> findAllSubCategory();
	
	public abstract List<SubCategory> findSubCategoryEntries(int firstResult, int maxResults);
	
	public abstract void saveSubCategory(SubCategory SubCategory);
	
	public abstract SubCategory updateSubCategory(SubCategory SubCategory);
	
	public abstract List<SubCategory> findSubCategorybySubCategoryId(String searchString);
	
	public abstract boolean checkRegex(String input, String user_pattern);

}
