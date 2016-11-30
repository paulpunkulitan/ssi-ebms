package ph.com.smesoft.ebms.service;
import java.util.List;

import ph.com.smesoft.ebms.domain.Country;

public interface CountryService {

	public abstract long countAllCountries();


	public abstract void deleteCountry(Country country);


	public abstract Country findCountry(Long id);


	public abstract List<Country> findAllCountries();


	public abstract List<Country> findCountryEntries(int firstResult, int maxResults);


	public abstract void saveCountry(Country country);


	public abstract Country updateCountry(Country country);

    //public abstract List<Floor> findFloorbyFloorNumber(String searchKeyword);
  //  public abstract String findFloorbyFloorNumber(String searchString);
	public abstract List<Country> findCountryBySearch(String searchString);

}
