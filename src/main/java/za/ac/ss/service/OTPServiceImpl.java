package za.ac.ss.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import za.ac.ss.dto.MailRequestDTO;
import za.ac.ss.entities.OTP;
import za.ac.ss.entities.Users;
import za.ac.ss.helper.GenerateOTP;
import za.ac.ss.repository.OTPRepository;
import za.ac.ss.repository.UsersRepository;
import za.ac.ss.service.faces.EmailService;
import za.ac.ss.service.faces.OTPService;

@Slf4j
@Service
public class OTPServiceImpl implements OTPService {
	@Autowired private OTPRepository otpRepository;
	@Autowired private GenerateOTP generateOTP;
	@Autowired private EmailService mailService;
	@Autowired private UsersRepository userRepository;
	
	// initialize OTP
	@Override
	@Transactional
	public void init(String email) {
		if (email != null) {
			saveOTP(email);
		} else {
			log.info("User doesn't have an email address in the system. Please contact support office");
		}
	}

	private void saveOTP(String email) {
		OTP otp = new OTP();
		otp.setEmail(email);
		otp.setOtp(generateOTP());
		this.otpRepository.save(otp);
		sendEmail(email, otp);
	}
	
	private void sendEmail(String email, OTP otp) {
		try {
			MailRequestDTO mailRequestDTO = new MailRequestDTO();
			mailRequestDTO.setTo(email);
			mailRequestDTO.setFrom("slabbutp@slabbert-admin.co.za");
			mailRequestDTO.setSubject("Verification OTP");
			mailRequestDTO.setBody("Verification OTP");
			Map<String, Object> model = new HashMap<>();
			model.put("otp", String.valueOf(otp.getOtp()));
			mailService.sendEmail(mailRequestDTO, model, "verification-otp.ftl");
		} catch (Exception e) {
			log.error("Email did not send exception " + e.getMessage());
		}
	}

	private Integer generateOTP() {
		return generateOTP.generateOTP();
	}
	
	@Override
	@Transactional
	public Optional<OTP> verify(Integer otp) {
		Optional<OTP> optData = this.otpRepository.findByOtp(otp);
		if (optData.isPresent()) {
			permitUser(optData);
			removeOTP(optData);
		}
		optData.orElseThrow(() -> new IllegalStateException("Invalid OTP"));
		return optData;
	}

	private void permitUser(Optional<OTP> optData) {
		Optional<Users> userData = userRepository.findByEmail(optData.get().getEmail());
		if (userData.isPresent()) {
			userData.get().setEnabled(1);
			this.userRepository.save(userData.get());
		} else {
			throw new IllegalStateException("Email matching the token doesn't exist");
		}
	}

	private void removeOTP(Optional<OTP> optData) {
		Optional<OTP> otp = this.otpRepository.findByOtp(optData.get().getOtp());
		if (otp.isPresent()) {
			this.otpRepository.deleteByOtp(optData.get().getOtp());
		}
		otp.orElseThrow(() -> new IllegalStateException("OTP Unknown"));
	}
	
}
