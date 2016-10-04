package ph.com.smesoft.hms.domain;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
public class Shift {

    /**
     */
    @NotNull
    @Future
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss a")
    private Date shiftDate;

    /**
     */
    @ManyToOne
    private Person person;

    /**
     */
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Floor> floors = new HashSet<Floor>();

    /**
     */
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Room> rooms = new HashSet<Room>();

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("shiftDate", "person", "floors", "rooms");

	public static final EntityManager entityManager() {
        EntityManager em = new Shift().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countShifts() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Shift o", Long.class).getSingleResult();
    }

	public static List<Shift> findAllShifts() {
        return entityManager().createQuery("SELECT o FROM Shift o", Shift.class).getResultList();
    }

	public static List<Shift> findAllShifts(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Shift o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Shift.class).getResultList();
    }

	public static Shift findShift(Long id) {
        if (id == null) return null;
        return entityManager().find(Shift.class, id);
    }

	public static List<Shift> findShiftEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Shift o", Shift.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<Shift> findShiftEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Shift o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Shift.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Shift attached = Shift.findShift(this.id);
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
    public Shift merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Shift merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
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

	public static Shift fromJsonToShift(String json) {
        return new JSONDeserializer<Shift>()
        .use(null, Shift.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Shift> collection) {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(collection);
    }

	public static String toJsonArray(Collection<Shift> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(collection);
    }

	public static Collection<Shift> fromJsonArrayToShifts(String json) {
        return new JSONDeserializer<List<Shift>>()
        .use("values", Shift.class).deserialize(json);
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

	public Date getShiftDate() {
        return this.shiftDate;
    }

	public void setShiftDate(Date shiftDate) {
        this.shiftDate = shiftDate;
    }

	public Person getPerson() {
        return this.person;
    }

	public void setPerson(Person person) {
        this.person = person;
    }

	public Set<Floor> getFloors() {
        return this.floors;
    }

	public void setFloors(Set<Floor> floors) {
        this.floors = floors;
    }

	public Set<Room> getRooms() {
        return this.rooms;
    }

	public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }
}
