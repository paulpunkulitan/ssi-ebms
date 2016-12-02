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
		@NamedQuery(name = "findAllAreasBySearch", 
				query = "SELECT a FROM Area a WHERE LOWER(a.areaName) LIKE LOWER(:searchString) "
				+ "OR LOWER(a.street.streetName) LIKE LOWER(:searchString)"
				),
		@NamedQuery(name = "countAreasByStreetName", 
		query = "SELECT COUNT(a) FROM Area a WHERE a.street.streetName = :streetName"
		),
		@NamedQuery(
			    name = "firstAreaInsertedRecord",
			   query = "SELECT a FROM Area a ORDER BY a.id ASC"
			),
		@NamedQuery(
			    name = "checkIfAreaExist",
			   query = "SELECT COUNT(a.areaName) FROM Area a WHERE a.areaName = :areaName"
			),
	    @NamedQuery(
	            name = "findAllAreasByStreetId",
	            query = "SELECT a FROM Area a WHERE a.street.id = :streetId"
	            )
})

@Entity
@Configurable
public class Area {

	/**
	 */
	@NotEmpty
	@Size(max = 1000)
	@Column(nullable=false) 
	private String areaName;

	/**
	 */
	@ManyToOne
	private Street street;

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
	
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Street getStreet() {
		return this.street;
	}

	public void setStreet(Street street) {
		this.street = street;
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

	public static Area fromJsonToArea(String json) {
		return new JSONDeserializer<Area>().use(null, Area.class).deserialize(json);
	}

	public static String toJsonArray(Collection<Area> collection) {
		return new JSONSerializer().exclude("*.class").deepSerialize(collection);
	}

	public static String toJsonArray(Collection<Area> collection, String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class").deepSerialize(collection);
	}

	public static Collection<Area> fromJsonArrayToAreas(String json) {
		return new JSONDeserializer<List<Area>>().use("values", Area.class).deserialize(json);
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("areaName", "street");

	public static final EntityManager entityManager() {
		EntityManager em = new Area().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countAreas() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Area o", Long.class).getSingleResult();
	}

	public static List<Area> findAllAreas() {
		return entityManager().createQuery("SELECT o FROM Area o", Area.class).getResultList();
	}

	public static List<Area> findAllAreas(String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM Area o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, Area.class).getResultList();
	}

	public static Area findArea(Long id) {
		if (id == null)
			return null;
		return entityManager().find(Area.class, id);
	}

	public static List<Area> findAreaEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Area o", Area.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static List<Area> findAreaEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM Area o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, Area.class).setFirstResult(firstResult).setMaxResults(maxResults)
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
			Area attached = Area.findArea(this.id);
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
	public Area merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Area merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}
}
