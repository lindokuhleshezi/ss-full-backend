/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.ss.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import za.ac.ss.entities.Doctor;
import za.ac.ss.entities.DoctorAppointment;
import za.ac.ss.entities.Person;
import za.ac.ss.entities.RoadAccidentFund;
import za.ac.ss.entities.Users;
import za.ac.ss.exception.ResourceNotFoundException;
import za.ac.ss.repository.DoctorRepository;
import za.ac.ss.repository.RoadAccidentFundRepository;
import za.ac.ss.service.faces.AppointmentService;
import za.ac.ss.service.faces.CustomerService;
import za.ac.ss.service.faces.RAFService;
import za.ac.ss.service.faces.UserService;

/**
 *
 * @author lindokuhle
 * 
 */
@Slf4j
@Service
@Transactional
public class RAFServiceImpl implements RAFService {

	@Autowired private RoadAccidentFundRepository roadAccidentFundDAO;
	@Autowired private CustomerService customerService;
	@Autowired private DoctorRepository doctorRepository;
	@Autowired private UserService userService;
	@Autowired private AppointmentService appointmentService;

	@Override
	public Optional<RoadAccidentFund> findById(Long id) {
		Optional<RoadAccidentFund> roadAccident = this.roadAccidentFundDAO.findById(id);
		roadAccident.isPresent();
		roadAccident.orElseThrow(() -> new IllegalStateException(String.format("Road Accident record with %s doesn't exist", id)));
		return roadAccident;
	}

	@Override
	@Transactional
	public void save(RoadAccidentFund roadAccidentFund) throws ResourceNotFoundException {
		Optional<Person> customerData =  this.findCustomerInfo(roadAccidentFund.getPerson().getId());
		List<DoctorAppointment> appointments = findDoctorsByAppointment(roadAccidentFund);
		try {
			if (customerData.isPresent()) {
				RoadAccidentFund roadAccidentFundData = RoadAccidentFund.builder()
						.courtDate(roadAccidentFund.getCourtDate())
						.hospital(roadAccidentFund.getHospital())
						.dateOfAccident(roadAccidentFund.getDateOfAccident())
						.referenceNumber(roadAccidentFund.getReferenceNumber())
						.claimLodgeDate(roadAccidentFund.getClaimLodgeDate())
						.doctorAppointment(appointments)
						.litigationProcess(roadAccidentFund.getLitigationProcess())
						.person(customerData.get())
						.category(roadAccidentFund.getCategory())
						.merit(roadAccidentFund.getMerit())
						.dateSubmited(roadAccidentFund.getDateSubmited())
						.courtDate(roadAccidentFund.getCourtDate())
						.hospitalContactNumber(roadAccidentFund.getHospitalContactNumber())
						.claimLodgeDate(roadAccidentFund.getClaimLodgeDate()).build();
				
				this.roadAccidentFundDAO.save(roadAccidentFundData);
			}
		} catch (Exception e) {
			log.error("Error happenend on road accident save " + e);
		}
	}

	private Optional<Person> findCustomerInfo(Long id) throws ResourceNotFoundException {
		return this.customerService.findById(id);
	}

	private List<DoctorAppointment> findDoctorsByAppointment(RoadAccidentFund roadAccidentFund) {
		DoctorAppointment appData = new DoctorAppointment();
		List<DoctorAppointment> appointmentData = new ArrayList<DoctorAppointment>();
		try {
			for (DoctorAppointment appointment : roadAccidentFund.getDoctorAppointment()) {
				appData = appointment;
				Optional<Doctor> doc = this.doctorRepository.findById(appointment.getDoctor().getId());
				doc.isPresent();
				doc.orElseThrow(() -> new IllegalStateException(
						String.format("Doctor %s is not found ", appointment.getDoctor().getId())));
				appData.setDoctor(doc.get());
				appointmentData.add(appData);
			}
		} catch (Exception e) {
			log.error("Error happened whilst trying to find a doctor by Appointment info or details" + e);
		}

		return appointmentData;
	}

	@Override
	public List<RoadAccidentFund> findAll() {

		List<RoadAccidentFund> roadAccidentData = this.roadAccidentFundDAO.findAll();
		if (roadAccidentData.size() == 0) {
			log.warn("There are not Road Accident records");
		}
		return roadAccidentData;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		Optional<RoadAccidentFund> roadAccident = this.roadAccidentFundDAO.findById(id);
		if (roadAccident.isPresent()) {
			roadAccidentFundDAO.deleteById(id);
		}
		roadAccident.orElseThrow(
				() -> new IllegalStateException(String.format("Road Accident record with %s doesn't exist", id)));
	}

