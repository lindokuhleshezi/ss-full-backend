package za.ac.ss.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import za.ac.ss.entities.Person;


@Repository
public interface CustomerRepository extends JpaRepository<Person, Long>  {

	public Optional<Person> findByIdNumber(String idNumber);
    @Query("select p from Person p where idNumber like %:idNumber%")
    public List<Person> searchByIdnumber(String idNumber);
	public Boolean existsByIdNumber(String idNumber);
	public Person findByPassport(String passport);

}
