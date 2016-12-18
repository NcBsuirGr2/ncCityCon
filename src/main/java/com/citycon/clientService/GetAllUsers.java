/**
 * GetAllUsers.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.citycon.clientService;

public interface GetAllUsers extends java.rmi.Remote {
    public String getTestString() throws java.rmi.RemoteException;
    public String sayHelloTo(String text) throws java.rmi.RemoteException;
    public com.citycon.clientService.UserModel getTestUser() throws java.rmi.RemoteException;
    public com.citycon.clientService.UserModel[] getUsers() throws java.rmi.RemoteException;
}
