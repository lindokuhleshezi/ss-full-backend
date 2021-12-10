package za.ac.ss.service.faces;

import java.util.List;
import java.util.Optional;

import za.ac.ss.entities.Doctor;
import za.ac.ss.exception.ResourceNotFoundException;



public interface DoctorService {
	 public List<Doctor> getDoctors();
	 public Optional<Doctor> getDoctor(Long id) throws ResourceNotFoundException;
	 public Doctor saveDoctor(Doctor doctor);
     public void updateDoctor(Long id,Doctor doctor) throws ResourceNotFoundException;
	 public void deleteById(Long id) throws ResourceNotFoundException;
	 public List<Doctor> searchByFullName(String fullName);
}
