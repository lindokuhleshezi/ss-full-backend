package za.ac.ss.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import za.ac.ss.entities.OTP;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {

	public Optional<OTP> findByOtp(Integer otp);
	public void deleteByOtp(Integer otp);

}
