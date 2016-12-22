package com.citycon.statistic.controllers;

import com.citycon.statistic.repositories.UserMergeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Provides statistic for merged users from OrderExpress.
 *
 * @author Alex
 * @version 2.0
 */
@Controller
@RequestMapping("/merge")
public class UserMergeStatisticController {
    private UserMergeRepository repo;

    @Autowired
    public UserMergeStatisticController(UserMergeRepository repo) {
        this.repo = repo;
    }

    /**
     * Provides statistics for merged users from OrderExpress.
     *
     * @param model     model to set attributes
     * @return          view statistics of merge
     */
    @RequestMapping(method = RequestMethod.GET)
    public String UserMergeStatistic(Model model){
        model.addAttribute("mergeStatistic", repo.merge());
        return "statistic/mergeUsers";
    }
}
