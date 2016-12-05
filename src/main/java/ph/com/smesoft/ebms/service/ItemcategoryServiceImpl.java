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

import ph.com.smesoft.ebms.domain.Industrytype;
import ph.com.smesoft.ebms.domain.ItemCategory;
import ph.com.smesoft.ebms.repository.IndustrytypeRepository;
import ph.com.smesoft.ebms.repository.ItemcategoryRepository;

@Service
@Transactional
public class ItemcategoryServiceImpl implements ItemcategoryService{
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
   ItemcategoryRepository itemCategoryRepository;

	public long countAllItemCategory() {
      return  itemCategoryRepository.count();
  }

	public void deleteItemCategory(ItemCategory itemCategory) {
		itemCategoryRepository.delete(itemCategory);
  }

	public ItemCategory findItemCategory(Long id) {
      return itemCategoryRepository.findOne(id);
  }

	public List<ItemCategory> findAllItemCategory() {
      return itemCategoryRepository.findAll();
  }

	public List<ItemCategory> findItemCategoryEntries(int firstResult, int maxResults) {
      return itemCategoryRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
  }

	public void saveItemCategory(ItemCategory itemCategory) {
      itemCategoryRepository.save(itemCategory);
  }

	public ItemCategory updateItemCategory(ItemCategory itemCategory) {
      return itemCategoryRepository.save(itemCategory);
  }
	
	public List<ItemCategory> findItemCategorybyItemCategoryId(String searchString){
	    TypedQuery<ItemCategory> searchResult = em.createNamedQuery("findCategoryById", ItemCategory.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<ItemCategory> result=searchResult.getResultList();
	    return result;
	 }
	
	
	
	public boolean checkRegex(String input, String user_pattern){
		Pattern pattern = Pattern.compile(user_pattern);
		Matcher matcher;
		
		  matcher = pattern.matcher(input);
		  return matcher.matches();
	}
}
