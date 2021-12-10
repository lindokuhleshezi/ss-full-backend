package za.ac.ss.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import za.ac.ss.entities.CustomerToken;

@Repository
public interface CustomerTokenRepository extends JpaRepository<CustomerToken, Long>{

	Optional<CustomerToken> findByToken(UUID token);
	Optional<CustomerToken> findByIdNumber(String idNumber);
	void deleteByToken(UUID token);

}
