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
	@NamedQuery(name = "findMeasurementByMeasurementName", 
			query = "SELECT m FROM Measurement m WHERE LOWER(m.measurementName) LIKE LOWER(:searchString) "
	),

@NamedQuery(
		   name = "countMeasurement",
		   query = "SELECT m.measurementName FROM Measurement m WHERE LOWER(m.measurementName) = LOWER(:search) "
)
})

@Entity
@Configurable
public class Measurement {

	/**
	 */
	@NotEmpty
	@Size(max = 1000)
	private String measurementName;
	@ManyToOne
	private Business business;
	@PersistenceContext
	transient EntityManager entityManager;
	
	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("measurementName");
	
	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public static final EntityManager entityManager() {
	EntityManager em = new Measurement().entityManager;
	if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
	return em;
	}
	
	public static long countMeasurement() {
	return entityManager().createQuery("SELECT COUNT(o) FROM Measurement o", Long.class).getSingleResult();
	}
	
	public static List<Measurement> findAllMeasurements() {
	return entityManager().createQuery("SELECT o FROM Measurement o", Measurement.class).getResultList();
	}
	
	public static List<Measurement> findAllMeasurements(String sortFieldName, String sortOrder) {
	String jpaQuery = "SELECT o FROM Measurement o";
	if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
	jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
	if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
	    jpaQuery = jpaQuery + " " + sortOrder;
	}
	}
	return entityManager().createQuery(jpaQuery, Measurement.class).getResultList();
	}
	
	public static Measurement findMeasurement(Long id) {
	if (id == null) return null;
	return entityManager().find(Measurement.class, id);
	}
	
	public static List<Measurement> findMeasurementEntries(int firstResult, int maxResults) {
	return entityManager().createQuery("SELECT o FROM Measurement o", Measurement.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}
	
	public static List<Measurement> findMeasurementEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
	String jpaQuery = "SELECT o FROM Measurement o";
	if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
	jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
	if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
	    jpaQuery = jpaQuery + " " + sortOrder;
	}
	}
	return entityManager().createQuery(jpaQuery, Measurement.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
	Measurement attached = Measurement.findMeasurement(this.id);
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
	public Measurement merge() {
	if (this.entityManager == null) this.entityManager = entityManager();
	Measurement merged = this.entityManager.merge(this);
	this.entityManager.flush();
	return merged;
	}
	
	public String toString() {
	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	public String getMeasurementName() {
	return measurementName;
	}
	
	public void setMeasurementName(String customerTypeName) {
	this.measurementName = customerTypeName;
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
	
	public static Measurement fromJsonToMeasurement(String json) {
	return new JSONDeserializer<Measurement>()
	.use(null, Measurement.class).deserialize(json);
	}
	
	public static String toJsonArray(Collection<Measurement> collection) {
	return new JSONSerializer()
	.exclude("*.class").deepSerialize(collection);
	}
	
	public static String toJsonArray(Collection<Measurement> collection, String[] fields) {
	return new JSONSerializer()
	.include(fields).exclude("*.class").deepSerialize(collection);
	}
	
	public static Collection<Measurement> fromJsonArrayToMeasurements(String json) {
	return new JSONDeserializer<List<Measurement>>()
	.use("values", Measurement.class).deserialize(json);
	}
}
