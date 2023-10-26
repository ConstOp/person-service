package telran.java48.person.service;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice.Return;
import telran.java48.person.dao.PersonRepository;
import telran.java48.person.dto.AddressDto;
import telran.java48.person.dto.ChildDto;
import telran.java48.person.dto.CityPopulationDto;
import telran.java48.person.dto.EmployeeDto;
import telran.java48.person.dto.PersonDto;
import telran.java48.person.dto.exeptions.PersonNotFoundException;
import telran.java48.person.model.Address;
import telran.java48.person.model.Child;
import telran.java48.person.model.Employee;
import telran.java48.person.model.Person;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService, CommandLineRunner {

	final PersonRepository personRepository;
	final ModelMapper modelMapper;

	@Override
	@Transactional
	public Boolean addPerson(PersonDto personDto) {
		if (personRepository.existsById(personDto.getId())) {
			return false;
		}
		personRepository.save((Person) modelMapper.map(personDto, checkTypeClass(personDto)));
		return true;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		return (PersonDto) modelMapper.map(person, checkTypeClass(person));
	}

	@Override
	@Transactional
	public PersonDto removePerson(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		personRepository.delete(person);
		return (PersonDto) modelMapper.map(person, checkTypeClass(person));
	}

	@Override
	@Transactional
	public PersonDto updetePersonName(Integer id, String name) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		person.setName(name);
//		personRepository.save(person);
		return (PersonDto) modelMapper.map(person, checkTypeClass(person));
	}

	@Override
	@Transactional
	public PersonDto updetePersonAddress(Integer id, AddressDto addressDto) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		person.setAddress(modelMapper.map(addressDto, Address.class));
//		personRepository.save(person);
		return (PersonDto) modelMapper.map(person, checkTypeClass(person));
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsByCity(String city) {
		return personRepository.findPersonsByAddressCityIgnoreCase(city)
				.map(p -> (PersonDto) modelMapper.map(p, checkTypeClass(p))).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsByName(String name) {
		return personRepository.findPersonsByNameIgnoreCase(name)
				.map(p -> (PersonDto) modelMapper.map(p, checkTypeClass(p))).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsBetweenAge(Integer minAge, Integer maxAge) {
		LocalDate nowDate = LocalDate.now();
		return personRepository.findPersonsByBirthDateBetween(nowDate.minusYears(maxAge), nowDate.minusYears(minAge))
				.map(p -> (PersonDto) modelMapper.map(p, checkTypeClass(p))).collect(Collectors.toList());
	}

	@Override
	public Iterable<CityPopulationDto> getCitiesPopulation() {
		return personRepository.getCitiesPopulation();
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		if (personRepository.count() == 0) {
			Person person = new Person(1000, "John", LocalDate.of(1985, 4, 11),
					new Address("Tel Aviv", "Iben Gvirol", 87));
			Child child = new Child(2000, "Moshe", LocalDate.of(2018, 7, 5), new Address("Ashkelon", "Bar Kohva", 21),
					"Shalom");
			Employee employee = new Employee(3000, "Sarah", LocalDate.of(1995, 11, 23),
					new Address("Rehovot", "Herzl", 7), "Motorola", 20000);
			Child child1 = new Child(4000, "Moshe", LocalDate.of(2018, 7, 5), new Address("Ashkelon", "Bar Kohva", 21),
					"Shalom");
			personRepository.save(person);
			personRepository.save(child);
			personRepository.save(employee);
			personRepository.save(child1);
		}

	}

	@Override
	@Transactional
	public Iterable<PersonDto> findAllChildren() {
		return personRepository.findAllChildren().map(p -> (PersonDto) modelMapper.map(p, checkTypeClass(p)))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public Iterable<PersonDto> findEmployeesBySalary(Integer minSalary, Integer maxSalary) {
		return personRepository.findEmployeesBySalary(minSalary, maxSalary)
				.map(p -> (PersonDto) modelMapper.map(p, checkTypeClass(p))).collect(Collectors.toList());
	}

	public static Class<?> checkTypeClass(Object person) {
//		String personClassString = person.getClass().getSimpleName();
//		if (personClassString.endsWith("Dto")) {
//			System.out.println(Class.forName(personClassString.substring(0, personClassString.length() - 3)));
//			return Class.forName(personClassString.substring(0, personClassString.length() - 3));
//		} else {
//			System.out.println(Class.forName(personClassString + "Dto"));
//			return Class.forName(personClassString + "Dto");
//		}
		if (person instanceof Child) {
			return ChildDto.class;
		}
		if (person instanceof ChildDto) {
			return Child.class;
		}
		if (person instanceof Employee) {
			return EmployeeDto.class;
		}
		if (person instanceof EmployeeDto) {
			return Employee.class;
		}
		if (person instanceof Person) {
			return PersonDto.class;
		} else
			return Person.class;
	}
}