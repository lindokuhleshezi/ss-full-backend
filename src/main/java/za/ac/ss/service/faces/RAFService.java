/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.ss.service.faces;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.annotation.Secured;

import za.ac.ss.entities.RoadAccidentFund;
import za.ac.ss.exception.ResourceNotFoundException;

/**
 *
 * @author lindokuhle
 * @author
 */
@Secured ({"ROLE_Admin", "ROLE_Employee", "ROLE_Customer"})
public interface RAFService {

	@Secured ({"ROLE_Admin", "ROLE_Employee"})
    public Optional<RoadAccidentFund> findById(Long id);
	@Secured ({"ROLE_Admin", "ROLE_Employee"})
    public void save(RoadAccidentFund roadAccidentFund) throws ResourceNotFoundException;
	@Secured ({"ROLE_Admin", "ROLE_Employee"})
    public List<RoadAccidentFund> findAll();
	@Secured ({"ROLE_Admin", "ROLE_Employee"})
	public RoadAccidentFund update(Long id, RoadAccidentFund roadAccidentFund) throws ResourceNotFoundException;
	@Secured ({"ROLE_Admin", "ROLE_Employee"})
	public Long daysLeft(Long id) throws ResourceNotFoundException;
	@Secured ({"ROLE_Admin", "ROLE_Employee"})
	public List<RoadAccidentFund> findByReferenceNo(String referenceno);
	@Secured ({"ROLE_Admin", "ROLE_Employee"})
	public void delete(Long id);
	@Secured ({"ROLE_Admin", "ROLE_Employee"})
	public List<RoadAccidentFund> findByUsername(String username) throws ResourceNotFoundException;
	@Secured ({"ROLE_Admin", "ROLE_Employee"})
	public List<RoadAccidentFund> findRoadAccidentFundByPersonId(Long id);
	@Secured ({"ROLE_Admin", "ROLE_Employee"})
	public Optional<RoadAccidentFund> findByDoctorAppointmentId(Long id);

}
