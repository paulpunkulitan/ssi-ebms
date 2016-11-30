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
   name = "findFloorByFloorNum",
   query = "SELECT f FROM Floor f WHERE LOWER(f.floorNumber) LIKE LOWER(:searchString) "
                                + "OR LOWER(f.description) LIKE LOWER(:searchString)"
                                + "OR LOWER(f.floorComments) LIKE LOWER(:searchString)"

   )
})

@Configurable
@Entity
public class Floor {

    /**
     */
                
    @NotNull
    private Long floorNumber;

    /**
     */
    @NotEmpty
    @Size(max = 1000)
    private String description;
    
    @NotEmpty
    @Size(max = 1000)
    private String floorComments;
    
    

                @PersistenceContext
    transient EntityManager entityManager;

                public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("floorNumber", "floorComments", "description");

                public static final EntityManager entityManager() {
        EntityManager em = new Floor().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

                public static long countFloors() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Floor o", Long.class).getSingleResult();
    }

                public static List<Floor> findAllFloors() {
        return entityManager().createQuery("SELECT o FROM Floor o", Floor.class).getResultList();
    }

                public static List<Floor> findAllFloors(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Floor o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Floor.class).getResultList();
    }

                public static Floor findFloor(Long id) {
        if (id == null) return null;
        return entityManager().find(Floor.class, id);
    }

                public static List<Floor> findFloorEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Floor o", Floor.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

                public static List<Floor> findFloorEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Floor o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Floor.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Floor attached = Floor.findFloor(this.id);
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
    public Floor merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Floor merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

                public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

                public Long getFloorNumber() {
        return this.floorNumber;
    }

                public void setFloorNumber(Long floorNumber) {
        this.floorNumber = floorNumber;
    }
                

                public String getFloorComments() {
        return this.floorComments;
    }

                public void setFloorComments(String floorComments) {
        this.floorComments = floorComments;
    }
                
                public String getDescription() {
        return this.description;
    }

                public void setDescription(String description) {
        this.description = description;
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

                public static Floor fromJsonToFloor(String json) {
        return new JSONDeserializer<Floor>()
        .use(null, Floor.class).deserialize(json);
    }

                public static String toJsonArray(Collection<Floor> collection) {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(collection);
    }

                public static String toJsonArray(Collection<Floor> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(collection);
    }

                public static Collection<Floor> fromJsonArrayToFloors(String json) {
        return new JSONDeserializer<List<Floor>>()
        .use("values", Floor.class).deserialize(json);
    }
}
