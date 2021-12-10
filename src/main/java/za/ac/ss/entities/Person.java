/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.ss.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Email;

import org.hibernate.annotations.ColumnTransformer;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.ac.ss.annotation.RSAIDNumber;
import za.ac.ss.helper.AttributeEncryptor;

/**
 *
 * @author lindokuhle
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PROFILE", indexes = {@Index(columnList = "id, email")})
@EntityListeners(AuditingEntityListener.class)
public class Person  extends Auditable<String> {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    String lastName;

    @Column(name = "FULL_NAME")
    private String fullName;

    @RSAIDNumber(message = "ID Number is invalid")
    @Convert(converter = AttributeEncryptor.class)
    @Column(name = "ID_NUMBER", nullable = true, unique = true)
    @ColumnTransformer(
    		read = "PGP_SYM_DECRYPT(id_number::bytea, 'secret-key-12345')",
            write = "PGP_SYM_ENCRYPT (?, 'secret-key-12345')::text"
    )
    private String idNumber;
    
    @Column(name = "PASSPORT", nullable = true, unique = true)
    @Convert(converter = AttributeEncryptor.class)
    @ColumnTransformer(
    		read = "PGP_SYM_DECRYPT(passport::bytea, 'secret-key-12345')",
            write = "PGP_SYM_ENCRYPT (?, 'secret-key-12345')::text"
    )
    private String passport;

    @Email(message = "Please provide a correct email address")
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "CONTACT")
    @Convert(converter = AttributeEncryptor.class)
    @ColumnTransformer(
    		read = "PGP_SYM_DECRYPT(contact::bytea, 'secret-key-12345')",
            write = "PGP_SYM_ENCRYPT (?, 'secret-key-12345')::text"
    )
    private String contact;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Address address;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Users users;

	@Version
	private Long version;
	
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinTable(name = "emergency_contact_person")
	private List<ContactPerson> contactPerson;
	
}
