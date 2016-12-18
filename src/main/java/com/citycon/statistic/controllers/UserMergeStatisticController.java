package com.citycon.statistic.controllers;

import com.citycon.statistic.repositories.UserMergeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by Vojts on 18.12.2016.
 */
@Controller
@RequestMapping("/merge")
public class UserMergeStatisticController {
    private UserMergeRepository repo;

    @Autowired
    public UserMergeStatisticController(UserMergeRepository repo) {
        this.repo = repo;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String UserMergeStatistic(Model model){
        model.addAttribute("mergeStatistic", repo.merge());
        return "statistic/mergeUsers";
    }
}
