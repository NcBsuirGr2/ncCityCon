package com.citycon.statistic.controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class StatisticControllerAdvice {
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleDataAccesExcpetion() {
        ModelAndView mav = new ModelAndView("errors/error");
        mav.addObject("errorMessage", "Internal server error. Please, come later. " +
                "Our programmers will do their best to fix the problem, be sure.");
        return mav;
    }
}
