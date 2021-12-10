package za.ac.ss.entities;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class ResetToken implements Serializable {

	private static final long serialVersionUID = 907944648976181932L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private UUID token;
	@Version
	private Long version;
	@Column(unique = true, nullable = false)
	private String username;
}
