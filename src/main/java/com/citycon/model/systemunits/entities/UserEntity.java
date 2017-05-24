package com.citycon.model.systemunits.entities;

import com.citycon.model.Grant;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Email;

import java.io.Serializable;
import java.sql.Date;

/**
 * Represents all necessary information about user. It is the plain
 * java bean which must be obtained from DAO layer and be used in jsp-pages 
 * to display city information.
 *
 * @author Mike
 * @version 2.0
 */
public class UserEntity extends Entity implements Serializable {
	@NotBlank(message="User name can not be blank")
	@Size(min=3, max=15, message="User name must be {min}..{max} in length")
	@Pattern(regexp="^[a-z-_A-Z0-9]{2,}$", flags=Pattern.Flag.CASE_INSENSITIVE, message="Invalid user name: ${validatedValue}")
	private String name;

	@NotBlank(message="User login can not be blank")
	@Size(min=3, max=15, message="User login must be {min}..{max} in length")
	@Pattern(regexp="^[a-z-_A-Z0-9]{2,}$", flags=Pattern.Flag.CASE_INSENSITIVE, message="Invalid user login: ${validatedValue}")
	private String login;


	@NotBlank(message="User password can not be blank")
	@Size(min=6, max=15, message="User password must be {min}..{max} in length")
	@Pattern(regexp="^[-a-zA-Z0-9!#$%&'*+/=?^_`{|}~]{6,}$", flags=Pattern.Flag.CASE_INSENSITIVE, message="Invalid user password: ${validatedValue}")
	private String password;	

	@NotBlank(message="User email can not be blank")
	@Size(max=30, message="User email must be less {max}")
	@Email(message="Invalid user email: ${validatedValue}")
	private String email;

	private Grant grant;

	@NotBlank(message="User group can not be blank")
	@Pattern(regexp="^(Admin|Guest|Operator)$", flags=Pattern.Flag.CASE_INSENSITIVE, message="Invalid user group: ${validatedValue}")
	private String group;

	private Date createDate;

	// ----- Getters -----
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
	public String getGroup() {
		return group;
	}

	// ----- Setters -----
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

    public void setGroup(String group) {
        this.group = group;
    }

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	
	public String toString() {
		StringBuilder userString = new StringBuilder();
		userString.append("User: id(");
		userString.append(id);
		userString.append("), name(");
		userString.append(name);
		userString.append("), login(");
		userString.append(login);
		userString.append("), password(");
		userString.append(password);
		userString.append("), email(");
		userString.append(email);
		userString.append("), grant(");
		userString.append(grant);
		userString.append("), group(");
		userString.append(group);
		userString.append("), createDate(");
		userString.append(createDate);
		userString.append(")");
		return userString.toString();
	}
	public boolean equals(Object comp) {
		if (this == comp) return true;
		if (!(this instanceof UserEntity)) return false;
		return this.id == ((UserEntity)comp).getId();
	}
}