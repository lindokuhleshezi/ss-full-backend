package za.ac.ss.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import za.ac.ss.entities.Doctor;
import za.ac.ss.exception.ResourceNotFoundException;
import za.ac.ss.service.faces.DoctorService;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired private DoctorService doctorService;

    @GetMapping
    public List<Doctor> getDoctors() {
        return doctorService.getDoctors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>  getDoctor(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		Optional<Doctor> doctor = this.doctorService.getDoctor(id);
		return doctor.map(doc -> {
			return ResponseEntity.ok(doc);
		}).orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
    }

    @DeleteMapping("delete/{id}")
    public void deleteDoctor(@PathVariable Long id) throws ResourceNotFoundException {
        this.doctorService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public void update(@PathVariable Long id, @RequestBody @Valid Doctor doctor) throws ResourceNotFoundException {    
        this.doctorService.updateDoctor(id,doctor);
    }

    @PostMapping
    public Doctor saveDoctor(@Valid @RequestBody Doctor doctor) {
        return doctorService.saveDoctor(doctor);
    }

    @GetMapping("/searchByFullName")
    public List<Doctor> searchByFullName(String fullName) {
        return this.doctorService.searchByFullName(fullName);
    }
}
