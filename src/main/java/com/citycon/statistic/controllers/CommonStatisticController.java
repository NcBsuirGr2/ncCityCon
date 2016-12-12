package com.citycon.statistic.controllers;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.model.systemunits.orm.ORMCity;
import com.citycon.model.systemunits.orm.ORMRouter;
import com.citycon.model.systemunits.orm.ORMRouterConnection;
import com.citycon.model.systemunits.orm.ORMUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class CommonStatisticController {

    @RequestMapping(method = RequestMethod.GET)
    public String showCommonStatistics(Model model) {
        try {
            model.addAttribute("count_users", ORMUser.getCount());
            model.addAttribute("count_cities", ORMCity.getCount());
            model.addAttribute("count_routers", ORMRouter.getCount());
            model.addAttribute("count_connections", ORMRouterConnection.getCount());
        } catch (DAOException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "errorPage";
        }
        return "common";
    }
}
