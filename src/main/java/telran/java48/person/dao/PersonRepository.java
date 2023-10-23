package telran.java48.person.dao;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.springframework.data.repository.CrudRepository;

import telran.java48.person.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {

	Stream<Person> findPersonsByAddressCity(String city);

	Stream<Person> findPersonsByName(String name);

	Stream<Person> findPersonsByBirthDateBetween(LocalDate min, LocalDate max);
}
