/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.ss.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import za.ac.ss.entities.RoadAccidentFund;

/**
 *
 * @author lindokuhle
 */
@Repository
public interface RoadAccidentFundRepository extends JpaRepository<RoadAccidentFund, Long> {

	List<RoadAccidentFund> findByReferenceNumberContaining(String referenceno);
	List<RoadAccidentFund> findByPersonId(Long personId);
	List<RoadAccidentFund> findRoadAccidentFundByPersonId(Long id);
	Optional<RoadAccidentFund> findDoctorAppointmentById(Long id);

}
