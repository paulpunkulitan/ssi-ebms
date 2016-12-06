package ph.com.smesoft.ebms.service;

import java.util.List;

import ph.com.smesoft.ebms.domain.Measurement;

public interface MeasurementService {
	public abstract long countAllMeasurements();


	public abstract void deleteMeasurement(Measurement floor);


	public abstract Measurement findMeasurement(Long id);


	public abstract List<Measurement> findAllMeasurements();


	public abstract List<Measurement> findMeasurementEntries(int firstResult, int maxResults);


	public abstract void saveMeasurement(Measurement floor);


	public abstract Measurement updateMeasurement(Measurement floor);

    //public abstract List<Measurement> findMeasurementbyMeasurementNumber(String searchKeyword);
  //  public abstract String findMeasurementbyMeasurementNumber(String searchString);
	public abstract List<Measurement> findMeasurementByMeasurementNum(String searchString);
	
	public abstract long checkIfMeasurementExist(String locationType);
	
	public boolean checkRegex(String input, String user_pattern);

}
