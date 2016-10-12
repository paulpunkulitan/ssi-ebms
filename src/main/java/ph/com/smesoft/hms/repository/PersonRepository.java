package ph.com.smesoft.hms.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ph.com.smesoft.hms.domain.Person;

@Repository
public interface PersonRepository extends JpaSpecificationExecutor<Person>, JpaRepository<Person, Long> {
	
	/*@Query("Select PERSONTYPE from PERSON where PVID = :pvId")
	  Person findByPvId(@Param("pvId") String pvId);*/
}
