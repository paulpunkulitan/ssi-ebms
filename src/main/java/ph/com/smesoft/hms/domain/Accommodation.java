package ph.com.smesoft.hms.domain;
import org.springframework.transaction.annotation.Transactional;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;

@Entity
@Configurable
public class Accommodation {

    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date startDate;

    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date endDate;

    // *********
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "h:mm a")
    private Date startTime;
    
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "h:mm a")
    private Date endTime;
    
    /**
     */
    @ManyToOne
    private Person person;

    /**
     */
    @ManyToOne
    private Room room;

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

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(this);
    }

	public static Accommodation fromJsonToAccommodation(String json) {
        return new JSONDeserializer<Accommodation>()
        .use(null, Accommodation.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Accommodation> collection) {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(collection);
    }

	public static String toJsonArray(Collection<Accommodation> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(collection);
    }

	public static Collection<Accommodation> fromJsonArrayToAccommodations(String json) {
        return new JSONDeserializer<List<Accommodation>>()
        .use("values", Accommodation.class).deserialize(json);
    }

	public Date getStartDate() {
        return this.startDate;
    }

	public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

	public Date getEndDate() {
        return this.endDate;
    }

	public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Person getPerson() {
        return this.person;
    }

	public void setPerson(Person person) {
        this.person = person;
    }

	public Room getRoom() {
        return this.room;
    }

	public void setRoom(Room room) {
        this.room = room;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("startDate", "endDate", "person", "room");

	public static final EntityManager entityManager() {
        EntityManager em = new Accommodation().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countAccommodations() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Accommodation o", Long.class).getSingleResult();
    }

	public static List<Accommodation> findAllAccommodations() {
        return entityManager().createQuery("SELECT o FROM Accommodation o", Accommodation.class).getResultList();
    }

	public static List<Accommodation> findAllAccommodations(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Accommodation o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Accommodation.class).getResultList();
    }

	public static Accommodation findAccommodation(Long id) {
        if (id == null) return null;
        return entityManager().find(Accommodation.class, id);
    }

	public static List<Accommodation> findAccommodationEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Accommodation o", Accommodation.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<Accommodation> findAccommodationEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Accommodation o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Accommodation.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Accommodation attached = Accommodation.findAccommodation(this.id);
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
    public Accommodation merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Accommodation merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
