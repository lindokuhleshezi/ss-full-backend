package za.ac.ss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import za.ac.ss.entities.DoctorAppointment;

@Repository
public interface AppointmentRepository extends JpaRepository<DoctorAppointment, Long> {

//	@Query("SELECT da.id FROM doctor_appointment da INNER JOIN doctor_raf_appointment dra on da.id = dra.doctor_appointment_id INNER JOIN road_accident_fund raf on raf.id = dra.road_accident_fund_id INNER JOIN raf_person rp on rp.raf_id = raf.id INNER JOIN profile p on p.id = rp.person_id WHERE p.id =1")
	List<DoctorAppointment> findDoctorAppointmentByRoadAccidentFundId(Long id);
	
	
}
