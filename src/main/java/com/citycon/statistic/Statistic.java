package com.citycon.statistic;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.model.systemunits.orm.ORMCity;
import com.citycon.model.systemunits.orm.ORMRouter;
import com.citycon.model.systemunits.orm.ORMRouterConnection;
import com.citycon.model.systemunits.orm.ORMUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Vojts on 08.12.2016.
 */
@Controller
public class Statistic {
    @RequestMapping("/statistic")
    public String CommonStatistic(Model model) throws DAOException {

        model.addAttribute("count_users", ORMUser.getCount());
        model.addAttribute("count_cities", ORMCity.getCount());
        model.addAttribute("count_routers", ORMRouter.getCount());
        model.addAttribute("count_connections", ORMRouterConnection.getCount());

        return "common";
    }

    @RequestMapping("/statistic/users")
    public String UsersStatistic(Model model) throws DAOException {

        model.addAttribute("count_users", ORMUser.getCount());
        model.addAttribute("count_admins", ORMUser.getCount("admin"));
        model.addAttribute("count_operators", ORMUser.getCount("operator"));
        model.addAttribute("count_guests", ORMUser.getCount("guest"));
        model.addAttribute("first_users", ORMUser.first_users());
        model.addAttribute("last_users", ORMUser.last_users());

        return "users";
    }

    @RequestMapping("/statistic/cities")
    public String CitiesStatistic(Model model) throws DAOException {

        model.addAttribute("count_countries", ORMCity.getCountCountries());
        model.addAttribute("count_cities", ORMCity.getCount());
        model.addAttribute("max_city_country", ORMCity.maxCityCountry());
        model.addAttribute("min_city_country", ORMCity.minCityCountry());
        model.addAttribute("max_routers_city", ORMCity.maxRouterCity());

        return "cities";
    }

    @RequestMapping("/statistic/routers")
    public String RoutersStatistic(Model model) throws DAOException{

        model.addAttribute("count_routers", ORMRouter.getCount());
        model.addAttribute("count_active_routers", ORMRouter.countActiveRouters());
        model.addAttribute("count_connections", ORMRouterConnection.getCount());
        model.addAttribute("count_ports", ORMRouterConnection.countPorts());

        return "routers";
    }

    @RequestMapping("/statistic/connections")
    public String ConnectionsStatistic(Model model) throws DAOException{

        model.addAttribute("count_connections", ORMRouterConnection.getCount());
        model.addAttribute("count_ports", ORMRouterConnection.countPorts());

        return "connections";
    }

}
