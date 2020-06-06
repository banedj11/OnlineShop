package com.baka.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "address")
public class Address {

	@Id
	@Column(name = "address_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Please enter street")
	@Column(name = "street")
	private String street;
	
	@NotEmpty(message = "Please enter coty")
	@Column(name = "city")
	private String city;
	
	@NotEmpty(message = "Please enter state")
	@Column(name = "state")
	private String state;
	
	@NotEmpty(message = "Please enter zip code")
	@Column(name = "zip_code")
	private String zipCode;
	
	@Column(name = "default_address")
	private boolean defaultAddress = true;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	public Address(String street, String city, String state, String zipCode, boolean defaultAddress) {
		
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.defaultAddress = defaultAddress;
	}
	
	public Address() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isDefaultAddress() {
		return defaultAddress;
	}

	public void setDefaultAddress(boolean defaultAddress) {
		this.defaultAddress = defaultAddress;
	}

	
	@Override
	public String toString() {
		return "Address [id=" + id + ", street=" + street + ", city=" + city + ", state=" + state + ", zipCode="
				+ zipCode + "]";
	}
	
	
}
