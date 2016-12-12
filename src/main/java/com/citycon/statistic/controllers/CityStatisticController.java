package com.citycon.statistic.controllers;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.model.systemunits.orm.ORMCity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/cities")
public class CityStatisticController {

    @RequestMapping(method = RequestMethod.GET)
    public String CitiyStatistic(Model model) throws DAOException {

        model.addAttribute("count_countries", ORMCity.getCountCountries());
        model.addAttribute("count_cities", ORMCity.getCount());
        model.addAttribute("max_city_country", ORMCity.maxCityCountry());
        model.addAttribute("min_city_country", ORMCity.minCityCountry());
        model.addAttribute("max_routers_city", ORMCity.maxRouterCity());

        return "cities";
    }
}
