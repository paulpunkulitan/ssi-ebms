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
import ph.com.smesoft.ebms.domain.SubCategory;
import ph.com.smesoft.ebms.repository.IndustrytypeRepository;
import ph.com.smesoft.ebms.repository.SubcategoryRepository;

@Service
@Transactional
public class SubcategoryServiceImpl implements SubcategoryService{
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
   SubcategoryRepository SubCategoryRepository;

	public long countAllSubCategory() {
	      return  SubCategoryRepository.count();
	  }

		public void deleteSubCategory(SubCategory SubCategory) {
			SubCategoryRepository.delete(SubCategory);
	  }

		public SubCategory findSubCategory(Long id) {
	      return SubCategoryRepository.findOne(id);
	  }

		public List<SubCategory> findAllSubCategory() {
	      return SubCategoryRepository.findAll();
	  }

		public List<SubCategory> findSubCategoryEntries(int firstResult, int maxResults) {
	      return SubCategoryRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
	  }

		public void saveSubCategory(SubCategory SubCategory) {
	      SubCategoryRepository.save(SubCategory);
	  }

		public SubCategory updateSubCategory(SubCategory SubCategory) {
	      return SubCategoryRepository.save(SubCategory);
	  }
		
		public List<SubCategory> findSubCategorybySubCategoryId(String searchString){
		    TypedQuery<SubCategory> searchResult = em.createNamedQuery("findCategoryById", SubCategory.class);
		    searchResult.setParameter("searchString",'%'+searchString+'%');
		    List<SubCategory> result=searchResult.getResultList();
		    return result;
		 }
		
		public List<SubCategory> findSubCategoryByCategoryId(Long id){
			TypedQuery<SubCategory> searchResult = em.createNamedQuery("findSubCategoryByCategoryId", SubCategory.class);
			searchResult.setParameter("categoryId", id);
			List<SubCategory> result = searchResult.getResultList();
			return result;
		}
		
		public boolean checkRegex(String input, String user_pattern){
			Pattern pattern = Pattern.compile(user_pattern);
			Matcher matcher;
			
			  matcher = pattern.matcher(input);
			  return matcher.matches();
		}
}
