package ph.com.smesoft.hms.domain;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
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

@Entity
@Configurable
public class Room {

    /**
     */
    @Size(min = 3, max = 30)
    private String roomNumber;

    /**
     */
    @NotNull
    @Size(max = 1000)
    private String description;

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

	public String getRoomNumber() {
        return this.roomNumber;
    }

	public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
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

	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(this);
    }

	public static Room fromJsonToRoom(String json) {
        return new JSONDeserializer<Room>()
        .use(null, Room.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Room> collection) {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(collection);
    }

	public static String toJsonArray(Collection<Room> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(collection);
    }

	public static Collection<Room> fromJsonArrayToRooms(String json) {
        return new JSONDeserializer<List<Room>>()
        .use("values", Room.class).deserialize(json);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("doorNumber", "description", "room");

	public static final EntityManager entityManager() {
        EntityManager em = new Room().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countRooms() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Room o", Long.class).getSingleResult();
    }

	public static List<Room> findAllRooms() {
        return entityManager().createQuery("SELECT o FROM Room o", Room.class).getResultList();
    }

	public static List<Room> findAllRooms(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Room o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Room.class).getResultList();
    }

	public static Room findRoom(Long id) {
        if (id == null) return null;
        return entityManager().find(Room.class, id);
    }

	public static List<Room> findRoomEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Room o", Room.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<Room> findRoomEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Room o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Room.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Room attached = Room.findRoom(this.id);
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
    public Room merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Room merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
