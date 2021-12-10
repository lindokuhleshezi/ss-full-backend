package za.ac.ss.helper;

import java.util.Random;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GenerateOTP {
	private final static Integer LEN = 4;
	
	public Integer generateOTP() {
		return Integer.valueOf(new String(generateCode(LEN)));
	}
	
	// Generate GenerateOTP
	private String generateCode(int len) 
    { 
        String numbers = "0123456789"; 
  
       Random rndm_method = new Random();
        
        char[] otp = new char[len]; 
  
        for (int i = 0; i < len; i++) 
        { 
            otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length())); 
        } 
        log.info("OTP: " + Integer.parseInt(String.valueOf(otp)));
        return String.valueOf(otp); 
    } 
}
