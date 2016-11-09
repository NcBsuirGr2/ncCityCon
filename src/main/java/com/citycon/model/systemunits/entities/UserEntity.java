package com.citycon.model.systemunits.entities;

import com.citycon.model.Grant;
/**
 * Represents all necessary information about user. It is the plain
 * java bean which must be obtained from DAO layer and be used in jsp-pages 
 *  to display city information.
 *
 * @author Mike
 * @version 1.1
 */
public class UserEntity extends Entity {
	private String name;
	private String login;
	private String password;	
	private String email;
	private Grant grant;

	public String getName() {
		return name;
	}
	public String getLogin() {
		return login;
	}
	public String getPassword() {
		return password;
	}
	public String getEmail() {
		return email;
	}
	public Grant getGrant() {
		return grant;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setGrant(Grant grant) {
		this.grant = grant;
	}
}