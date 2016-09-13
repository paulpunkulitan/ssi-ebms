package ph.com.smesoft.hms.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import ph.com.smesoft.hms.domain.Floor;
import ph.com.smesoft.hms.service.FloorService;

@Configurable
/**
 * A central place to register application converters and formatters. 
 */
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@SuppressWarnings("deprecation")
	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	@Autowired
    FloorService floorService;
	
	public Converter<Floor, String> getFloorToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.hms.domain.Floor, java.lang.String>() {
            public String convert(Floor floor) {
                return "(no displayable fields)";
            }
        };
    }

	public Converter<Long, Floor> getIdToFloorConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.hms.domain.Floor>() {
            public ph.com.smesoft.hms.domain.Floor convert(java.lang.Long id) {
                return floorService.findFloor(id);
            }
        };
    }

	public Converter<String, Floor> getStringToFloorConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.hms.domain.Floor>() {
            public ph.com.smesoft.hms.domain.Floor convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Floor.class);
            }
        };
    }
	
	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getFloorToStringConverter());
        registry.addConverter(getIdToFloorConverter());
        registry.addConverter(getStringToFloorConverter());
       
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
