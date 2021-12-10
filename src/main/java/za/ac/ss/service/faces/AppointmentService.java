package za.ac.ss.service.faces;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.security.access.annotation.Secured;

import za.ac.ss.entities.DoctorAppointment;
import za.ac.ss.enums.MedicoState;
import za.ac.ss.exception.ResourceNotFoundException;


@Secured ({"ROLE_Admin", "ROLE_Employee", "ROLE_Customer"})
public interface AppointmentService {
	 public List<DoctorAppointment> getDoctorAppointments();
	 public Optional<DoctorAppointment> getDoctorAppointment(Long id) throws ResourceNotFoundException;
	 public void save(DoctorAppointment appointment);
     public void updateDoctor(Long id,DoctorAppointment appointment);
	 public void deleteById(Long id) throws ResourceNotFoundException;
	 public void statusUpdate(Long id, MedicoState state) throws ResourceNotFoundException;
	 public Stream<DoctorAppointment> checkAllMedicoDueIn3Days();
	 public List<DoctorAppointment> getDoctorAppointmentsByUserId(Long id);
}
