package ph.com.smesoft.ebms.service;

import java.util.List;

import ph.com.smesoft.ebms.domain.ItemCategory;

public interface ItemcategoryService {
	
	public abstract long countAllItemCategory();
	
	public abstract void deleteItemCategory(ItemCategory itemCategory);
	
	public abstract ItemCategory findItemCategory(Long id);
	
	public abstract List<ItemCategory> findAllItemCategory();
	
	public abstract List<ItemCategory> findItemCategoryEntries(int firstResult, int maxResults);
	
	public abstract void saveItemCategory(ItemCategory itemCategory);
	
	public abstract ItemCategory updateItemCategory(ItemCategory itemCategory);
	
	public abstract List<ItemCategory> findItemCategorybyItemCategoryId(String searchString);
	
	public abstract boolean checkRegex(String input, String user_pattern);

}
