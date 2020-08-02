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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ie.wenderson.ozorio.app.demo.common.CipherUtil;

@Entity
@Table(name="TBL_001_USER")
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String password;

    private Role role;
    
    private String roleName;


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
	
	public User(String username, String password, String roleName) {
		super();
		this.username = username;
		this.password = password;
		this.roleName = roleName;
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
    @Length(min = 1, max = 15)
    @Column(name="ST_USERNAME")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

    @NotNull
    @Length(min=1, max=2000)
    @Column(name="ST_PASSWORD")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@JsonIgnore
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="ST_ROLE", columnDefinition = "char(2) default 'AD'")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
    @Transient
	public String getRoleName() {
    	if(role!= null) {
    		roleName = role.getDescription();
    	}
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public void encryptPassword() throws Exception{
		if(!StringUtils.isEmpty(this.password)){
			password = CipherUtil.encrypt(this.password, CipherUtil.secretKey);
		}
		
	}
	
	public void decryptPassword() throws Exception{
		if(!StringUtils.isEmpty(this.password)){
			password = CipherUtil.decrypt(this.password, CipherUtil.secretKey);
		}
	}
}
