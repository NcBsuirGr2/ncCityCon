package com.citycon.services;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by Vojts on 06.12.2016.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public class HelloWorld {
    @WebMethod
    public String getTestString(){
        return "test string from cityCon app";
    }
}
