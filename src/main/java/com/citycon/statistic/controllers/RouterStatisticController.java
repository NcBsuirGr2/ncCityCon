package com.citycon.statistic.controllers;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.model.systemunits.orm.ORMRouterConnection;
import com.citycon.statistic.repositories.ConnectionStatisticRepository;
import com.citycon.statistic.repositories.RouterStatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/routers")
public class RouterStatisticController {
    private RouterStatisticRepository routerRepo;
    private ConnectionStatisticRepository connectionRepo;

    @Autowired
    public RouterStatisticController(RouterStatisticRepository routerRepo, ConnectionStatisticRepository connectionRepo) {
        this.routerRepo = routerRepo;
        this.connectionRepo = connectionRepo;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String RoutersStatistic(Model model) throws DAOException {

        Long count_routers = routerRepo.getCount();
        Long count_inactive_routers = routerRepo.getCountInActiveRouters();
        Long count_routers_ports = routerRepo.getCountPorts();
        Long count_connections = connectionRepo.getCount();
        float inactive_routers_percent = count_inactive_routers/(float)count_routers;
        float connections_per_router = count_connections/(float)count_routers;
        float used_ports_percent = count_connections/((float)count_routers_ports*2);

        model.addAttribute("count_routers", count_routers);
        model.addAttribute("count_routers_ports", count_routers_ports);

        model.addAttribute("count_inactive_routers", count_inactive_routers);
        model.addAttribute("inactive_routers_percent", inactive_routers_percent);

        model.addAttribute("connections_per_router", connections_per_router);
        model.addAttribute("used_ports_percent", used_ports_percent);

        return "statistic/routers";
    }
}
