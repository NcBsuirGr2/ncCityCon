package com.citycon.model.systemunits.entities;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Represents all necessary information about city. It is the plain
 * java bean which must be obtained from DAO layer and be used in jsp-pages 
 * to display city information.
 *
 * @author Mike
 * @version 1.2
 */
public class CityEntity extends Entity implements Serializable {

	@NotBlank(message="City name can not be empty")
	@Size(min=3, max=20, message="City name must be {min}..{max} in length")
	@Pattern(regexp="^[a-z- a-z0-9]{2,}$", flags=Pattern.Flag.CASE_INSENSITIVE, message="Invalid city name: ${validatedValue}")
	private String name;
	
	@NotBlank(message="Country name can not be empty")
	@Size(min=3, max=20, message="Country name must be {min}..{max} in length")
	@Pattern(regexp="^[a-z- a-z0-9]{2,}$", flags=Pattern.Flag.CASE_INSENSITIVE, message="Invalid country name: ${validatedValue}")
	private String countryName;

	@Min(value=0, message="Routers num can not be negative")
	private int routersNum = 0;

	// ----- Getters -----
	public String getName() {
		return name;
	}
	public String getCountryName() {
		return countryName;
	}
	public int getRoutersNum() {
		return routersNum;
	}

	// ----- Setters -----
	public void setName(String name) {
		this.name = name;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public void setRoutersNum(int routersNum) {
		this.routersNum = routersNum;
	}

	public String toString() {
		StringBuilder cityString = new StringBuilder();
		cityString.append("City: id(");
		cityString.append(id);
		cityString.append("), name(");
		cityString.append(name);
		cityString.append("), countryName(");
		cityString.append(countryName);
		cityString.append("), routersNum(");
		cityString.append(routersNum);
		cityString.append(")");
		return cityString.toString();
	}
}