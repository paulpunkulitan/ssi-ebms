package ph.com.smesoft.ebms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ph.com.smesoft.ebms.domain.Industrytype;

public interface IndustrytypeRepository extends JpaSpecificationExecutor<Industrytype>, JpaRepository<Industrytype, Long> {

}
