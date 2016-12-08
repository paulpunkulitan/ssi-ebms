package ph.com.smesoft.ebms.domain;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import com.sun.istack.NotNull;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@NamedQueries({
@NamedQuery(
   name = "findCustomertypeByCustomertypeNum",
   query = "SELECT b FROM Customertype b WHERE LOWER(b.customerTypeName) LIKE LOWER(:searchString) "
   ),
@NamedQuery(
		   name = "countCustomerType",
		   query = "SELECT b.customerTypeName FROM Customertype b WHERE LOWER(b.customerTypeName) = LOWER(:search) "
)
})

@Configurable
@Entity
public class Customertype {

    /**
     */
	@Column(unique=true)
    @Size(min=1, max=30)
    private String customerTypeName;
    
    

    @PersistenceContext
    transient EntityManager entityManager;

    public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("customerTypeName");

    public static final EntityManager entityManager() {
        EntityManager em = new Customertype().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countCustomertype() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Customertype o", Long.class).getSingleResult();
    }

    public static List<Customertype> findAllCustomertypes() {
        return entityManager().createQuery("SELECT o FROM Customertype o", Customertype.class).getResultList();
    }

    public static List<Customertype> findAllCustomertypesName() {
        return entityManager().createQuery("SELECT o.customerTypeName FROM Customertype o", Customertype.class).getResultList();
    }
    
    public static List<Customertype> findAllCustomertypes(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Customertype o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Customertype.class).getResultList();
    }
    
    public static List<Customertype> findAllCustomertypesName(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Customertype o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Customertype.class).getResultList();
    }

    public static Customertype findCustomertype(Long id) {
        if (id == null) return null;
        return entityManager().find(Customertype.class, id);
    }

    public static List<Customertype> findCustomertypeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Customertype o", Customertype.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List<Customertype> findCustomertypeEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Customertype o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Customertype.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<Customertype> findCustomertypeNameEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o.customerTypeName FROM Customertype o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Customertype.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Customertype attached = Customertype.findCustomertype(this.id);
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
    public Customertype merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Customertype merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getCustomerTypeName() {
		return customerTypeName;
	}

	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
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

    public static Customertype fromJsonToCustomertype(String json) {
        return new JSONDeserializer<Customertype>()
        .use(null, Customertype.class).deserialize(json);
    }

    public static String toJsonArray(Collection<Customertype> collection) {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(collection);
    }

    public static String toJsonArray(Collection<Customertype> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(collection);
    }

    public static Collection<Customertype> fromJsonArrayToCustomertypes(String json) {
        return new JSONDeserializer<List<Customertype>>()
        .use("values", Customertype.class).deserialize(json);
    }
}
