package com.citycon.model.systemunits.entities;

import com.citycon.model.Grant;

import java.sql.Date;

/**
 * Represents all necessary information about user. It is the plain
 * java bean which must be obtained from DAO layer and be used in jsp-pages 
 * to display city information.
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
	private String group;
	private Date createDate;

	public UserEntity() {
		String names[] = {"Mike", "Alex", "Tim", "Dima", "Karin"};
		String logins[] = {"ekiM", "xelA", "miT", "amiD", "niraK"};	
		String passwords[] = {"Mikepass", "Alexpass", "Timpass", "Dimapass", "Karinpass"};	
		String emails[] = {"mike@mi.ke", "alex@al.ex", "tim@ti.im", "dima@di.ma", "karin@kar.in"};	
		int randomOne = (int)(Math.random()*names.length);

		name = names[randomOne];
		login = logins[randomOne];
		password = passwords[randomOne];
		email = emails[randomOne];
		grant = new Grant();
		
		grant.setSystemUnitsBranchLevel((int)(Math.random()*3));
		grant.setUsersBranchLevel((int)(Math.random()*3));
	}

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

    public void setGroup(String group) {
        this.group = group;
    }

	public String getGroup() {
		return group;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCreateDate() {
		return createDate;
	}
}