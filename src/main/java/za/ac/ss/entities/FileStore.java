package za.ac.ss.entities;

import java.io.Serializable;

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
import javax.persistence.Version;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.ac.ss.enums.DocumentProcess;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="documents")
@EntityListeners(AuditingEntityListener.class)
public class FileStore  extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 2067053094806317532L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	Long id;

	@Version
	@Column(name = "version")
	private Long version;

	@Column(name = "name", unique = true, nullable = false)
	private String name;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "path")
	private String path;
	
	@Column(name = "size")
	private double size;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "raf_document")
	private RoadAccidentFund roadAccidentFund;
	
	@OneToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@Enumerated(EnumType.STRING)
	private DocumentProcess documentProcess; //Approved, Rejected

}
