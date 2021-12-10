package za.ac.ss.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import za.ac.ss.entities.ResetToken;
import za.ac.ss.repository.ResetTokenRepository;
import za.ac.ss.service.faces.ResetTokenService;


@Slf4j
@Service
public class ResetTokenServiceImpl implements ResetTokenService {

	@Autowired ResetTokenRepository resetTokenRepository;
	
	@Override
	public void deleteByToken(UUID token) {
		this.resetTokenRepository.deleteByToken(token);
	}

	@Override
	public ResetToken createToken(ResetToken resetToken) {
		ResetToken token = resetTokenRepository.save(resetToken);
		return token;
	}

	@Override
	public Optional<ResetToken> findByToken(UUID token) {
		return resetTokenRepository.findByToken(token);
	}
	
	@Override
	public ResetToken findOrCreate(String username) {
		Optional<ResetToken> resetToken = resetTokenRepository.findByUsername(username);
		log.info("resetToken " + resetToken);
		if (resetToken.isPresent()) {
			return resetToken.get();
		}
		return createToken(ResetToken.builder().username(username).token(UUID.randomUUID()).build());
	}

}
