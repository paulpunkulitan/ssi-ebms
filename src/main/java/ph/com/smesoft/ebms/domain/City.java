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
		@NamedQuery(name = "findAllCitiesBySearch", 
				query = "SELECT c FROM City c WHERE LOWER(c.cityName) LIKE LOWER(:searchString) "
				+ "OR LOWER(c.state.id) LIKE LOWER(:searchString)"
				),
		@NamedQuery(name = "countCitiesbyStateId", 
		query = "SELECT COUNT(c) FROM City c WHERE c.state.id = :stateId"
		),
		@NamedQuery(
			    name = "firstCitInsertedRecord",
			   query = "SELECT c FROM City c ORDER BY c.id ASC"
			),
		@NamedQuery(
			    name = "checkIfCityExist",
			   query = "SELECT COUNT(c.cityName) FROM City c WHERE c.cityName = :cityName"
			),
	    @NamedQuery(
	            name = "findAllCitiesByStateId",
	            query = "SELECT c FROM City c WHERE c.state.id = :stateId"
	            ),
		@NamedQuery(
	            name = "cityByStateId",
	            query = "SELECT c.id, c.cityName FROM City c, State s "
	            		+ "WHERE c.state = s and s.id = :stateId"
	            )
})

@Entity
@Configurable
public class City {

	/**
	 */
	@NotEmpty
	@Column(unique=true, nullable=false) 
	@Size(max = 1000)
	private String cityName;

	@ManyToOne
	private State state;

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
	
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public State getState() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
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

	public static City fromJsonToCity(String json) {
		return new JSONDeserializer<City>().use(null, City.class).deserialize(json);
	}

	public static String toJsonArray(Collection<City> collection) {
		return new JSONSerializer().exclude("*.class").deepSerialize(collection);
	}

	public static String toJsonArray(Collection<City> collection, String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class").deepSerialize(collection);
	}

	public static Collection<City> fromJsonArrayToCities(String json) {
		return new JSONDeserializer<List<City>>().use("values", City.class).deserialize(json);
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("cityName", "state");

	public static final EntityManager entityManager() {
		EntityManager em = new City().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countCities() {
		return entityManager().createQuery("SELECT COUNT(o) FROM City o", Long.class).getSingleResult();
	}

	public static List<City> findAllCities() {
		return entityManager().createQuery("SELECT o FROM City o", City.class).getResultList();
	}

	public static List<City> findAllCities(String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM City o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, City.class).getResultList();
	}

	public static City findCity(Long id) {
		if (id == null)
			return null;
		return entityManager().find(City.class, id);
	}

	public static List<City> findCityEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM City", City.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static List<City> findCityEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM City o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, City.class).setFirstResult(firstResult).setMaxResults(maxResults)
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
			City attached = City.findCity(this.id);
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
	public City merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		City merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}
}
