package com.citycon.statistic.controllers;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.model.systemunits.orm.ORMCity;
import com.citycon.statistic.repositories.CityStatisticRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

@Controller
@RequestMapping("/cities")
public class CityStatisticController {
    private CityStatisticRepository repo;

    @Autowired
    public CityStatisticController(CityStatisticRepository repo) {
        this.repo = repo;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String CityStatistic(Model model) throws DAOException {

        model.addAttribute("count_countries", repo.getCountriesCount());
        model.addAttribute("count_cities", repo.getCount());
        model.addAttribute("max_city_countries", selectMaxCitiesCountries(repo.getDescCountryCities()));
        model.addAttribute("min_city_countries", selectMinCitiesCountries(repo.getDescCountryCities()));
        model.addAttribute("max_router_cities", selectMaxRoutersCities(repo.getDescCountryRouters()));

        return "cities";
    }


    private List<Map<String, Object>> selectMaxCitiesCountries(List<Map<String, Object>> descCountryList) {
        long requiredCitiesCount = (Long)descCountryList.get(0).get("citiesCount");

        List<Map<String, Object>> maxCityCountries = new LinkedList<>();
        Logger logger = LoggerFactory.getLogger("com.citycon.statistic.controllers.CityStatisticController");
        for (Map<String, Object> countryMap : descCountryList) {
            if ((Long)countryMap.get("citiesCount") < requiredCitiesCount) {
                break;
            }

            logger.debug("have {}, need {}", (Long)countryMap.get("citiesCount"), requiredCitiesCount);
            maxCityCountries.add(countryMap);
        }
        logger.debug("list size: {}", maxCityCountries.size());
        return maxCityCountries;
    }

    private List<Map<String, Object>> selectMinCitiesCountries(List<Map<String, Object>> descCountryList) {
        Long requiredCitiesCount = (Long)descCountryList.get(descCountryList.size()-1).get("citiesCount");

        List<Map<String, Object>> minCityCountries = new LinkedList<>();
        Logger logger = LoggerFactory.getLogger("com.citycon.statistic.controllers.CityStatisticController");
        for (int i = descCountryList.size()-1; i > 0; i--) {
            Map<String, Object> element = descCountryList.get(i);

            logger.debug("---min cities logs----");
            logger.debug("have {}, need {}", (Long)element.get("citiesCount"), requiredCitiesCount);
            if ((Long)element.get("citiesCount") > requiredCitiesCount) {
                break;
            }
            minCityCountries.add(element);
        }
        logger.debug("list size: {}", minCityCountries.size());
        return minCityCountries;
    }

    private List<Map<String, Object>> selectMaxRoutersCities(List<Map<String, Object>> descCityList) {
        long requiredRoutersCount = (Long)descCityList.get(0).get("routersCount");

        List<Map<String, Object>> maxRouterCities = new LinkedList<>();
        Logger logger = LoggerFactory.getLogger("com.citycon.statistic.controllers.CityStatisticController");
        for (Map<String, Object> cityMap : descCityList) {

            logger.debug("---max routers logs----");
            logger.debug("have {}, need {}", (Long)cityMap.get("routersCount"), requiredRoutersCount);
            logger.debug("Country {}, city {}", cityMap.get("country"), cityMap.get("city"));

            if ((Long)cityMap.get("routersCount") < requiredRoutersCount) {
                break;
            }
            maxRouterCities.add(cityMap);
        }
        logger.debug("list size: {}", maxRouterCities.size());
        return maxRouterCities;
    }
}
