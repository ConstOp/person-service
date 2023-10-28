package telran.java48.person.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.experimental.PackagePrivate;
import telran.java48.person.dto.AddressDto;
import telran.java48.person.dto.CityPopulationDto;
import telran.java48.person.dto.PersonDto;
import telran.java48.person.service.PersonService;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

	final PersonService personService;

	@PostMapping
	public Boolean addPerson(@RequestBody PersonDto personDto) {
		return personService.addPerson(personDto);
	}

	@GetMapping("/{id}")
	public PersonDto findPersonById(@PathVariable Integer id) {
		return personService.findPersonById(id);
	}

	@DeleteMapping("/{id}")
	PersonDto removePerson(@PathVariable Integer id) {

		return personService.removePerson(id);
	}

	@PutMapping("/{id}/name/{name}")
	PersonDto updetePersonName(@PathVariable Integer id, @PathVariable String name) {
		return personService.updetePersonName(id, name);
	}

	@PutMapping("/{id}/address")
	PersonDto updetePersonAddress(@PathVariable Integer id, @RequestBody AddressDto addressDto) {
		return personService.updetePersonAddress(id, addressDto);
	}

	@GetMapping("/city/{city}")
	Iterable<PersonDto> findPersonsByCity(@PathVariable String city) {
		return personService.findPersonsByCity(city);

	}

	@GetMapping("/name/{name}")
	Iterable<PersonDto> findPersonsByName(@PathVariable String name) {
		return personService.findPersonsByName(name);
	}

	@GetMapping("/ages/{minAge}/{maxAge}")
	Iterable<PersonDto> findPersonsBetweenAge(@PathVariable Integer minAge, @PathVariable Integer maxAge) {
		return personService.findPersonsBetweenAge(minAge, maxAge);
	}

	@GetMapping("/population/{city}")
	Iterable<CityPopulationDto> getCitiesPopulation() {
		return personService.getCitiesPopulation();
	}
	
	@GetMapping("/children")
	Iterable<PersonDto> findAllChildren() {
		return personService.findAllChildren();
	}
	
	@GetMapping("/salary/{min}/{max}")
	public Iterable<PersonDto> findEmployeeBySalary(@PathVariable Integer min, @PathVariable Integer max) {
		return personService.findEmployeeBySalary(min, max);
	}
}
