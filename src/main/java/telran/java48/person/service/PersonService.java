package telran.java48.person.service;

import telran.java48.person.dto.AddressDto;
import telran.java48.person.dto.CityPopulationDto;
import telran.java48.person.dto.PersonDto;

public interface PersonService {
	Boolean addPerson(PersonDto personDto);

	PersonDto findPersonById(Integer id);

	PersonDto removePerson(Integer id);

	PersonDto updetePersonName(Integer id, String name);

	PersonDto updetePersonAddress(Integer id, AddressDto addressDto);

	Iterable<PersonDto> findPersonsByCity(String city);

	Iterable<PersonDto> findPersonsByName(String name);

	Iterable<PersonDto> findPersonsBetweenAge(Integer minAge, Integer maxAge);

	Iterable<CityPopulationDto> getCitiesPopulation();

	Iterable<PersonDto> findAllChildren();

	Iterable<PersonDto> findEmployeeBySalary(int min, int max);

}