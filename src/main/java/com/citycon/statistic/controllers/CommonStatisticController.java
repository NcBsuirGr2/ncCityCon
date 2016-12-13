package com.citycon.statistic.controllers;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.model.systemunits.orm.ORMCity;
import com.citycon.model.systemunits.orm.ORMRouter;
import com.citycon.model.systemunits.orm.ORMRouterConnection;
import com.citycon.model.systemunits.orm.ORMUser;
import com.citycon.statistic.repositories.CityStatisticRepository;
import com.citycon.statistic.repositories.ConnectionStatisticRepository;
import com.citycon.statistic.repositories.RouterStatisticRepository;
import com.citycon.statistic.repositories.UserStatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/")
public class CommonStatisticController {
    private UserStatisticRepository userRepo;
    private CityStatisticRepository cityRepo;
    private RouterStatisticRepository routerRepo;
    private ConnectionStatisticRepository connectionRepo;

    @Autowired
    public void setUserRepo(UserStatisticRepository repo) {
        userRepo = repo;
    }
    @Autowired
    public void setCityRepo(CityStatisticRepository repo) {
        cityRepo = repo;
    }
    @Autowired
    public void setRouterRepo(RouterStatisticRepository repo) {
        routerRepo = repo;
    }
    @Autowired
    public void setConnectionRepo(ConnectionStatisticRepository repo) {
        connectionRepo = repo;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showCommonStatistics(Model model) {
            model.addAttribute("count_users", userRepo.getCount());
            model.addAttribute("count_cities", cityRepo.getCount());
            model.addAttribute("count_routers", routerRepo.getCount());
            model.addAttribute("count_connections", connectionRepo.getCount());
        return "statistic/common";
    }


}
