package za.ac.ss.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import za.ac.ss.entities.ResetToken;

@Repository
public interface ResetTokenRepository extends JpaRepository<ResetToken, Long> {
	void deleteByToken(UUID token);
	Optional<ResetToken> findByToken(UUID token);
	Optional<ResetToken> findByUsername(String username);

}
