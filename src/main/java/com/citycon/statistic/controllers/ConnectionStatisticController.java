package com.citycon.statistic.controllers;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.model.systemunits.orm.ORMRouterConnection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/connections")
public class ConnectionStatisticController {
    @RequestMapping(method = RequestMethod.GET)
    public String ConnectionsStatistic(Model model) throws DAOException {

        model.addAttribute("count_connections", ORMRouterConnection.getCount());
        model.addAttribute("count_ports", ORMRouterConnection.countPorts());

        return "connections";
    }
}
