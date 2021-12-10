package za.ac.ss.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import za.ac.ss.entities.Doctor;
import za.ac.ss.exception.ResourceNotFoundException;
import za.ac.ss.repository.DoctorRepository;
import za.ac.ss.service.faces.DoctorService;

@Slf4j
@Service
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	private DoctorRepository doctorDao;
	
	@Override
	public List<Doctor> getDoctors() {
		return doctorDao.findAll();
	}

	@Override
	public Optional<Doctor> getDoctor(Long id) throws ResourceNotFoundException {
		Optional<Doctor> doctorData = doctorDao.findById(id);
		doctorData.isPresent();
		doctorData.orElseThrow(() -> new ResourceNotFoundException(id + " is not found"));
		return doctorData;
	}

	@Override
	@Transactional
	public Doctor saveDoctor(Doctor doctorParam) {
		log.info("doctorParam {}", doctorParam.getAddress());
		return this.doctorDao.save(Doctor.builder()
				.id(doctorParam.getId())
				.businessName(doctorParam.getBusinessName())
				.practiceNo(doctorParam.getPracticeNo())
				.regNumber(doctorParam.getRegNumber())
				.contactNumber(doctorParam.getContactNumber())
				.email(doctorParam.getEmail())
				.address(doctorParam.getAddress()).build());
	}

	@Override
	public void deleteById(Long id) throws ResourceNotFoundException {
		Optional<Doctor> doctorData = doctorDao.findById(id);
		doctorData.orElseThrow(() -> new ResourceNotFoundException(id + " is not found"));
		if (doctorData.isPresent()) {
			doctorDao.deleteById(id);
		}
	}

	@Override
	public List<Doctor> searchByFullName(String fullName) {
		return doctorDao.seachByFullName(fullName);
	}

	@Override
	@Transactional
	public void updateDoctor(Long id, Doctor doc) throws ResourceNotFoundException {
		Optional<Doctor> doctorData = doctorDao.findById(id);
		doctorData.orElseThrow(() -> new ResourceNotFoundException(id + " is not found"));

		try {
			if (doctorData.isPresent()) {
				doctorData.get().setId(doctorData.get().getId());
				
				doctorData.get().getAddress().setLine1(doc.getAddress().getLine1());;
				doctorData.get().getAddress().setLine2(doc.getAddress().getLine2());
				doctorData.get().getAddress().setCity(doc.getAddress().getCity());
				doctorData.get().getAddress().setProvince(doc.getAddress().getProvince());
				doctorData.get().getAddress().setZipCode(doc.getAddress().getZipCode());
				
				doctorData.get().setEmail(doc.getEmail());
				doctorData.get().setBusinessName(doc.getBusinessName());
				doctorData.get().setContactNumber(doc.getContactNumber());
				doctorData.get().setPracticeNo(doc.getPracticeNo());
				doctorData.get().setRegNumber(doc.getRegNumber());
				doctorData.get().setVersion(doctorData.get().getVersion());
				this.doctorDao.save(doctorData.get());
			}
		} catch (Exception e) {
			log.error("Doctor update failed at: {}", e);
			throw new IllegalStateException("Doctor update failed at: {}", e);
		}
	}

}
