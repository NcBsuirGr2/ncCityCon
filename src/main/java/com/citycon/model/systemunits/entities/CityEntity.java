package com.citycon.model.systemunits.entities;

import com.citycon.model.Grant;
/**
 * Represents all necessary information about city. It is the plain
 * java bean which must be obtained from DAO layer and be used in jsp-pages 
 * to display city information.
 *
 * @author Mike
 * @version 1.1
 */
public class CityEntity extends Entity {	
	private String name;
	private String countryName;

	public CityEntity() {
		String names[] = {"Minsk", "Moskow", "London", "Berlin", "Paris", "Tokio"};
		String countryNames[] = {"Belarus", "Russia", "UK", "Germany", "France", "Japan"};	
		int randomOne = (int)(Math.random()*names.length);
		name = names[randomOne];
		countryName = countryNames[randomOne];
	}

	public String getName() {
		return name;
	}
	public String getCountryName() {
		return countryName;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
}