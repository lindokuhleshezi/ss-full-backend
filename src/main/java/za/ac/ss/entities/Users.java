package za.ac.ss.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", indexes = @Index(name = "idx_id_email_username", columnList = "id,email,username"))
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Users extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = -9099385477159572212L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false, updatable = false, insertable = false)
    private Long id;
	
	@Version
	private Long version;
	
	@Email(message = "Not a valid email")
    private String email;
    
    //@UniqueUsername(message = "username already exist")
	@NotNull(message = "username cannot be null")
    @Column(unique = true, nullable = false)
    private String username;
    
    @NotNull(message="password cannot be null")
    private String password;
    private String lastName;
    private int enabled;

    public Users(Users users) {
        this.id = users.id;
        this.email = users.email;
        this.username = users.username;
        this.password = users.password;
        this.lastName = users.lastName;
        this.enabled = users.enabled;
        this.roles = users.roles;
    }

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Role.class, cascade = {CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH} )
    @JoinTable(
            name="user_roles",
            joinColumns=
            @JoinColumn( name="user_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="role_id", referencedColumnName="id"))
    private List<Role> roles;

    @NotNull(message = "Anonymous user account not allowed, link the user account with customer or employee")
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER) 
    private Person person;

    
}
