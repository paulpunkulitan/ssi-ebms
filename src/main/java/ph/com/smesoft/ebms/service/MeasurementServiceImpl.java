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

import ph.com.smesoft.ebms.domain.Measurement;
import ph.com.smesoft.ebms.repository.MeasurementRepository;
@Service
@Transactional
public class MeasurementServiceImpl implements MeasurementService{
	
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
	MeasurementRepository measurementRepository;
	
	public long countAllMeasurements() {
        return measurementRepository.count();
    }
	
	public void deleteMeasurement(Measurement Measurement) {
		measurementRepository.delete(Measurement);
    }
	
	public Measurement findMeasurement(Long id) {
        return measurementRepository.findOne(id);
    }

	public List<Measurement> findAllMeasurements() {
        return measurementRepository.findAll();
    }

	public List<Measurement> findMeasurementEntries(int firstResult, int maxResults) {
        return measurementRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveMeasurement(Measurement Measurement) {
		measurementRepository.save(Measurement);
    }

	public Measurement updateMeasurement(Measurement Measurement) {
        return measurementRepository.save(Measurement);
    }
	
	public List<Measurement> findMeasurementByMeasurementNum(String searchString){
	    TypedQuery<Measurement> searchResult = em.createNamedQuery("findMeasurementByMeasurementNum", Measurement.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<Measurement> result=searchResult.getResultList();
	    return result;
	 }

	public long checkIfMeasurementExist(String search) {
		
		TypedQuery<Measurement> searchResult = em.createNamedQuery("countMeasurement", Measurement.class);
	    searchResult.setParameter("search",search);
	    List<Measurement> result = searchResult.getResultList();
	    int count = result.size();
	    return count;
	}

	public boolean checkRegex(String input, String user_pattern) {
		Pattern pattern = Pattern.compile(user_pattern);
		Matcher matcher;
		
		  matcher = pattern.matcher(input);
		  return matcher.matches();
	}
}