	@Override
	@Transactional
	public RoadAccidentFund update(Long id, RoadAccidentFund roadAccidentFund) throws ResourceNotFoundException {

		Optional<Person> customerData = this.findCustomerInfo(roadAccidentFund.getPerson().getId());
		RoadAccidentFund accidentFund2 = roadAccidentFundDAO.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("record not found"));
		List<DoctorAppointment> appointments = getDoctorsByAppointment(roadAccidentFund);
		if (customerData.isPresent()) {
			//accidentFund2.setId(accidentFund2.getId());
			accidentFund2.setHospital(roadAccidentFund.getHospital());
			accidentFund2.setDateOfAccident(roadAccidentFund.getDateOfAccident());
			accidentFund2.setCourtDate(roadAccidentFund.getCourtDate());
			accidentFund2.setClaimLodgeDate(roadAccidentFund.getClaimLodgeDate());
			accidentFund2.setReferenceNumber(roadAccidentFund.getReferenceNumber());
			accidentFund2.setLitigationProcess(roadAccidentFund.getLitigationProcess());
			accidentFund2.setHospitalContactNumber(roadAccidentFund.getHospitalContactNumber());
			accidentFund2.setPerson(customerData.get());
			accidentFund2.setVersion(roadAccidentFund.getVersion());
			accidentFund2.setCategory(roadAccidentFund.getCategory());
			accidentFund2.setMerit(roadAccidentFund.getMerit());
			accidentFund2.setDateSubmited(roadAccidentFund.getDateSubmited());
			accidentFund2.setDoctorAppointment(appointments);
		}
		return this.roadAccidentFundDAO.save(accidentFund2);

	}

	private List<DoctorAppointment> getDoctorsByAppointment(RoadAccidentFund roadAccidentFund) throws ResourceNotFoundException {
		if (roadAccidentFund.getId() != null) {
			Optional<DoctorAppointment> appData = this.appointmentService.getDoctorAppointment(roadAccidentFund.getId());
			List<DoctorAppointment> appointmentData = new ArrayList<DoctorAppointment>();
			if (appData.isPresent()) {
				for (DoctorAppointment appointment : roadAccidentFund.getDoctorAppointment()) {
					Optional<Doctor> doc = this.doctorRepository.findById(appointment.getDoctor().getId());
					doc.isPresent();
					doc.orElseThrow(() -> new IllegalStateException(String.format("Doctor %s is not found ", appointment.getDoctor().getId())));
					appointment.setId(appData.get().getId());
					appointment.setDoctor(doc.get());
					appointment.setVersion(appData.get().getVersion() != null ? appData.get().getVersion() : 0);
					appointmentData.add(appointment);
					roadAccidentFund.setDoctorAppointment(appointmentData);
				}
			} else {
				for (DoctorAppointment appointment : roadAccidentFund.getDoctorAppointment()) {
					Optional<Doctor> doc = this.doctorRepository.findById(appointment.getDoctor().getId());
					doc.isPresent();
					doc.orElseThrow(() -> new IllegalStateException(String.format("Doctor %s is not found ", appointment.getDoctor().getId())));
					appointment.setDoctor(doc.get());
					appointmentData.add(appointment);
					roadAccidentFund.setDoctorAppointment(appointmentData);
				}
			}
			
			return roadAccidentFund.getDoctorAppointment();
			
		} else {
			return findDoctorsByAppointment(roadAccidentFund);
		}
	}
	
	@Override
	public Long daysLeft(Long id) throws ResourceNotFoundException {
		LocalDate now = LocalDate.now();
		Optional<RoadAccidentFund> roadAccidentFundData = this.roadAccidentFundDAO.findById(id);
		roadAccidentFundData.orElseThrow(() -> new ResourceNotFoundException(id + " is not found"));
		LocalDate startDate = null;
		if (roadAccidentFundData.isPresent() && roadAccidentFundData.get().getClaimLodgeDate() != null) {
			startDate = roadAccidentFundData.get().getClaimLodgeDate().toLocalDate();
		}
		return startDate != null ? (120 - ChronoUnit.DAYS.between(startDate, now)) : 0L;
	}

	@Override
	public List<RoadAccidentFund> findByReferenceNo(String referenceno) {
		return this.roadAccidentFundDAO.findByReferenceNumberContaining(referenceno);
	}

	@Override
	@Transactional
	public List<RoadAccidentFund> findByUsername(String username) throws ResourceNotFoundException {
		Optional<Users> user = this.userService.findByUsername(username);
		List<RoadAccidentFund> roadAccidentData = null;
		try {
			Optional<Person> customerInfo = this.findCustomerInfo(user.get().getPerson().getId());
			if (customerInfo.isPresent())
				roadAccidentData = this.roadAccidentFundDAO.findByPersonId(customerInfo.get().getId());
			if (roadAccidentData.size() == 0)
				log.warn(String.format("% have no history of road accident fund case", username));

			customerInfo.orElseThrow(() -> new IllegalStateException(String.format("Profile associated with %s is not available ", username)));

		} catch (Exception e) {
			log.error("Error happened whilst trying to find road accident record by username ", e);
		}

		return roadAccidentData;
	}

	@Override
	public List<RoadAccidentFund> findRoadAccidentFundByPersonId(Long id) {
		return this.roadAccidentFundDAO.findRoadAccidentFundByPersonId(id);
	}
	
	@Override
	public Optional<RoadAccidentFund> findByDoctorAppointmentId(Long id) {
		return this.roadAccidentFundDAO.findDoctorAppointmentById(id);
	}

}
