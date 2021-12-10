package za.ac.ss.service.faces;

import java.util.Optional;
import java.util.UUID;

import za.ac.ss.entities.ResetToken;

public interface ResetTokenService {

	void deleteByToken(UUID token);
	ResetToken createToken(ResetToken resetToken);
	Optional<ResetToken> findByToken(UUID token);
	ResetToken findOrCreate(String username);

}