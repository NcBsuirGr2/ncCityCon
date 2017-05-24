/**
 * UserModel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 *
 * @author Alex
 * @version 2.0
 */

package com.citycon.clientService;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserModel  implements java.io.Serializable {

    @NotBlank(message="User login can not be blank")
    @Size(min=3, max=15, message="User login must be {min}..{max} in length")
    @Pattern(regexp="^[a-z-_a-z0-9]{2,}$", flags=Pattern.Flag.CASE_INSENSITIVE, message="Invalid user login: ${validatedValue}")
    private String username;

    @NotBlank(message="User password can not be blank")
    @Size(min=6, max=15, message="User password must be {min}..{max} in length")
    @Pattern(regexp="^[-a-z0-9!#$%&'*+/=?^_`{|}~]{2,}$", flags=Pattern.Flag.CASE_INSENSITIVE, message="Invalid user password: ${validatedValue}")
    private String password;

    @NotBlank(message="User email can not be blank")
    @Size(max=30, message="User email must be less {max}")
    @Email(message="Invalid user email: ${validatedValue}")
    private String email;

    public UserModel() {
    }

    public UserModel(
           String username,
           String password,
           String email) {
           this.username = username;
           this.password = password;
           this.email = email;
    }


    /**
     * Gets the username value for this UserModel.
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this UserModel.
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * Gets the password value for this UserModel.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this UserModel.
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * Gets the email value for this UserModel.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }


    /**
     * Sets the email value for this UserModel.
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    private Object __equalsCalc = null;

    /**
     * Equal UserModel objects on unique parameters from DataBase: Login and Email
     *
     * @param obj
     * @return
     */
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof UserModel)) return false;
        UserModel other = (UserModel) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = ((this.username == null && other.getUsername() == null) ||
                (this.username != null &&
                        this.username.equals(other.getUsername()))) ||
                ((this.email == null && other.getEmail() == null) ||
                        (this.email != null &&
                                this.email.equals(other.getEmail())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getUsername() != null) {
            _hashCode += getUsername().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UserModel.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://services/", "userModel"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("", "username"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("", "password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("email");
        elemField.setXmlName(new javax.xml.namespace.QName("", "email"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
