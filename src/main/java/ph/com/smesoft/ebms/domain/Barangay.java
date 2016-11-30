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
		@NamedQuery(name = "findAllBarangaysBySearch", 
				query = "SELECT b FROM Barangay b WHERE LOWER(b.barangayName) LIKE LOWER(:searchString) "
				+ "OR LOWER(b.city.cityName) LIKE LOWER(:searchString)"
		),
		@NamedQuery(name = "countBarangaybyCityName", 
		query = "SELECT COUNT(b) FROM Barangay b WHERE b.city.cityName = :cityName"
		),
		@NamedQuery(
			    name = "firstBarangayInsertedRecord",
			   query = "SELECT b FROM Barangay b ORDER BY b.id ASC"
			),
		@NamedQuery(
			    name = "checkIfBarangayExist",
			   query = "SELECT COUNT(b.barangayName) FROM Barangay b WHERE b.barangayName = :barangayName"
			),
	    @NamedQuery(
	            name = "findAllBarangaysByCityId",
	            query = "SELECT b FROM Barangay b WHERE b.city.id = :cityId"
	            )
})

@Entity
@Configurable
public class Barangay {

	/**
	 */
	@NotEmpty
	@Column(unique=true, nullable=false) 
	@Size(max = 1000)
	private String barangayName;

	/**
	 */
	@ManyToOne
	private City city;
	
/*	@ManyToOne
	private Country country; */


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
	
	public String getBarangayName() {
		return barangayName;
	}

/*	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	} */

	public void setBarangayName(String barangayName) {
		this.barangayName = barangayName;
	}

	
	public City getCity() {
		return this.city;
	}

	public void setCity(City city) {
		this.city = city;
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

	public static Barangay fromJsonToBarangay(String json) {
		return new JSONDeserializer<Barangay>().use(null, Barangay.class).deserialize(json);
	}

	public static String toJsonArray(Collection<Barangay> collection) {
		return new JSONSerializer().exclude("*.class").deepSerialize(collection);
	}

	public static String toJsonArray(Collection<Barangay> collection, String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class").deepSerialize(collection);
	}

	public static Collection<Barangay> fromJsonArrayToBarangays(String json) {
		return new JSONDeserializer<List<Barangay>>().use("values", Barangay.class).deserialize(json);
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("barangayName", "city");

	public static final EntityManager entityManager() {
		EntityManager em = new Barangay().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countBarangays() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Barangay o", Long.class).getSingleResult();
	}

	public static List<Barangay> findAllBarangays() {
		return entityManager().createQuery("SELECT o FROM Barangay o", Barangay.class).getResultList();
	}

	public static List<Barangay> findAllBarangays(String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM Barangay o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, Barangay.class).getResultList();
	}

	public static Barangay findBarangay(Long id) {
		if (id == null)
			return null;
		return entityManager().find(Barangay.class, id);
	}

	public static List<Barangay> findBarangayEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Barangay o", Barangay.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static List<Barangay> findBarangayEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM Barangay o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, Barangay.class).setFirstResult(firstResult).setMaxResults(maxResults)
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
			Barangay attached = Barangay.findBarangay(this.id);
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
	public Barangay merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Barangay merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}
}
