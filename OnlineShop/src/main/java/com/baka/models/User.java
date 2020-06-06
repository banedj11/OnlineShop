package com.baka.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;


@Entity
@Table(name = "user")
@DynamicUpdate
public class User {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "first_name")
	@NotEmpty(message = "Please enter first name")
	@NotNull
	private String firstName;
	
	@Column(name = "last_name")
	@NotEmpty(message = "Please enter last name")
	@NotNull
	private String lastName;
	
	@Column(name = "email", unique = true, nullable = false)
	@NotEmpty(message = "Please enter email address")
	@Email(message = "Invalid email address")
	@NotNull
	private String email;
	
	@Column(name = "password", updatable = true, nullable = false)
	@NotEmpty(message = "Please enter password")
	@NotNull
	@Size(min = 4, message = "minimum 4 characters")
	private String password;
	
	@Transient
	private String confirmPassword;
	
    @Column(name = "mobile")
	@NotNull(message = "Required Field")
	@Size(min = 9, message = "Must be minimum 9 numbers")
    @Pattern(regexp="(^$|[0-9]{10})", message = "Invalid mobile number")
	private String mobile;
	
	@Column(name = "enable")
	private boolean enable = true;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	private List<Address> addresses;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Cart cart;
	
	@OneToMany(mappedBy = "user")
	private List<Order> orders;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "user_roles", joinColumns = {@JoinColumn(name = "user_id")},
	                             inverseJoinColumns = {@JoinColumn(name = "role_id")})
	private List<Role> roles;
	
    
	
	public User(String firstName, String lastName,String email,String password,String confirmPassword,
			    String mobile, boolean enable) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.mobile = mobile;
		this.enable = enable;
		
	}

	public User() {}

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
    public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", mobile=" + mobile + "]";
	}

    
    
   
	

	
	
	
	
	
	
}
