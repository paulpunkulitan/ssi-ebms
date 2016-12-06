package ph.com.smesoft.ebms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ph.com.smesoft.ebms.domain.SubCategory;

public interface SubcategoryRepository extends JpaSpecificationExecutor<SubCategory>, JpaRepository<SubCategory, Long> {

}
