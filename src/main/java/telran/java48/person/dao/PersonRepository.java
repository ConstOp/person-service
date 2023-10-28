package telran.java48.person.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import telran.java48.person.dto.CityPopulationDto;
import telran.java48.person.model.Child;
import telran.java48.person.model.Employee;
import telran.java48.person.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
//	@Query("select p from Person p where p.address.city=:cityName")
	Stream<Person> findPersonsByAddressCityIgnoreCase(@Param("cityName") String city);

//	@Query("select p from Person p where p.name=?1")
	Stream<Person> findPersonsByNameIgnoreCase(String name);

	Stream<Person> findPersonsByBirthDateBetween(LocalDate min, LocalDate max);

	@Query("select new telran.java48.person.dto.CityPopulationDto(p.address.city, count(p)) from Person p group by p.address.city order by count(p) asc")
	List<CityPopulationDto> getCitiesPopulation();

//	@Query("select c from Child c")
	Stream<Child> findChildrenBy();

//	@Query("select e from Employee e where e.salary between ?1 and ?2")
	Stream<Employee> findBySalaryBetween(int min, int max);

}
