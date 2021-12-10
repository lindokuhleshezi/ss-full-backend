package za.ac.ss.service.faces;

import java.util.Optional;

import za.ac.ss.entities.OTP;

public interface OTPService {

	public void init(String email);
	public Optional<OTP> verify(Integer otp);

}