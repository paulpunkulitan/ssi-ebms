package ph.com.smesoft.ebms.service;

import java.util.List;

import ph.com.smesoft.ebms.domain.City;

public interface CityService {

	public abstract long countAllCities();


	public abstract void deleteCity(City city);


	public abstract City findCity(Long id);


	public abstract List<City> findAllCities();


	public abstract List<City> findCityEntries(int firstResult, int maxResults);


	public abstract void saveCity(City city);


	public abstract City updateCity(City city);

    //public abstract List<Floor> findFloorbyFloorNumber(String searchKeyword);
    //public abstract String findFloorbyFloorNumber(String searchString);
	
	public abstract List<City> findAllCitiesBySearch(String searchString);

	public abstract List<City> findAllCitiesByStateId(Long searchString);
	

}
