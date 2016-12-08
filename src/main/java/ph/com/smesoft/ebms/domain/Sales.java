package ph.com.smesoft.ebms.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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

@Configurable
@Entity
@NamedQueries({
	
})

public class Sales {

	@ManyToOne
	private Customer customer;
	
	private String date;
	private String salesOrderNo;
	
	
	@PersistenceContext
    transient EntityManager entityManager;
	
	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("date", "customer");
	 
	public static final EntityManager entityManager() {
	        EntityManager em = new Sales().entityManager;
	        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
	        return em;
	}
	 
	 public static long countSales() {
	        return entityManager().createQuery("SELECT COUNT(o) FROM Sales o", Long.class).getSingleResult();
	}
	    
	public static List<Sales> findAllSales() {
	        return entityManager().createQuery("SELECT o FROM Sales o", Sales.class).getResultList();
	}
	
	
	public static List<Sales> findAllSales(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Sales o ";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Sales.class).getResultList();
    }
	
	 public static Sales findSales(Long id) {
	        if (id == null) return null;
	        return entityManager().find(Sales.class, id);
	    }

	 
	public static List<Sales> findSalesEntries(int firstResult, int maxResults) {
	        return entityManager().createQuery("SELECT o FROM Sales o", Sales.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
	
	public static List<Sales> findSalesEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Sales o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC". equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Sales.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
	
	
	
	public static Sales findSalesId(Long id) {
		if (id == null)
			return null;
		return entityManager().find(Sales.class, id);
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
	            Sales attached = Sales.findSales(this.id);
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
	    public Sales merge() {
	        if (this.entityManager == null) this.entityManager = entityManager();
	        Sales merged = this.entityManager.merge(this);
	        this.entityManager.flush();
	        return merged;
	    }
	    
	    public String toString() {
	        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	    }

	
		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name = "id")
	    private Long id;
	


		public Customer getCustomer() {
			return customer;
		}

		public void setCustomer(Customer customer) {
			this.customer = customer;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}
		
		public String getSalesOrderNo() {
			return salesOrderNo;
		}

		public void setSalesOrderNo(String salesOrderNo) {
			this.salesOrderNo = salesOrderNo;
		}


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

		public static Sales fromJsonToSales(String json) {
		        return new JSONDeserializer<Sales>()
		        .use(null, Sales.class).deserialize(json);
		}

		public static String toJsonArray(Collection<Sales> collection) {
		        return new JSONSerializer()
		        .exclude("*.class").deepSerialize(collection);
		}

		public static String toJsonArray(Collection<Sales> collection, String[] fields) {
		        return new JSONSerializer()
		        .include(fields).exclude("*.class").deepSerialize(collection);
		}

		public static Collection<Sales> fromJsonArrayToSales(String json) {
		        return new JSONDeserializer<List<Sales>>()
		        .use("values", Sales.class).deserialize(json);
		}


}
