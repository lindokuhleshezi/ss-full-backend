package za.ac.ss.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import za.ac.ss.entities.DoctorAppointment;
import za.ac.ss.enums.MedicoState;
import za.ac.ss.enums.MedicoWorkflow;
import za.ac.ss.exception.ResourceNotFoundException;
import za.ac.ss.repository.AppointmentRepository;
import za.ac.ss.service.faces.AppointmentService;

@Slf4j
@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired private AppointmentRepository repository;
	@Autowired private MedicoWorkflowFactory mediWorkFlowService;
	@Override
	public List<DoctorAppointment> getDoctorAppointments() {
		return this.repository.findAll();
	}

	@Override
	public Optional<DoctorAppointment> getDoctorAppointment(Long id) throws ResourceNotFoundException {
		return this.repository.findById(id);
	}

	@Override
	@Transactional
	public void save(DoctorAppointment appointment) {
		try {
			DoctorAppointment app = DoctorAppointment.builder()
				.doctor(appointment.getDoctor())
				.appointmentDate(appointment.getAppointmentDate())
				.appointmentTime(appointment.getAppointmentTime())
				.medicoState(mediWorkFlowService.run(MedicoWorkflow.PENDING, appointment.getId()))
				.description(appointment.getDescription())
				.id(appointment.getId()).version(appointment.getVersion()).build();
			this.repository.save(app);
		} catch (Exception ex) {
			log.info("save doctor appoinment - " + ex.getMessage());
		}
	}

	@Override
	@Transactional
	public void updateDoctor(Long id, DoctorAppointment appointment) {
		Optional<DoctorAppointment> appOption = this.repository.findById(id);
		if (appOption.isPresent()) {
				this.repository.save(DoctorAppointment.builder()
						.doctor(appointment.getDoctor())
						.appointmentDate(appointment.getAppointmentDate())
						.appointmentTime(appointment.getAppointmentTime())
						.medicoState(mediWorkFlowService.run(MedicoWorkflow.valueOf(appointment.getMedicoState().name()), id))
						.description(appointment.getDescription())
						.id(appointment.getId())
						.version(appointment.getVersion()).build());
		} else {
			log.info("update doctor " + new Exception());
		}
	}

	@Override
	public void deleteById(Long id) throws ResourceNotFoundException {
		Optional<DoctorAppointment> appOption = this.repository.findById(id);
		if (appOption.isPresent()) {
			this.repository.deleteById(id);
		} else {
			log.info("delete of an appointment failed " + new Exception());
			throw new IllegalStateException("Something went wrong");
		}
	}
	
	@Override
	public void statusUpdate(Long id, MedicoState state) throws ResourceNotFoundException {
		Optional<DoctorAppointment> doctorAppointment = this.getDoctorAppointment(id);
		MedicoState medicoState = doctorAppointment.get().getMedicoState();
		if (medicoState == null) {
			doctorAppointment.get().setMedicoState(MedicoState.PENDING);
			this.repository.save(doctorAppointment.get());
		} else {
			doctorAppointment.get().setMedicoState(state);
		}
		
	}

	@Override
	public Stream<DoctorAppointment> checkAllMedicoDueIn3Days() {
		LocalDate currentDate = LocalDate.now();
		List<DoctorAppointment> appointments = this.getDoctorAppointments();
		List<DoctorAppointment> filter = appointments.stream().filter(a -> ChronoUnit.DAYS.between(a.getAppointmentDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), currentDate) >= 1 && ChronoUnit.DAYS.between(a.getAppointmentDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), currentDate) <= 3).collect(Collectors.toList());
		
		return filter.stream();
	}

	@Override
	public List<DoctorAppointment> getDoctorAppointmentsByUserId(Long id) {
		return this.repository.findDoctorAppointmentByRoadAccidentFundId(id);
	}

}
