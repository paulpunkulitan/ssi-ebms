package ph.com.smesoft.ebms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ph.com.smesoft.ebms.domain.ItemCategory;

public interface ItemcategoryRepository extends JpaSpecificationExecutor<ItemCategory>, JpaRepository<ItemCategory, Long> {

}
