package ph.com.smesoft.ebms.service;

import java.util.List;

import ph.com.smesoft.ebms.domain.Industrytype;

public interface IndustrytypeService {
	public abstract long countAllIndustrytypes();


	public abstract void deleteIndustrytype(Industrytype industry);


	public abstract Industrytype findIndustrytype(Long id);


	public abstract List<Industrytype> findAllIndustrytypes();


	public abstract List<Industrytype> findIndustrytypeEntries(int firstResult, int maxResults);


	public abstract void saveIndustrytype(Industrytype industry);


	public abstract Industrytype updateIndustrytype(Industrytype industry);

    //public abstract List<Floor> findFloorbyFloorNumber(String searchKeyword);
    //public abstract String findFloorbyFloorNumber(String searchString);
	
	public abstract List<Industrytype> findIndustrytypebyIndustrytypeNumber(String searchString);
	
	public abstract long checkIfIndustryTypeExist(String industryType);
	
	public boolean checkRegex(String input, String user_pattern);

}
