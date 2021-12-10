package za.ac.ss.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.ac.ss.annotation.RSAIDNumber;


@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CUSTOMER_AUTH_TOKEN")
public class CustomerToken {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private UUID token;
	
	@CreationTimestamp
	@Column(name = "CREATED_DATE_TIME")
	private LocalDateTime createdDateTime;
	
	@Column(name = "EXPIRE")
	private LocalDateTime expireTime;
	
	@RSAIDNumber(message = "ID Number is invalid")
	@Column(name = "ID_NUMBER")
	private String idNumber;
	
	@PrePersist() 
	private void setExpiry() {
		expireTime = createdDateTime.plusMinutes(30);
	}
}
