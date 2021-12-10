package za.ac.ss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import za.ac.ss.enums.MedicoState;
import za.ac.ss.exception.ResourceNotFoundException;
import za.ac.ss.service.faces.AppointmentService;

@RestController
@RequestMapping("/medico")
public class MedicoController {
	
	@Autowired AppointmentService appointmentService;
	
	@PostMapping("/create/{id}")
	public ResponseEntity<MedicoState> create(@PathVariable Long id, @RequestBody MedicoState state) throws ResourceNotFoundException {
		appointmentService.statusUpdate(id, state);
		return new ResponseEntity<MedicoState>(state, HttpStatus.CREATED);
	}
	
	@GetMapping("/list")
	public ResponseEntity<MedicoState[]> getMedicoStates() {
		return new ResponseEntity<MedicoState[]>(MedicoState.values(), HttpStatus.OK);
	}
	
}
