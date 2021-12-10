package za.ac.ss.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import za.ac.ss.entities.DoctorAppointment;
import za.ac.ss.exception.ResourceNotFoundException;
import za.ac.ss.service.faces.AppointmentService;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
	
	@Autowired private AppointmentService appointment;

    @GetMapping("/list")
    public List<DoctorAppointment> getDoctorAppointments() {
        return appointment.getDoctorAppointments();
    }

    @GetMapping("/{id}")
    public Optional<DoctorAppointment> getDoctorAppointment(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        return this.appointment.getDoctorAppointment(id);
    }

    @DeleteMapping("delete/{id}")
    public void deleteDoctorAppointment(@PathVariable Long id) throws ResourceNotFoundException {
        this.appointment.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public void update(@PathVariable Long id, @Valid @RequestBody DoctorAppointment appointment) {    
    	this.appointment.updateDoctor(id,appointment);
    }

    @PostMapping("/create")
    public void save(@Valid @RequestBody DoctorAppointment appointment) {
        this.appointment.save(appointment);
    }
    
    @GetMapping("/{id}/user")
    public List<DoctorAppointment> getUsersDoctorAppointments(@PathVariable Long id) {
    	return this.appointment.getDoctorAppointmentsByUserId(id);
    }
}
