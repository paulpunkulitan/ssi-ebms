package ph.com.smesoft.hms.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ph.com.smesoft.hms.domain.Accommodation;

@Repository
public interface AccommodationRepository extends JpaSpecificationExecutor<Accommodation>, JpaRepository<Accommodation, Long> {
}
