/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.ss.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Version;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.ac.ss.enums.MedicoState;
/**
 *
 * @author lindokuhle
 */
@Entity(name="doctor_appointment")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="doctor_appointment")
@EntityListeners(AuditingEntityListener.class)
public class DoctorAppointment extends Auditable<String>  implements Serializable {

	private static final long serialVersionUID = -1818612829478716667L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
	private Long version;
	
    @Column(name = "APPOINTMENT_DATE")
	@Temporal(javax.persistence.TemporalType.DATE)
    private Date appointmentDate;

    @Column(name = "TIME")
    private String appointmentTime; 

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "DOCTOR_ID", updatable = false)
    private Doctor doctor;
	
	@Enumerated(EnumType.STRING)
	@Column(name="MEDICO_STATUS")
	private MedicoState medicoState;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="PROPOSED_DATE")
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date proposedDate;
	
	@ManyToOne
	@JoinTable(name = "doctor_raf_appointment", joinColumns = {
			@JoinColumn(name = "road_accident_fund_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "doctor_appointment_id", referencedColumnName = "id") })
	private RoadAccidentFund roadAccidentFund;
    
}
