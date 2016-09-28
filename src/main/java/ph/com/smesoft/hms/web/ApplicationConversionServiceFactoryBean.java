package ph.com.smesoft.hms.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import ph.com.smesoft.hms.domain.Accommodation;
import ph.com.smesoft.hms.domain.Door;
import ph.com.smesoft.hms.domain.Floor;
import ph.com.smesoft.hms.domain.Person;
import ph.com.smesoft.hms.domain.Room;
import ph.com.smesoft.hms.domain.Shift;
import ph.com.smesoft.hms.service.AccommodationService;
import ph.com.smesoft.hms.service.DoorService;
import ph.com.smesoft.hms.service.FloorService;
import ph.com.smesoft.hms.service.PersonService;
import ph.com.smesoft.hms.service.RoomService;
import ph.com.smesoft.hms.service.ShiftService;

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
    AccommodationService accommodationService;

	@Autowired
    DoorService doorService;

	@Autowired
    FloorService floorService;

	@Autowired
    PersonService personService;

	@Autowired
    RoomService roomService;

	@Autowired
    ShiftService shiftService;

	public Converter<Accommodation, String> getAccommodationToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.hms.domain.Accommodation, java.lang.String>() {
            public String convert(Accommodation accommodation) {
                return new StringBuilder().append(accommodation.getStartDate()).append(' ').append(accommodation.getEndDate()).toString();
            }
        };
    }

	public Converter<Long, Accommodation> getIdToAccommodationConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.hms.domain.Accommodation>() {
            public ph.com.smesoft.hms.domain.Accommodation convert(java.lang.Long id) {
                return accommodationService.findAccommodation(id);
            }
        };
    }

	public Converter<String, Accommodation> getStringToAccommodationConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.hms.domain.Accommodation>() {
            public ph.com.smesoft.hms.domain.Accommodation convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Accommodation.class);
            }
        };
    }

	public Converter<Door, String> getDoorToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.hms.domain.Door, java.lang.String>() {
            public String convert(Door door) {
                return new StringBuilder().append(door.getRooNumber()).append(' ').append(door.getDescription()).toString();
            }
        };
    }

	public Converter<Long, Door> getIdToDoorConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.hms.domain.Door>() {
            public ph.com.smesoft.hms.domain.Door convert(java.lang.Long id) {
                return doorService.findDoor(id);
            }
        };
    }

	public Converter<String, Door> getStringToDoorConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.hms.domain.Door>() {
            public ph.com.smesoft.hms.domain.Door convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Door.class);
            }
        };
    }

	public Converter<Floor, String> getFloorToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.hms.domain.Floor, java.lang.String>() {
            public String convert(Floor floor) {
                return new StringBuilder().append(floor.getFloorNumber()).append(' ').append(floor.getDescription()).toString();
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

	public Converter<Person, String> getPersonToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.hms.domain.Person, java.lang.String>() {
            public String convert(Person person) {
                return new StringBuilder().append(person.getPalmusId()).append(' ').append(person.getFirstName()).append(' ').append(person.getMiddleName()).append(' ').append(person.getLastName()).toString();
            }
        };
    }

	public Converter<Long, Person> getIdToPersonConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.hms.domain.Person>() {
            public ph.com.smesoft.hms.domain.Person convert(java.lang.Long id) {
                return personService.findPerson(id);
            }
        };
    }

	public Converter<String, Person> getStringToPersonConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.hms.domain.Person>() {
            public ph.com.smesoft.hms.domain.Person convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Person.class);
            }
        };
    }

	public Converter<Room, String> getRoomToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.hms.domain.Room, java.lang.String>() {
            public String convert(Room room) {
                return new StringBuilder().append(room.getRoomNumber()).append(' ').append(room.getDescription()).toString();
            }
        };
    }

	public Converter<Long, Room> getIdToRoomConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.hms.domain.Room>() {
            public ph.com.smesoft.hms.domain.Room convert(java.lang.Long id) {
                return roomService.findRoom(id);
            }
        };
    }

	public Converter<String, Room> getStringToRoomConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.hms.domain.Room>() {
            public ph.com.smesoft.hms.domain.Room convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Room.class);
            }
        };
    }


	public Converter<Shift, String> getShiftToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.hms.domain.Shift, java.lang.String>() {
            public String convert(Shift shift) {
                return new StringBuilder().append(shift.getShiftDate()).toString();
            }
        };
    }

	public Converter<Long, Shift> getIdToShiftConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.hms.domain.Shift>() {
            public ph.com.smesoft.hms.domain.Shift convert(java.lang.Long id) {
                return shiftService.findShift(id);
            }
        };
    }

	public Converter<String, Shift> getStringToShiftConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.hms.domain.Shift>() {
            public ph.com.smesoft.hms.domain.Shift convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Shift.class);
            }
        };
    }

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getAccommodationToStringConverter());
        registry.addConverter(getIdToAccommodationConverter());
        registry.addConverter(getStringToAccommodationConverter());
        registry.addConverter(getDoorToStringConverter());
        registry.addConverter(getIdToDoorConverter());
        registry.addConverter(getStringToDoorConverter());
        registry.addConverter(getFloorToStringConverter());
        registry.addConverter(getIdToFloorConverter());
        registry.addConverter(getStringToFloorConverter());
        registry.addConverter(getPersonToStringConverter());
        registry.addConverter(getIdToPersonConverter());
        registry.addConverter(getStringToPersonConverter());
        registry.addConverter(getRoomToStringConverter());
        registry.addConverter(getIdToRoomConverter());
        registry.addConverter(getStringToRoomConverter());
        registry.addConverter(getShiftToStringConverter());
        registry.addConverter(getIdToShiftConverter());
        registry.addConverter(getStringToShiftConverter());
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
