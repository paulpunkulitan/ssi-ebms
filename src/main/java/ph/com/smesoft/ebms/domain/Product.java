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
	@NamedQuery(
	   name = "findProductByid",
	   query = "SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(:searchString) "
	   		+ "or LOWER(p.unitPrice) LIKE LOWER(:searchString)"
	   	
	   )
})

public class Product {

	@NotEmpty
	@Size(max = 100)
	private String productName;
    
	private double unitPrice;
	
	@Size(max = 30)
	private String description;
	
	@ManyToOne
	private Business business;
	
	@ManyToOne
	private ItemCategory itemCategory;
	
	@ManyToOne
	private SubCategory subCategory;
	
	@ManyToOne
	private Measurement measurement;
	
	@ManyToOne
	private Brand brand;
	


	@PersistenceContext
    transient EntityManager entityManager;
	
	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("productName", "unitPrice", "description");
	 
	public static final EntityManager entityManager() {
	        EntityManager em = new Product().entityManager;
	        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
	        return em;
	}
	 
	 public static long countProduct() {
	        return entityManager().createQuery("SELECT COUNT(o) FROM Product o", Long.class).getSingleResult();
	}
	    
	public static List<Product> findAllProduct() {
	        return entityManager().createQuery("SELECT o FROM Product o", Product.class).getResultList();
	}
	
	
	public static List<Product> findAllProduct(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Product o ";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Product.class).getResultList();
    }
	
	 public static Product findProduct(Long id) {
	        if (id == null) return null;
	        return entityManager().find(Product.class, id);
	    }

	 
	public static List<Product> findProductEntries(int firstResult, int maxResults) {
	        return entityManager().createQuery("SELECT o FROM Product o", Product.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
	
	public static List<Product> findProductEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Product o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC". equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Product.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
	
	
	
	public static Product findProductId(Long id) {
		if (id == null)
			return null;
		return entityManager().find(Product.class, id);
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
	            Product attached = Product.findProduct(this.id);
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
	    public Product merge() {
	        if (this.entityManager == null) this.entityManager = entityManager();
	        Product merged = this.entityManager.merge(this);
	        this.entityManager.flush();
	        return merged;
	    }
	    
	    public String toString() {
	        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	    }

	
		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public double getUnitPrice() {
			return unitPrice;
		}

		public void setUnitPrice(double unitPrice) {
			this.unitPrice = unitPrice;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Business getBusiness() {
			return business;
		}

		public void setBusiness(Business business) {
			this.business = business;
		}

		public ItemCategory getItemCategory() {
			return itemCategory;
		}

		public void setItemCategory(ItemCategory itemCategory) {
			this.itemCategory = itemCategory;
		}

		public SubCategory getSubCategory() {
			return subCategory;
		}

		public void setSubCategory(SubCategory subCategory) {
			this.subCategory = subCategory;
		}

		public Measurement getMeasurement() {
			return measurement;
		}

		public void setMeasurement(Measurement measurement) {
			this.measurement = measurement;
		}

		public Brand getBrand() {
			return brand;
		}

		public void setBrand(Brand brand) {
			this.brand = brand;
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

		public static Product fromJsonToProduct(String json) {
		        return new JSONDeserializer<Product>()
		        .use(null, Product.class).deserialize(json);
		}

		public static String toJsonArray(Collection<Product> collection) {
		        return new JSONSerializer()
		        .exclude("*.class").deepSerialize(collection);
		}

		public static String toJsonArray(Collection<Product> collection, String[] fields) {
		        return new JSONSerializer()
		        .include(fields).exclude("*.class").deepSerialize(collection);
		}

		public static Collection<Product> fromJsonArrayToProduct(String json) {
		        return new JSONDeserializer<List<Product>>()
		        .use("values", Product.class).deserialize(json);
		}


}
