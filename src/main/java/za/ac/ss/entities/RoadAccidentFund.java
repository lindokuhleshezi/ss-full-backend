/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.ss.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Version;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.ac.ss.enums.LitigationProcess;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(indexes = {@Index(name = "idx_id_reference_no", columnList = "id,reference_no")})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class RoadAccidentFund extends Auditable<String>  implements Serializable {
	
	private static final long serialVersionUID = 6947937972042510616L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Version
	private Long version;

	@Column(name = "HOSPITAL")
	private String hospital;

	@Column(name = "HOSPITAL_CONTACT_NUMBER")
	private String hospitalContactNumber;

	@Column(name = "DATE_OF_ACCIDENT")
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date dateOfAccident;

	@Column(name = "COURT_DATE")
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date courtDate;

	@Column(name = "CLAIM_LODGE_DATE")
	private LocalDateTime claimLodgeDate;

	@Column(name = "LITIGATION")
	@Enumerated(EnumType.STRING)
	private LitigationProcess litigationProcess;

	@Column(name = "MERIT")
	private String merit;

	@OneToOne
	@JoinColumn(name ="category_id")
	private Category category;

	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.REMOVE})
	@JoinTable(name = "doctor_raf_appointment", joinColumns = {
			@JoinColumn(name = "road_accident_fund_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "doctor_appointment_id", referencedColumnName = "id") })
	private List<DoctorAppointment> doctorAppointment;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinTable(name = "raf_person", joinColumns = {
			@JoinColumn(name = "raf_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "person_id", referencedColumnName = "id") })
	private Person person;

	@Column(name="REFERENCE_NO")
	private String referenceNumber;

	@Column(name = "DATE_SUBMITTED")
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date dateSubmited;
	
	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinTable(name = "raf_document", joinColumns = {
			@JoinColumn(name = "road_accident_fund_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "id", referencedColumnName = "id") })
	private List<FileStore> fileStore;
	
	@PrePersist
	public void changeStatus() {
		litigationProcess = LitigationProcess.IN_PROCESS;
	}
}
