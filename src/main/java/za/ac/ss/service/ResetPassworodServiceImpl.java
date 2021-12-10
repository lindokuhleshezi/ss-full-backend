package za.ac.ss.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import za.ac.ss.dto.MailRequestDTO;
import za.ac.ss.entities.ResetToken;
import za.ac.ss.entities.Users;
import za.ac.ss.exception.ResourceNotFoundException;
import za.ac.ss.repository.UsersRepository;
import za.ac.ss.service.faces.EmailService;
import za.ac.ss.service.faces.ResetPasswordService;
import za.ac.ss.service.faces.ResetTokenService;

@Slf4j
@Service
public class ResetPassworodServiceImpl implements ResetPasswordService {
	@Autowired public UsersRepository usersRepository;
	@Autowired public PasswordEncoder passwordEncoder;
	@Autowired public ResetTokenService resetTokenService;
	@Autowired public EmailService mailService;

	@Override
	@Transactional
	public void forgotPassword(String username, HttpServletRequest request) throws MalformedURLException, ResourceNotFoundException {
		log.info("username " + username);
		String host = getCurrentHost(request);
		Optional<Users> userRecord = findRequestor(username);
		log.info("is present " + userRecord);
		if (userRecord.isPresent()) {
			MailRequestDTO mailRequestDTO = mailRequestBuilder(userRecord);

			Map<String, Object> model = new HashMap<>();
			String name = userRecord.get().getLastName() != null ? userRecord.get().getLastName(): userRecord.get().getUsername();
			String url = userRecord.get().getEmail() != null ? ("http://" + host + "/#/reset-password/" + userRecord.get().getEmail() + "/"
							+ this.getToken(userRecord.get().getUsername()).getToken())
						: ("http://" + host + "/#/reset-password/" + userRecord.get().getEmail() + "/" + this.getToken(userRecord.get().getUsername()).getToken());
			
			model.put("name", name);
			model.put("url", url);
			model.put("body", "Password reset.");
			this.mailService.sendEmail(mailRequestDTO, model);
		}
	}

	private Optional<Users> findRequestor(String username) throws ResourceNotFoundException {
		Optional<Users> userRecord = this.usersRepository.findByUsername(username);
		userRecord.orElseThrow(() -> new ResourceNotFoundException(username + " doesn't exist"));
		return userRecord;
	}

	private String getCurrentHost(HttpServletRequest request) throws MalformedURLException {
		URL url = new URL(request.getRequestURL().toString());
		String host = url.getHost();
		return host;
	}

	private MailRequestDTO mailRequestBuilder(Optional<Users> userRecord) {
		return MailRequestDTO.builder().subject("Reset Password").body("Password Reset")
				.to(userRecord.get().getEmail() != null ? userRecord.get().getEmail() : userRecord.get().getEmail())
				.from("slabbutp@slabbert-admin.co.za").build();
	}

	@Override
	@Transactional
	public Optional<Users> resetPassword(String username, String token, String password)
			throws ResourceNotFoundException {
		// creating UUID
		UUID convertedToken = UUID.fromString(token);
		Optional<ResetToken> resetToken = this.resetTokenService.findByToken(convertedToken);
		Optional<Users> userDetails = this.usersRepository.findByUsername(username);

		if (resetToken.isPresent() && userDetails.isPresent()) {
			Users constructedUser = userDetails.get();
			constructedUser.setPassword(passwordEncoder.encode(password));
			this.usersRepository.save(constructedUser);
			this.resetTokenService.deleteByToken(convertedToken);
		}

		userDetails.orElseThrow(() -> new ResourceNotFoundException(username + " is not found"));
		resetToken.orElseThrow(() -> new ResourceNotFoundException(convertedToken + " doesn't exist"));

		return userDetails;
	}

	private ResetToken getToken(String username) throws ResourceNotFoundException {
		return this.resetTokenService.findOrCreate(username);
	}
}