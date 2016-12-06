package ph.com.smesoft.ebms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ph.com.smesoft.ebms.domain.Measurement;

@Repository
public interface MeasurementRepository extends JpaSpecificationExecutor<Measurement>, JpaRepository<Measurement, Long>{

}
