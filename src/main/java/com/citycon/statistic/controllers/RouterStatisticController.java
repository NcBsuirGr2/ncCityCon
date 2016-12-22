package com.citycon.statistic.controllers;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.model.systemunits.entities.RouterEntity;
import com.citycon.model.systemunits.orm.ORMRouterConnection;
import com.citycon.statistic.repositories.ConnectionStatisticRepository;
import com.citycon.statistic.repositories.RouterStatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Provides common statistic about routers and statistic
 * for the concrete router
 *
 * @author Mike
 * @version 2.0
 */
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

    /**
     * Provides common routers statistic.
     *
     * @param model     model to set attributes
     * @return          name of view
     */
    @RequestMapping(method = RequestMethod.GET)
    public String routersStatistic(Model model) {

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

    /**
     * Provides statistic for the concrete router.
     *
     * @param model     model to set attributes
     * @param SN        SN of router to get statistic about
     * @return          name of view
     */
    @RequestMapping(value="/{SN}", method=RequestMethod.GET)
    public String routerStatistic(
            @PathVariable("SN") String SN,
            Model model) {
        RouterEntity router = new RouterEntity();
        router.setSN(SN);
        model.addAttribute("SN", SN);
        model.addAttribute("name", routerRepo.getName(router));
        model.addAttribute("active", (routerRepo.isActive(router))? "active":"inactive");
        model.addAttribute("connectionsNum", routerRepo.getConnectionsCount(router));
        model.addAttribute("inactiveConnectionsNum", routerRepo.getInactiveConnectionsCount(router));
        model.addAttribute("connectedRoutersActive", routerRepo.getConnectedRouters(router, true));
        model.addAttribute("connectedRoutersInactive", routerRepo.getConnectedRouters(router, false));
        return "statistic/router";
    }
}
