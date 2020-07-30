package ie.wenderson.ozorio.app.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="TBL_001_USER")
public class User implements Serializable{

	private static final long serialVersionUID = 1L;


    private Long id;
    

    private String username;
    

    private String password;
    

    private Role role;

    
	public User() {
		super();
	}

	public User(Long id, String username, String password, Role role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	@Id
    @SequenceGenerator(name="SQ_USER", sequenceName="SYSTEM_SEQUENCE_USER",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SQ_USER")
	@Column(name="NU_ID_USER")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    @NotNull
    @Length(min = 1, max = 30)
    @Column(name="ST_USERNAME", length = 30)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

    @NotNull
    @Length(min=1, max=2000)
    @Column(name="ST_PASSWORD", length = 2000)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="ST_ROLE", columnDefinition = "char(2) default 'AD'")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
