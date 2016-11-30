package ph.com.smesoft.ebms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ph.com.smesoft.ebms.domain.Business;

public interface BusinessRepository extends JpaSpecificationExecutor<Business>, JpaRepository<Business, Long>{

}
