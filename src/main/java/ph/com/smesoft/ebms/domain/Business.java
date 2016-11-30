package ph.com.smesoft.ebms.domain;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@NamedQueries({
@NamedQuery(
   name = "findBusinessByBusinessNum",
   query = "SELECT b FROM Business b WHERE LOWER(b.businessName) LIKE LOWER(:searchString) "
                                + "OR LOWER(b.businessAddress) LIKE LOWER(:searchString)"
                                + "OR LOWER(b.businessEmailAddress) LIKE LOWER(:searchString)"
                                + "OR LOWER(b.businessTelephoneNumber) LIKE LOWER(:searchString)"

   ),
@NamedQuery(
		   name = "countBusiness",
		   query = "SELECT b FROM Business b WHERE LOWER(b.businessName) = LOWER(:search) "
)
})

@Configurable
@Entity
public class Business {
    /**
     */

	@NotEmpty
    private String businessName;
    
    @NotEmpty
    private String businessAddress;
 
    @NotEmpty
    private String businessEmailAddress;
        
    @NotEmpty
    private String businessTelephoneNumber;
        

    @PersistenceContext
    transient EntityManager entityManager;

    public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("businessName");

    public static final EntityManager entityManager() {
        EntityManager em = new Business().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countBusiness() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Business o", Long.class).getSingleResult();
    }

    public static List<Business> findAllBusinesses() {
        return entityManager().createQuery("SELECT o.barangayName FROM Business o", Business.class).getResultList();
    }

    public static List<Business> findAllBusinesses(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Business o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Business.class).getResultList();
    }

    public static Business findBusiness(Long id) {
        if (id == null) return null;
        return entityManager().find(Business.class, id);
    }

    public static List<Business> findBusinessEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Business o", Business.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List<Business> findBusinessEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Business o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Business.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    @Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

    @Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Business attached = Business.findBusiness(this.id);
            this.entityManager.remove(attached);
        }
    }

    @Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

    @Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

    @Transactional
    public Business merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Business merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}

	public String getBusinessEmailAddress() {
		return businessEmailAddress;
	}

	public void setBusinessEmailAddress(String businessEmailAddress) {
		this.businessEmailAddress = businessEmailAddress;
	}

	public String getBusinessTelephoneNumber() {
		return businessTelephoneNumber;
	}

	public void setBusinessTelephoneNumber(String businessTelephoneNumber) {
		this.businessTelephoneNumber = businessTelephoneNumber;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version")
    private Integer version;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(this);
    }

    public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(this);
    }

    public static Business fromJsonToBusiness(String json) {
        return new JSONDeserializer<Business>()
        .use(null, Business.class).deserialize(json);
    }

    public static String toJsonArray(Collection<Business> collection) {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(collection);
    }

    public static String toJsonArray(Collection<Business> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(collection);
    }

    public static Collection<Business> fromJsonArrayToBusinesss(String json) {
        return new JSONDeserializer<List<Business>>()
        .use("values", Business.class).deserialize(json);
    }
}
