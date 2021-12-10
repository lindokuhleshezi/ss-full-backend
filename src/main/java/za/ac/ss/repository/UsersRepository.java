package za.ac.ss.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import za.ac.ss.entities.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
	public Optional<Users> findByUsername(String username);
	public Optional<Users> findByEmail(String email);
	public Optional<Users> findByPersonId(Long id);
}
