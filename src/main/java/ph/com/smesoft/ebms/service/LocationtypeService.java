package ph.com.smesoft.ebms.service;

import java.util.List;

import ph.com.smesoft.ebms.domain.Locationtype;

public interface LocationtypeService {
	public abstract long countAllLocationTypes();


	public abstract void deleteLocationType(Locationtype floor);


	public abstract Locationtype findLocationType(Long id);


	public abstract List<Locationtype> findAllLocationTypes();


	public abstract List<Locationtype> findLocationTypeEntries(int firstResult, int maxResults);


	public abstract void saveLocationType(Locationtype floor);


	public abstract Locationtype updateLocationType(Locationtype floor);

    //public abstract List<LocationType> findLocationTypebyLocationTypeNumber(String searchKeyword);
  //  public abstract String findLocationTypebyLocationTypeNumber(String searchString);
	public abstract List<Locationtype> findLocationtypeByLocationtypeNum(String searchString);
	
	public abstract long checkIfLocationTypeExist(String locationType);
	
	public boolean checkRegex(String input, String user_pattern);

}
