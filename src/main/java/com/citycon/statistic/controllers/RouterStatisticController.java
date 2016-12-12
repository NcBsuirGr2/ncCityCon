package com.citycon.statistic.controllers;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.model.systemunits.orm.ORMRouter;
import com.citycon.model.systemunits.orm.ORMRouterConnection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/routers")
public class RouterStatisticController {
    @RequestMapping(method = RequestMethod.GET)
    public String RoutersStatistic(Model model) throws DAOException {

        model.addAttribute("count_routers", ORMRouter.getCount());
        model.addAttribute("count_active_routers", ORMRouter.countActiveRouters());
        model.addAttribute("count_connections", ORMRouterConnection.getCount());
        model.addAttribute("count_ports", ORMRouterConnection.countPorts());

        return "routers";
    }
}
