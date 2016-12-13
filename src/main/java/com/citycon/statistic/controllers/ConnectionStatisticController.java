package com.citycon.statistic.controllers;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.model.systemunits.orm.ORMRouterConnection;
import com.citycon.statistic.repositories.ConnectionStatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/connections")
public class ConnectionStatisticController {
    private ConnectionStatisticRepository repo;

    @Autowired
    public ConnectionStatisticController(ConnectionStatisticRepository repo) {
        this.repo = repo;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String ConnectionsStatistic(Model model) throws DAOException {

        Long count_connections = repo.getCount();
        Long count_inactive_connections = repo.getInacitveConnectionsCount();
        float inactive_connections_percent = count_inactive_connections/(float)count_connections;

        model.addAttribute("count_connections", count_connections);
        model.addAttribute("count_inactive_connections", count_inactive_connections);
        model.addAttribute("inactive_connections_percent", inactive_connections_percent);

        return "connections";
    }
}
