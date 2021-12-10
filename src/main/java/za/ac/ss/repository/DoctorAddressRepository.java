package za.ac.ss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import za.ac.ss.entities.DoctorAddress;

@Repository
public interface DoctorAddressRepository extends JpaRepository<DoctorAddress, Long> {

}
