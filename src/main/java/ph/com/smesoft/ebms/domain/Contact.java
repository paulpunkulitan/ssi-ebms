package ph.com.smesoft.ebms.domain;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import static javax.persistence.GenerationType.IDENTITY;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Embeddable
@NamedQueries({
@NamedQuery(
   name = "findContactByid",
   query = "SELECT c FROM Contact c WHERE LOWER(c.FirstName) LIKE LOWER(:searchString) "
           + "OR LOWER(c.MiddleName) LIKE LOWER(:searchString)"
           + "OR LOWER(c.LastName) LIKE LOWER(:searchString)"
           + "OR LOWER(c.Mobile) LIKE LOWER(:searchString)"
           + "OR LOWER(c.Phone) LIKE LOWER(:searchString)"
           + "OR LOWER(c.Fax) LIKE LOWER(:searchString)"
           + "OR LOWER(c.Email) LIKE LOWER(:searchString)"
)})

public class Contact {
	
	@NotEmpty
	@Size(max = 30)
	private String FirstName;
	@Size(max = 30)
	private String MiddleName;
	@NotEmpty
	@Size(max = 30)
	private String LastName;
	
	@NotEmpty
	@Size(max = 12)
	private String Mobile;
	@Size(max = 12)
	private String Phone;   
	@Size(max = 15)
	private String Fax;   
	@Size(max = 30)
	private String Email; 
	
	

    @PersistenceContext
    transient EntityManager entityManager;
	
	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("FirstName", "MiddleName", "LastName");
	 
	public static final EntityManager entityManager() {
	        EntityManager em = new Floor().entityManager;
	        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
	        return em;
	}
	 
	 public static long countContact() {
	        return entityManager().createQuery("SELECT COUNT(o) FROM Contact o", Long.class).getSingleResult();
	}
	    
	public static List<Contact> findAllContact() {
	        return entityManager().createQuery("SELECT o FROM Contact o", Contact.class).getResultList();
	}
	
	public static List<Contact> findAllContact(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Contact o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Contact.class).getResultList();
    }
	
	 public static Contact findContact(Long id) {
	        if (id == null) return null;
	        return entityManager().find(Contact.class, id);
	    }

	 
	public static List<Contact> findContactEntries(int firstResult, int maxResults) {
	        return entityManager().createQuery("SELECT o FROM Contact o", Contact.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
		}
	
	
	public static List<Contact> findContactEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Contact o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Contact.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
	        	Contact attached = Contact.findContact(this.id);
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
	    public Contact merge() {
	        if (this.entityManager == null) this.entityManager = entityManager();
	        Contact merged = this.entityManager.merge(this);
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


	    public Long getId() {
	        return this.id;
	    }
	   

	    public void setId(Long id) {
	        this.id = id;
	    }
	   


	    
	    public String getFirstName() {
			return FirstName;
		}

		public void setFirstName(String firstName) {
			FirstName = firstName;
		}

		public String getMiddleName() {
			return MiddleName;
		}

		public void setMiddleName(String middleName) {
			MiddleName = middleName;
		}

		public String getLastName() {
			return LastName;
		}

		public void setLastName(String lastName) {
			LastName = lastName;
		}

		public String getMobile() {
			return Mobile;
		}

		public void setMobile(String mobile) {
			Mobile = mobile;
		}

		public String getPhone() {
			return Phone;
		}

		public void setPhone(String phone) {
			Phone = phone;
		}

		public String getFax() {
			return Fax;
		}

		public void setFax(String fax) {
			Fax = fax;
		}

		public String getEmail() {
			return Email;
		}

		public void setEmail(String email) {
			Email = email;
		}



	/*	public Customer getCustomer() {
			return customer;
		}

		public void setCustomer(Customer customer) {
			this.customer = customer;
		} */



	@Version
    @Column(name = "version")
	private Integer version;
	
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

		public static Contact fromJsonToContact(String json) {
		        return new JSONDeserializer<Contact>()
		        .use(null, Contact.class).deserialize(json);
		}

		public static String toJsonArray(Collection<Contact> collection) {
		        return new JSONSerializer()
		        .exclude("*.class").deepSerialize(collection);
		}

		public static String toJsonArray(Collection<Contact> collection, String[] fields) {
		        return new JSONSerializer()
		        .include(fields).exclude("*.class").deepSerialize(collection);
		}

		                public static Collection<Contact> fromJsonArrayToCustomer(String json) {
		        return new JSONDeserializer<List<Contact>>()
		        .use("values", Customer.class).deserialize(json);
		    }


}
