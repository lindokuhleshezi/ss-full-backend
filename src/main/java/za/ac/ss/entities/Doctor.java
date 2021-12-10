package za.ac.ss.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "doctor", indexes = @Index(name = "idx_id_business_name", columnList = "id,business_name"))
@EntityListeners(AuditingEntityListener.class)
public class Doctor extends Auditable<String> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@Version
	private Long version;
	
    @Column(name = "BUSINESS_NAME")
    String businessName;

    @Column(name = "PRACTICE_NO")
    String practiceNo;
    
    @Column(name = "COMPANY_REGISTRATION_NUMBER")
    String regNumber; 
    
    @Column(name = "CONTACT_NUMBER")
    String contactNumber; 
    
    @Column(name = "EMAIL")
    String email;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private DoctorAddress address;
    
}
