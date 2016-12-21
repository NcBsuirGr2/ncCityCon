package com.citycon.services.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Simple bean class to return within web service.
 *
 * @author Mike
 * @version 1.1
 */
@XmlRootElement(name = "User")
@XmlType(propOrder = {"login", "password", "email"})
public class User {
    private String login;
    private String password;
    private String email;

    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    @XmlElement(name = "login")
    public void setLogin(String login) {
        this.login = login;
    }
    @XmlElement(name = "password")
    public void setPassword(String password) {
        this.password = password;
    }
    @XmlElement(name = "email")
    public void setEmail(String email) {
        this.email = email;
    }
}
