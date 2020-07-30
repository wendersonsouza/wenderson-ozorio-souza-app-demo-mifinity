package ie.wenderson.ozorio.app.demo.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name="TBL_002_CREDTICARD")
public class CreditCard implements Serializable{

	private static final long serialVersionUID = 1L;


    private Long id;
    
    private String holderName;
    
    private String number;
    
    private String expiryDate;
    
    private User user;
    
    @Transient
    private String year;
    
    @Transient
    private String month;
    
    @Transient
    private Long operatorUserId;

	public CreditCard(Long id, String holderName, String number, String expiryDate, User user) {
		this.id = id;
		this.holderName = holderName;
		this.number = number;
		this.expiryDate = expiryDate;
		this.user = user; 
	}

	public CreditCard() {

	}

	@Id
    @SequenceGenerator(name="SQ_CREDTICARD", sequenceName="SYSTEM_SEQUENCE_CREDTICARD",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SQ_CREDTICARD")
	@Column(name="NU_ID_CREDTICARD")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
    @NotNull
    @Column(name="ST_HOLDER_NAME")
    public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	@NotNull
    @Column(name="ST_NUMBER" , length = 16)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

    @NotNull
    @Column(name="ST_EXPIRY_DATE", length = 5)
	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	@JsonIgnore
	@JsonProperty(value = "user")
    @ManyToOne(optional=false, fetch = FetchType.EAGER)
    @JoinColumn(name = "NU_ID_USER_FK")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	@Transient
	public String getYear() {
		if(!StringUtils.isEmpty(this.expiryDate)) {
			year = "20"+this.expiryDate.substring(0, this.expiryDate.indexOf("/"));
		}
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Transient
	public String getMonth() {
		if(!StringUtils.isEmpty(this.expiryDate)) {
			month = this.expiryDate.substring(this.expiryDate.indexOf("/") + 1);
		}
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
    
	public static String formattToExpiryDate(String year, String month) {
		return year + "/" + month;
	}
	
	public static String expiryDateFormartter(String year,String month) {
		LocalDate currentdate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month),1);
		return String.format("%ty", currentdate) + "/" + String.format("%tm", currentdate);
	}

	@Transient
	public Long getOperatorUserId() {
		if(this.user!= null) {
			this.operatorUserId = this.user.getId();
		}
		
		return operatorUserId;
	}

	public void setOperatorUserId(Long operatorUserId) {
		this.operatorUserId = operatorUserId;
		if(user == null) {
			user = new User();
		}
		user.setId(operatorUserId);
	}
	
}
