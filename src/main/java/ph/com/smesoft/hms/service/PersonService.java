package ph.com.smesoft.hms.service;
import java.util.List;

import ph.com.smesoft.hms.domain.Person;

public interface PersonService {

	public abstract long countAllPeople();


	public abstract void deletePerson(Person person);


	public abstract Person findPerson(Long id);


	public abstract List<Person> findAllPeople();


	public abstract List<Person> findPersonEntries(int firstResult, int maxResults);


	public abstract void savePerson(Person person);


	public abstract Person updatePerson(Person person);

}
