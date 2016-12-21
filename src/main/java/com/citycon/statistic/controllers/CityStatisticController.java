package com.citycon.statistic.controllers;

import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.statistic.repositories.CityStatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Provides statistic for all cities and for concrete city.
 *
 * @author Mike
 * @version 1.2
 */
@Controller
@RequestMapping("/cities")
public class CityStatisticController {
    private CityStatisticRepository repo;

    @Autowired
    public CityStatisticController(CityStatisticRepository repo) {
        this.repo = repo;
    }

    /**
     * Provides common cities statistics.
     *
     * @param model     model to set attributes
     * @return          view name
     */
    @RequestMapping(method = RequestMethod.GET)
    public String CitiesStatistic(Model model) {
            model.addAttribute("count_countries", repo.getCountriesCount());
            model.addAttribute("count_cities", repo.getCount());
            model.addAttribute("max_city_countries", selectMaxCitiesCountries(repo.getDescCountryCities()));
            model.addAttribute("min_city_countries", selectMinCitiesCountries(repo.getDescCountryCities()));
            model.addAttribute("max_router_cities", selectMaxRoutersCities(repo.getDescCountryRouters()));
        return "statistic/cities";
    }

    /**
     * Provides statistics from concrete city.
     *
     * @param country   country name
     * @param city      city name
     * @param model     model to set attributes
     * @return          view name
     */
    @RequestMapping(value="/{country}/{city}", method = RequestMethod.GET)
    public String CityStatistic(
            @PathVariable("country") String country,
            @PathVariable("city") String city,
            Model model) {

        model.addAttribute("country", country);
        model.addAttribute("city", city);
        CityEntity cityEntity = new CityEntity();
        cityEntity.setName(city);
        cityEntity.setCountryName(country);
        model.addAttribute("routersNum", repo.countRouters(cityEntity));
        model.addAttribute("connectionsNum", repo.countConnections(cityEntity));
        model.addAttribute("connectedActiveCities", repo.getConnectedCities(cityEntity, true));
        model.addAttribute("connectedInactiveCities", repo.getConnectedCities(cityEntity, false));
        return "statistic/city";
    }

    /**
     * Selects sublist of countries with max cities num from asc sorted list.
     *
     * @param descCountryList        list to find from
     * @return  minCityCountries     list of countries
     */
    private List<Map<String, Object>> selectMaxCitiesCountries(List<Map<String, Object>> descCountryList) {
        long requiredCitiesCount = (Long)descCountryList.get(0).get("citiesCount");
        List<Map<String, Object>> maxCityCountries = new LinkedList<>();

        for (Map<String, Object> countryMap : descCountryList) {
            if ((Long)countryMap.get("citiesCount") < requiredCitiesCount) {
                break;
            }
            maxCityCountries.add(countryMap);
        }

        return maxCityCountries;
    }

    /**
     * Selects sublist of countries with min cities num from desc sorted list.
     *
     * @param descCountryList        list to find from
     * @return  minCityCountries     list of countries
     */
    private List<Map<String, Object>> selectMinCitiesCountries(List<Map<String, Object>> descCountryList) {
        Long requiredCitiesCount = (Long)descCountryList.get(descCountryList.size()-1).get("citiesCount");
        List<Map<String, Object>> minCityCountries = new LinkedList<>();

        for (int i = descCountryList.size()-1; i > 0; i--) {
            Map<String, Object> element = descCountryList.get(i);
            if ((Long)element.get("citiesCount") > requiredCitiesCount) {
                break;
            }
            minCityCountries.add(element);
        }

        return minCityCountries;
    }

    /**
     * Selects sublist of cities with max routers num.
     *
     * @param descCityList          list to find from
     * @return  maxRouterCities     list of cities
     */
    private List<Map<String, Object>> selectMaxRoutersCities(List<Map<String, Object>> descCityList) {
        long requiredRoutersCount = (Long)descCityList.get(0).get("routersCount");

        List<Map<String, Object>> maxRouterCities = new LinkedList<>();
        for (Map<String, Object> cityMap : descCityList) {
            if ((Long)cityMap.get("routersCount") < requiredRoutersCount) {
                break;
            }
            maxRouterCities.add(cityMap);
        }

        return maxRouterCities;
    }
}
