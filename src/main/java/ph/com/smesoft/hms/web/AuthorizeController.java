package ph.com.smesoft.hms.web;

//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.util.List;
//import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ph.com.smesoft.hms.domain.Person;
import ph.com.smesoft.hms.service.PersonService;
import ph.com.smesoft.hms.service.ShiftService;
import ph.com.smesoft.hms.repository.PersonRepository;
import ph.com.smesoft.hms.reference.PersonType;
import ph.com.smesoft.hms.domain.Accommodation;
import ph.com.smesoft.hms.service.AccommodationService;
import ph.com.smesoft.hms.domain.Room;
import ph.com.smesoft.hms.domain.Shift;
import ph.com.smesoft.hms.domain.Door;
import ph.com.smesoft.hms.domain.Floor;
import ph.com.smesoft.hms.service.DoorService;
import ph.com.smesoft.hms.service.FloorService;
import ph.com.smesoft.hms.service.RoomService;

@Controller
@RequestMapping("/authorize")
public class AuthorizeController {

	@Autowired
	PersonRepository personRepository;

	@Autowired
	PersonService personService;

	@Autowired
	AccommodationService accommodationService;

	@Autowired
	DoorService doorService;

	@Autowired
	ShiftService shiftService;

	@Autowired
	RoomService roomService;

	@Autowired
	FloorService floorService;

	@PersistenceContext
	private EntityManager em;

	@RequestMapping(value = "/{pvId}/{palmusId}", headers = "Accept=application/json")
	public ResponseEntity<String> authorizePersonForm(@PathVariable("pvId") String pvId, @PathVariable("palmusId") String palmusId) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		/*String pvid = "PVID-00001";
		String palmusId = "PALMUS-01";*/
		System.out.println("ETO AY PVID" + pvId);
		System.out.println("ETO AY PALMUSID" + palmusId);
		System.out.println("TEST-0");
		List<Person> personList = personService.findAllPeople();
		List<Accommodation> accommodationList = accommodationService.findAllAccommodations();
		List<Shift> shiftList = shiftService.findAllShifts();
		List<Door> doorList = doorService.findAllDoors();
		List<Room> roomList = roomService.findAllRooms();
		List<Floor> floorList = floorService.findAllFloors();
		String isauthorized = "INVALID";
		System.out.println("pvId : " + pvId);
		System.out.println("palmusId : " + palmusId);
		System.out.println("TEST-1");
		try {
			
			System.out.println("TEST-2");
			for (int i = 0; i < personList.size(); i++) {
				System.out.println("TEST-3");
				Person person = personList.get(i);
				System.out.println(person);
				Long idd = person.getId();
				System.out.println("person.getId() : " + idd);
				String pID = person.getPvId();
				System.out.println("pID : " + pID);
				
				if (pvId.equalsIgnoreCase(person.getPvId())) {
					System.out.println("pvId.equalsIgnoreCase(person.getPvId()) : " + pvId.equalsIgnoreCase(person.getPvId()));
					Long id = person.getId();
					System.out.println("id : " + id);
					PersonType pType = person.getPersonType();
					String personType = pType.toString();
					System.out.println("personType : " + personType);
					if (personType.equalsIgnoreCase("Customer")) {
						for (int j = 0; j < accommodationList.size(); j++) {
							Accommodation accomm = accommodationList.get(j);
							if (id == accomm.person.getId()) {
								Room room = accomm.getRoom();
								Long roomId = room.getId();
								System.out.println("roomId : " + roomId);
								for (int k = 0; k < doorList.size(); k++) {
									Door door = doorList.get(k);
									System.out.println("door : " + door);
									if (roomId == door.room.getId()) {
										String palmussId = door.getPalmusId();
										if (palmusId.equalsIgnoreCase(palmussId)) {
											isauthorized = "VALID";
											System.out.println("isauthorized : " + isauthorized);
										}
									}
								}
							}
						}
					}
					// else {
					// for (int l=0; l<shiftList.size(); l++)
					// {
					// Shift shift = shiftList.get(l);
					// System.out.println(shift);
					// if (id==shift.person.getId()){
					// System.out.println(shift.person.getId());
					// for(int o=0; o<floorList.size(); o++){
					// Floor floors = floorList.get(o);
					// System.out.println(floors);
					// Long floorId = floors.getId();
					// System.out.println(floorId);
					// if (floorId == shift.floor.getId()) {
					// for (int n=0; n<roomList.size(); n++){
					// Room rooms = roomList.get(n);
					// Room room = (Room) shift.getRooms();
					// Long roomId = room.getId();
					// System.out.println(roomId);
					// if (floorId == rooms.floor.getId()){
					// for (int m=0; m<doorList.size(); m++){
					// Door door = doorList.get(m);
					// System.out.println(door);
					// if (roomId==door.room.getId()){
					// String palmussId = door.getPalmusId();
					// if (palmusId.equalsIgnoreCase(palmussId)){
					// isauthorized = "True";
					// System.out.println(isauthorized);
					//
					// }
					// else{
					// isauthorized = "False";
					// }
					// return new ResponseEntity<String>(isauthorized, headers,
					// HttpStatus.OK);
					// }
					// }
					// }
					// }
					// }
					// }
					// }
					// }
					// }
				}

			}
			/*
			 * String answer = "YES"; return new ResponseEntity<String>(answer,
			 * headers, HttpStatus.OK);
			 */
		} catch (Exception e) {
			System.out.println("ERROR!");
			return new ResponseEntity<String>("{\"ERROR\":" + e.getMessage() + "\"}", headers,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(isauthorized, headers, HttpStatus.OK);
	}
}