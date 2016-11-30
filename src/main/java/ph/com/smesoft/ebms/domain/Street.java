package ph.com.smesoft.ebms.domain;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
		@NamedQuery(name = "findAllStreetsBySearch", 
				query = "SELECT s FROM Street s WHERE LOWER(s.streetName) LIKE LOWER(:searchString) "
				+ "OR LOWER(s.barangay.barangayName) LIKE LOWER(:searchString)"
		),
		@NamedQuery(name = "countStreetbyStreetName", 
		query = "SELECT COUNT(s) FROM Street s WHERE s.barangay.barangayName = :barangayName"
		),
		@NamedQuery(
			    name = "firstStreetInsertedRecord",
			   query = "SELECT s FROM Street s ORDER BY s.id ASC"
			),
		@NamedQuery(
			    name = "checkIfStreetExist",
			   query = "SELECT COUNT(s.streetName) FROM Street s WHERE s.streetName = :streetName"
			),
	    @NamedQuery(
	            name = "findAllStreetsByBarangayId",
	            query = "SELECT s FROM Street s WHERE s.barangay.id = :barangayId"
	            )
})

@Entity
@Configurable
public class Street {

	/**
	 */
	@NotEmpty
	@Column(unique=true, nullable=false) 
	@Size(max = 1000)
	private String streetName;

	/**
	 */
	@ManyToOne
	private Barangay barangay;

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

	/*public Long getRoomNumber() {
		return this.roomNumber;
	}

	public void setRoomNumber(Long roomNumber) {
		this.roomNumber = roomNumber;
	}*/
	
	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	
	public Barangay getBarangay() {
		return this.barangay;
	}

	public void setBarangay(Barangay barangay) {
		this.barangay = barangay;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").deepSerialize(this);
	}

	public String toJson(String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class").deepSerialize(this);
	}

	public static Street fromJsonToStreet(String json) {
		return new JSONDeserializer<Street>().use(null, Street.class).deserialize(json);
	}

	public static String toJsonArray(Collection<Street> collection) {
		return new JSONSerializer().exclude("*.class").deepSerialize(collection);
	}

	public static String toJsonArray(Collection<Street> collection, String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class").deepSerialize(collection);
	}

	public static Collection<Street> fromJsonArrayToStreets(String json) {
		return new JSONDeserializer<List<Street>>().use("values", Street.class).deserialize(json);
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("streetName", "barangay");

	public static final EntityManager entityManager() {
		EntityManager em = new Barangay().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countStreets() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Street o", Long.class).getSingleResult();
	}

	public static List<Street> findAllStreets() {
		return entityManager().createQuery("SELECT o FROM Street o", Street.class).getResultList();
	}

	public static List<Street> findAllStreets(String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM Street o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, Street.class).getResultList();
	}

	public static Street findStreet(Long id) {
		if (id == null)
			return null;
		return entityManager().find(Street.class, id);
	}

	public static List<Street> findStreetEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Street o", Street.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static List<Street> findStreetEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM Street o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, Street.class).setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	@Transactional
	public void persist() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.persist(this);
	}

	@Transactional
	public void remove() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		if (this.entityManager.contains(this)) {
			this.entityManager.remove(this);
		} else {
			Street attached = Street.findStreet(this.id);
			this.entityManager.remove(attached);
		}
	}

	@Transactional
	public void flush() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.flush();
	}

	@Transactional
	public void clear() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.clear();
	}

	@Transactional
	public Street merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Street merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}
}
