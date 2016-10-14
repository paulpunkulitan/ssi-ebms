package ph.com.smesoft.hms.domain;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Configurable
public class Door {

	 /**
     */
    @Size(min = 3, max = 30)
    private String palmusId;
	
    /**
     */
    @Size(min = 3, max = 30)
    private String doorNumber;

    /**
     */
    @NotNull
    @Size(max = 1000)
    private String description;

    /**
     */
    @ManyToOne
    public Room room;

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

	public String getPalmusId() {
		return palmusId;
	}

	public void setPalmusId(String palmusId) {
		this.palmusId = palmusId;
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

	public static Door fromJsonToDoor(String json) {
        return new JSONDeserializer<Door>()
        .use(null, Door.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Door> collection) {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(collection);
    }

	public static String toJsonArray(Collection<Door> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(collection);
    }

	public static Collection<Door> fromJsonArrayToDoors(String json) {
        return new JSONDeserializer<List<Door>>()
        .use("values", Door.class).deserialize(json);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("doorNumber", "description", "room");

	public static final EntityManager entityManager() {
        EntityManager em = new Door().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countDoors() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Door o", Long.class).getSingleResult();
    }

	public static List<Door> findAllDoors() {
        return entityManager().createQuery("SELECT o FROM Door o", Door.class).getResultList();
    }

	public static List<Door> findAllDoors(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Door o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Door.class).getResultList();
    }

	public static Door findDoor(Long id) {
        if (id == null) return null;
        return entityManager().find(Door.class, id);
    }

	public static List<Door> findDoorEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Door o", Door.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<Door> findDoorEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Door o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Door.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Door attached = Door.findDoor(this.id);
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
    public Door merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Door merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String getDoorNumber() {
        return this.doorNumber;
    }

	public void setDoorNumber(String doorNumber) {
        this.doorNumber = doorNumber;
    }

	public String getDescription() {
        return this.description;
    }

	public void setDescription(String description) {
        this.description = description;
    }

	public Room getRoom() {
        return this.room;
    }

	public void setRoom(Room room) {
        this.room = room;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
