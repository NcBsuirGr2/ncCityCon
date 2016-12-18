package com.citycon.statistic.controllers;

import com.citycon.statistic.repositories.CityStatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cities")
public class CityStatisticController {
    private CityStatisticRepository repo;

    @Autowired
    public CityStatisticController(CityStatisticRepository repo) {
        this.repo = repo;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String CityStatistic(Model model) {
            model.addAttribute("count_countries", repo.getCountriesCount());
            model.addAttribute("count_cities", repo.getCount());
            model.addAttribute("max_city_countries", selectMaxCitiesCountries(repo.getDescCountryCities()));
            model.addAttribute("min_city_countries", selectMinCitiesCountries(repo.getDescCountryCities()));
            model.addAttribute("max_router_cities", selectMaxRoutersCities(repo.getDescCountryRouters()));
        return "statistic/cities";
    }


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
