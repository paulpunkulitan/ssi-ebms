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
   name = "findCountryBySearch",
   query = "SELECT c FROM Country c WHERE LOWER(c.countryName) LIKE LOWER(:searchString)"
                                
   )
})

@Configurable
@Entity
public class Country {

    /**
     */
    @NotEmpty
    @NotNull
    @Size(max = 1000)
    private String countryName;
    
    @PersistenceContext
    transient EntityManager entityManager;

    public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("countryName");

    public static final EntityManager entityManager() {
        EntityManager em = new Country().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countCountries() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Country o", Long.class).getSingleResult();
    }

    public static List<Country> findAllCountries() {
        return entityManager().createQuery("SELECT o FROM Country o", Country.class).getResultList();
    }

    public static List<Country> findAllCountries(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Country o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Country.class).getResultList();
    }

    public static Country findCountry(Long id) {
        if (id == null) return null;
        return entityManager().find(Country.class, id);
    }

    public static List<Country> findCountryEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Country o", Country.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List<Country> findCountryEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Country o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Country.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Country attached = Country.findCountry(this.id);
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
    public Country merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Country merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    
    public String getCountryName() {
        return this.countryName;
    }

                public void setCountryName(String countryName) {
        this.countryName = countryName;
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

    public static Country fromJsonToCountry(String json) {
        return new JSONDeserializer<Country>()
        .use(null, Country.class).deserialize(json);
    }

    public static String toJsonArray(Collection<Country> collection) {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(collection);
    }

    public static String toJsonArray(Collection<Country> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(collection);
    }

    public static Collection<Country> fromJsonArrayToCountries(String json) {
        return new JSONDeserializer<List<Country>>()
        .use("values", Country.class).deserialize(json);
    }
}
