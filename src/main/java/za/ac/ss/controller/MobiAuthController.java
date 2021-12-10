package za.ac.ss.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import za.ac.ss.dto.CustomerAuthDTO;
import za.ac.ss.dto.CustomerOTPDTO;
import za.ac.ss.entities.OTP;
import za.ac.ss.service.faces.ExternalAuthService;
import za.ac.ss.service.faces.OTPService;

@RestController
@RequestMapping("/mobi")
public class MobiAuthController {

	@Autowired ExternalAuthService externalAuthService;
	@Autowired OTPService otpService;
	
	@PostMapping("/auth")
	public UUID externalAuth(@RequestBody CustomerAuthDTO customerAuthDTO) {		
		return this.externalAuthService.createAuth(customerAuthDTO.getIdNumber());
	}
	
	@PostMapping("/otp")
	public Optional<OTP> externalOtp(@RequestBody CustomerOTPDTO customerOTPDTO) {		
		return this.otpService.verify(customerOTPDTO.getOtp());
	}
}
	