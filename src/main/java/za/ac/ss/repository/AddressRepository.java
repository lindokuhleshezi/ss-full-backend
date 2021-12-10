package za.ac.ss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import za.ac.ss.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
