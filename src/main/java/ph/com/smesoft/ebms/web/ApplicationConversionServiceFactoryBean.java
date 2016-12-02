package ph.com.smesoft.ebms.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import ph.com.smesoft.ebms.domain.Barangay;
import ph.com.smesoft.ebms.domain.Business;
import ph.com.smesoft.ebms.domain.City;
import ph.com.smesoft.ebms.domain.Contact;
import ph.com.smesoft.ebms.domain.Country;
import ph.com.smesoft.ebms.domain.Customer;
import ph.com.smesoft.ebms.domain.Customertype;
import ph.com.smesoft.ebms.domain.Floor;
import ph.com.smesoft.ebms.domain.Industrytype;
import ph.com.smesoft.ebms.domain.Locationtype;
import ph.com.smesoft.ebms.domain.State;
import ph.com.smesoft.ebms.domain.Street;
import ph.com.smesoft.ebms.domain.Area;
import ph.com.smesoft.ebms.service.BarangayService;
import ph.com.smesoft.ebms.service.BusinessService;
import ph.com.smesoft.ebms.service.CityService;
import ph.com.smesoft.ebms.service.ContactService;
import ph.com.smesoft.ebms.service.CountryService;
import ph.com.smesoft.ebms.service.CustomerService;
import ph.com.smesoft.ebms.service.CustomertypeService;
import ph.com.smesoft.ebms.service.FloorService;
import ph.com.smesoft.ebms.service.IndustrytypeService;
import ph.com.smesoft.ebms.service.LocationtypeService;
import ph.com.smesoft.ebms.service.StateService;
import ph.com.smesoft.ebms.service.StreetService;
import ph.com.smesoft.ebms.service.AreaService;

