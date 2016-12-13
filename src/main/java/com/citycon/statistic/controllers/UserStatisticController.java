package com.citycon.statistic.controllers;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.model.systemunits.orm.ORMUser;
import com.citycon.statistic.repositories.UserStatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/users")
public class UserStatisticController {
    UserStatisticRepository repo;

    @Autowired
    public UserStatisticController(UserStatisticRepository repo) {
        this.repo = repo;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String UsersStatistic(Model model) throws DAOException {
        model.addAttribute("count_users", repo.getCount());
        model.addAttribute("count_admins", repo.getCount("admin"));
        model.addAttribute("count_operators", repo.getCount("operator"));
        model.addAttribute("count_guests", repo.getCount("guest"));
        model.addAttribute("first_users", repo.getFirstUsers());
        model.addAttribute("last_users", repo.getLastUsers());

        return "statistic/users";
    }
}
