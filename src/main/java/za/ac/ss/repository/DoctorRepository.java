package za.ac.ss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import za.ac.ss.entities.Doctor;


@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    
    @Query("select p from Doctor p where fullName like %?1%")
    List<Doctor> seachByFullName(String idNumber);
}
