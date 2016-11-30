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
   name = "findIndustrytypeByIndustrytypeNum",
   query = "SELECT b FROM Industrytype b WHERE LOWER(b.industryTypeName) LIKE LOWER(:searchString) "
   ),
@NamedQuery(
		   name = "countIndustryType",
		   query = "SELECT f.industryTypeName FROM Industrytype f WHERE LOWER(f.industryTypeName) = LOWER(:search) "
)

})

@Configurable
@Entity
public class Industrytype {

    /**
     */
                
    @Size(min=1, max = 30)
    private String industryTypeName;
    
    

    @PersistenceContext
    transient EntityManager entityManager;

    public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("industryTypeName");

    public static final EntityManager entityManager() {
        EntityManager em = new Industrytype().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countIndustrytype() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Industrytype o", Long.class).getSingleResult();
    }

    public static List<Industrytype> findAllIndustrytypes() {
        return entityManager().createQuery("SELECT o FROM Industrytype o", Industrytype.class).getResultList();
    }

    public static List<Industrytype> findAllIndustrytypes(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Industrytype o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Industrytype.class).getResultList();
    }

    public static Industrytype findIndustrytype(Long id) {
        if (id == null) return null;
        return entityManager().find(Industrytype.class, id);
    }

    public static List<Industrytype> findIndustrytypeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Industrytype o", Industrytype.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List<Industrytype> findIndustrytypeEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Industrytype o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Industrytype.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Industrytype attached = Industrytype.findIndustrytype(this.id);
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
    public Industrytype merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Industrytype merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getIndustryTypeName() {
		return industryTypeName;
	}

	public void setIndustryTypeName(String industryTypeName) {
		this.industryTypeName = industryTypeName;
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

    public static Industrytype fromJsonToIndustrytype(String json) {
        return new JSONDeserializer<Industrytype>()
        .use(null, Industrytype.class).deserialize(json);
    }

    public static String toJsonArray(Collection<Industrytype> collection) {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(collection);
    }

    public static String toJsonArray(Collection<Industrytype> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(collection);
    }

    public static Collection<Industrytype> fromJsonArrayToIndustrytypes(String json) {
        return new JSONDeserializer<List<Industrytype>>()
        .use("values", Industrytype.class).deserialize(json);
    }
}
