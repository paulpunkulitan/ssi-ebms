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
		@NamedQuery(name = "findAllStatesBySearch", 
				query = "SELECT s FROM State s WHERE LOWER(s.stateName) LIKE LOWER(:searchString) "
				+ "OR LOWER(s.country.countryName) LIKE LOWER(:searchString)"

				),
		@NamedQuery(name = "countStatesbyCountryName", 
		query = "SELECT COUNT(s) FROM State s WHERE s.country.countryName = :countryName"
		),
		@NamedQuery(
			    name = "firstStateInsertedRecord",
			   query = "SELECT s FROM State s ORDER BY s.id ASC"
			),
		@NamedQuery(
			    name = "checkIfStateExist",
			   query = "SELECT COUNT(s.stateName) FROM State s WHERE s.stateName = :stateName"
			),
	    @NamedQuery(
	            name = "findAllStatesByCountryId",
	            query = "SELECT s FROM State s WHERE s.country.id = :countryId"
	            ),
		@NamedQuery(
	            name = "statesByCountryId",
	            query = "SELECT s.id, s.stateName FROM State s, Country c "
	            		+ "WHERE s.country = c and c.id = :countryId"
	            )
})
		
@Entity
@Configurable
public class State {

	/**
	 */
	@NotEmpty
	@Size(max = 1000)
	private String stateName;
	
	/**
	 */
	@ManyToOne
	private Country country;

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

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
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

	public static State fromJsonToState(String json) {
		return new JSONDeserializer<State>().use(null, State.class).deserialize(json);
	}

	public static String toJsonArray(Collection<State> collection) {
		return new JSONSerializer().exclude("*.class").deepSerialize(collection);
	}

	public static String toJsonArray(Collection<State> collection, String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class").deepSerialize(collection);
	}

	public static Collection<State> fromJsonArrayToStates(String json) {
		return new JSONDeserializer<List<State>>().use("values", State.class).deserialize(json);
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("stateName", "country");

	public static final EntityManager entityManager() {
		EntityManager em = new State().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countStates() {
		return entityManager().createQuery("SELECT COUNT(o) FROM State o", Long.class).getSingleResult();
	}

	public static List<State> findAllStates() {
		return entityManager().createQuery("SELECT o FROM State o", State.class).getResultList();
	}

	public static List<State> findAllStates(String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM State o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, State.class).getResultList();
	}

	public static State findState(Long id) {
		if (id == null)
			return null;
		return entityManager().find(State.class, id);
	}

	public static List<State> findStateEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM State o", State.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static List<State> findStateEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM State o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, State.class).setFirstResult(firstResult).setMaxResults(maxResults)
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
			State attached = State.findState(this.id);
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
	public State merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		State merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}
}