@Configurable
/**
 * A central place to register application converters and formatters. 
 */
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	@Autowired
    FloorService floorService;

	public Converter<Floor, String> getFloorToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.ebms.domain.Floor, java.lang.String>() {
            public String convert(Floor floor) {
                return new StringBuilder().append(floor.getFloorNumber()).append(' ').append(floor.getDescription()).toString();
            }
        };
    }

	public Converter<Long, Floor> getIdToFloorConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.ebms.domain.Floor>() {
            public ph.com.smesoft.ebms.domain.Floor convert(java.lang.Long id) {
                return floorService.findFloor(id);
            }
        };
    }

	public Converter<String, Floor> getStringToFloorConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.ebms.domain.Floor>() {
            public ph.com.smesoft.ebms.domain.Floor convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Floor.class);
            }
        };
    }

	@Autowired
    AreaService areaService;

	public Converter<Area, String> getAreaToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.ebms.domain.Area, java.lang.String>() {
            public String convert(Area area) {
                return new StringBuilder().append(area.getAreaName()).toString();
            }
        };
    }

	public Converter<Long, Area> getIdToAreaConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.ebms.domain.Area>() {
            public ph.com.smesoft.ebms.domain.Area convert(java.lang.Long id) {
                return areaService.findArea(id);
            }
        };
    }

	public Converter<String, Area> getStringToAreaConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.ebms.domain.Area>() {
            public ph.com.smesoft.ebms.domain.Area convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Area.class);
            }
        };
    }

	

	@Autowired
    CustomerService customerService;

	public Converter<Customer, String> getCustomerToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.ebms.domain.Customer, java.lang.String>() {
            public String convert(Customer customer) {
                return new StringBuilder().append(customer.getCustomerName()).toString();
            }
        };
    }

	public Converter<Long, Customer> getIdToCustomerConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.ebms.domain.Customer>() {
            public ph.com.smesoft.ebms.domain.Customer convert(java.lang.Long id) {
                return customerService.findCustomer(id);
            }
        };
    }
	
	public Converter<String, Customer> getStringToCustomerConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.ebms.domain.Customer>() {
            public ph.com.smesoft.ebms.domain.Customer convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Customer.class);
            }
        };
    }
	

	@Autowired
    BusinessService businessService;

	public Converter<Business, String> getBusinessToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.ebms.domain.Business, java.lang.String>() {
            public String convert(Business customerType) {
                return new StringBuilder().append(customerType.getBusinessName()).toString();
            }
        };
    }

	public Converter<Long, Business> getIdToBusinessConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.ebms.domain.Business>() {
            public ph.com.smesoft.ebms.domain.Business convert(java.lang.Long id) {
                return businessService.findBusiness(id);
            }
        };
    }

	public Converter<String, Business> getStringToBusinessConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.ebms.domain.Business>() {
            public ph.com.smesoft.ebms.domain.Business convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Business.class);
            }
        };
    }
	
	@Autowired
    CustomertypeService customerTypeService;

	public Converter<Customertype, String> getCustomertypeToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.ebms.domain.Customertype, java.lang.String>() {
            public String convert(Customertype customerType) {
                return new StringBuilder().append(customerType.getCustomerTypeName()).toString();
            }
        };
    }

	public Converter<Long, Customertype> getIdToCustomertypeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.ebms.domain.Customertype>() {
            public ph.com.smesoft.ebms.domain.Customertype convert(java.lang.Long id) {
                return customerTypeService.findCustomertype(id);
            }
        };
    }

	public Converter<String, Customertype> getStringToCustomertypeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.ebms.domain.Customertype>() {
            public ph.com.smesoft.ebms.domain.Customertype convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Customertype.class);
            }
        };
    }
	
	@Autowired
    IndustrytypeService industryTypeService;

	public Converter<Industrytype, String> getIndustrytypeToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.ebms.domain.Industrytype, java.lang.String>() {
            public String convert(Industrytype industryType) {
                return new StringBuilder().append(industryType.getIndustryTypeName()).toString();
            }
        };
    }

	public Converter<Long, Industrytype> getIdToIndustrytypeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.ebms.domain.Industrytype>() {
            public ph.com.smesoft.ebms.domain.Industrytype convert(java.lang.Long id) {
                return industryTypeService.findIndustrytype(id);
            }
        };
    }

	public Converter<String, Industrytype> getStringToIndustrytypeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.ebms.domain.Industrytype>() {
            public ph.com.smesoft.ebms.domain.Industrytype convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Industrytype.class);
            }
        };
    }
	
	@Autowired
    LocationtypeService LocationTypeService;

	public Converter<Locationtype, String> getLocationtypeToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.ebms.domain.Locationtype, java.lang.String>() {
            public String convert(Locationtype LocationType) {
                return new StringBuilder().append(LocationType.getLocationTypeName()).toString();
            }
        };
    }

	public Converter<Long, Locationtype> getIdToLocationtypeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.ebms.domain.Locationtype>() {
            public ph.com.smesoft.ebms.domain.Locationtype convert(java.lang.Long id) {
                return LocationTypeService.findLocationType(id);
            }
        };
    }

	public Converter<String, Locationtype> getStringToLocationtypeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.ebms.domain.Locationtype>() {
            public ph.com.smesoft.ebms.domain.Locationtype convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Locationtype.class);
            }
        };
    }	
	
	@Autowired
    CountryService countryService;

	public Converter<Country, String> getCountryToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.ebms.domain.Country, java.lang.String>() {
            public String convert(Country country) {
                return new StringBuilder().append(country.getCountryName()).toString();
            }
        };
    }

	public Converter<Long, Country> getIdToCountryConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.ebms.domain.Country>() {
            public ph.com.smesoft.ebms.domain.Country convert(java.lang.Long id) {
                return countryService.findCountry(id);
            }
        };
    }

	public Converter<String, Country> getStringToCountryConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.ebms.domain.Country>() {
            public ph.com.smesoft.ebms.domain.Country convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Country.class);
            }
        };
    }
	
	@Autowired
    CityService cityService;

	public Converter<City, String> getCityToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.ebms.domain.City, java.lang.String>() {
            public String convert(City city) {
                return new StringBuilder().append(city.getCityName()).toString();
            }
        };
    }

	public Converter<Long, City> getIdToCityConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.ebms.domain.City>() {
            public ph.com.smesoft.ebms.domain.City convert(java.lang.Long id) {
                return cityService.findCity(id);
            }
        };
    }

	public Converter<String, City> getStringToCityConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.ebms.domain.City>() {
            public ph.com.smesoft.ebms.domain.City convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), City.class);
            }
        };
    }

	
	@Autowired
    BarangayService barangayService;

	public Converter<Barangay, String> getBarangayToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.ebms.domain.Barangay, java.lang.String>() {
            public String convert(Barangay barangay) {
                return new StringBuilder().append(barangay.getBarangayName()).toString();
            }
        };
    }

	public Converter<Long, Barangay> getIdToBarangayConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.ebms.domain.Barangay>() {
            public ph.com.smesoft.ebms.domain.Barangay convert(java.lang.Long id) {
                return barangayService.findBarangay(id);
            }
        };
    }

	public Converter<String, Barangay> getStringToBarangayConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.ebms.domain.Barangay>() {
            public ph.com.smesoft.ebms.domain.Barangay convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Barangay.class);
            }
        };
    }

	
	@Autowired
    StreetService streetService;

	public Converter<Street, String> getStreetToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.ebms.domain.Street, java.lang.String>() {
            public String convert(Street street) {
                return new StringBuilder().append(street.getStreetName()).toString();
            }
        };
    }

	public Converter<Long, Street> getIdToStreetConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.ebms.domain.Street>() {
            public ph.com.smesoft.ebms.domain.Street convert(java.lang.Long id) {
                return streetService.findStreet(id);
            }
        };
    }

	public Converter<String, Customer> getStringToStreetConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.ebms.domain.Customer>() {
            public ph.com.smesoft.ebms.domain.Customer convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Customer.class);
            }
        };
    }
	
	@Autowired
    StateService stateService;

	public Converter<State, String> getStateToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.ebms.domain.State, java.lang.String>() {
            public String convert(State state) {
                return new StringBuilder().append(state.getStateName()).toString();
            }
        };
    }

	public Converter<Long, State> getIdToStateConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.ebms.domain.State>() {
            public ph.com.smesoft.ebms.domain.State convert(java.lang.Long id) {
                return stateService.findState(id);
            }
        };
    }

	public Converter<String, State> getStringToStateConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.ebms.domain.State>() {
            public ph.com.smesoft.ebms.domain.State convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), State.class);
            }
        };
    }
	

	@Autowired
    ContactService contactService;

	public Converter<Contact, String> getContactToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.ebms.domain.Contact, java.lang.String>() {
            public String convert(Contact contact) {
                return new StringBuilder().append(contact.getFirstName()).append(contact.getMiddleName()).append(contact.getLastName()).toString();
            }
        };
    }

	public Converter<Long, Contact> getIdToContactConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.ebms.domain.Contact>() {
            public ph.com.smesoft.ebms.domain.Contact convert(java.lang.Long id) {
                return contactService.findContact(id);
            }
        };
    }
	
	public Converter<String, Contact> getStringToContactConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.ebms.domain.Contact>() {
            public ph.com.smesoft.ebms.domain.Contact convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Contact.class);
            }
        };
    }
	
	
	public void installLabelConverters(FormatterRegistry registry) {
      
        registry.addConverter(getFloorToStringConverter());
        registry.addConverter(getIdToFloorConverter());
        registry.addConverter(getStringToFloorConverter());
        
        registry.addConverter(getCustomerToStringConverter());
        registry.addConverter(getIdToCustomerConverter());
        registry.addConverter(getStringToCustomerConverter());
           
        registry.addConverter(getBusinessToStringConverter());
        registry.addConverter(getIdToBusinessConverter());
        registry.addConverter(getStringToBusinessConverter());
        
        registry.addConverter(getCustomertypeToStringConverter());
        registry.addConverter(getIdToCustomertypeConverter());
        registry.addConverter(getStringToCustomertypeConverter());
        
        registry.addConverter(getIndustrytypeToStringConverter());
        registry.addConverter(getIdToIndustrytypeConverter());
        registry.addConverter(getStringToIndustrytypeConverter());
        
        registry.addConverter(getLocationtypeToStringConverter());
        registry.addConverter(getIdToLocationtypeConverter());
        registry.addConverter(getStringToLocationtypeConverter());
        
        registry.addConverter(getCountryToStringConverter());
        registry.addConverter(getIdToCountryConverter());
        registry.addConverter(getStringToCountryConverter());
        
        registry.addConverter(getStateToStringConverter());
        registry.addConverter(getIdToStateConverter());
        registry.addConverter(getStringToStateConverter());
        
        registry.addConverter(getCityToStringConverter());
        registry.addConverter(getIdToCityConverter());
        registry.addConverter(getStringToCityConverter());
        
        registry.addConverter(getBarangayToStringConverter());
        registry.addConverter(getIdToBarangayConverter());
        registry.addConverter(getStringToBarangayConverter());
        
        registry.addConverter(getStreetToStringConverter());
        registry.addConverter(getIdToStreetConverter());
        registry.addConverter(getStringToStreetConverter());
        
        registry.addConverter(getContactToStringConverter());
        registry.addConverter(getIdToContactConverter());
        registry.addConverter(getStringToContactConverter());
    
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
	
	
}
