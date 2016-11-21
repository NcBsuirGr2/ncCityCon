package com.citycon.controllers.servlets;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.dao.exceptions.DublicateKeyDAOException;
import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.model.systemunits.entities.RouterEntity;
import com.citycon.model.systemunits.orm.ORMRouter;
import com.citycon.model.systemunits.orm.ORMUser;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

/**
 * Created by root on 16.11.16.
 */
public class RouterEditServlet extends AbstractHttpServlet {

    private String ERROR_PAGE = "/jsp/error.jsp";
    private String LIST_ROUTERS_PAGE = "/jsp/routers/routerEdit.jsp";
    private String LIST_ROUTER_URL = "/routers";

    public RouterEditServlet(){
        super();
        logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.RouterEditServlet");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String sn = req.getParameter("SN");
        if (sn != null) {
            try {
                ORMRouter router = new ORMRouter();
                router.setSN(sn);
                router.read();
                req.setAttribute("router", router.getEntity());
            } catch (DAOException cause) {
                logger.warn("Error occur during reading Router", cause);
                forwardToErrorPage("Error occur during reading Router", req, res);
            }
        }
        res.setHeader("Allow", "GET, POST, PUT, DELETE");
        RequestDispatcher editView = req.getRequestDispatcher(LIST_ROUTERS_PAGE);
        editView.forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        switch (req.getParameter("type")) {
            case "edit" : {
                doPut(req, res);
                return;
            }
            case "delete" : {
                doDelete(req, res);
                return;
            }
            case "add" : {
                String sn = req.getParameter("SN");
                String name = req.getParameter("name");
                String portsNumber = req.getParameter("portsNum");
                String cityId = req.getParameter("cityId");

                logger.info("New router {}", sn);
                if (sn == null || name == null || portsNumber == null || cityId == null) {
                    logger.info("Something is null {},{},{},{}", name, sn, portsNumber, cityId);
                    forwardToErrorPage("Not enough info to create new roter", req, res);
                    return;
                }
                try {
                    ORMRouter router = new ORMRouter();
                    router.setSN(sn);
                    router.setCityId(Integer.parseInt(cityId));
                    router.setName(name);
                    router.setPortsNum(Integer.parseInt(portsNumber));
                    router.IsActive(true); //TODO is active
                    router.create();
                    res.sendRedirect(LIST_ROUTER_URL);
                } catch (DAOException e) {
                    logger.warn("Cannot create new router", e);
                    forwardToErrorPage("Cannot create new router", req, res);
                }
                break;
            } default : {
                forwardToErrorPage("This adress is not exist", req, res);
                break;
            }

        }

    }

    protected void doPut(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String sn = req.getParameter("SN");
        String name = req.getParameter("name");
        String cityId = req.getParameter("cityId");
        String portsNum = req.getParameter("portsNum");
        String isActive = String.valueOf(true);


        if (sn == null) {
            forwardToErrorPage("Cannot update router cause SN field is empty", req, res);
            logger.warn("Cannot update router cause SN field is empty");
            return;
        }

        try {
            ORMRouter router = new ORMRouter();
            router.setSN(sn);
            router.setCityId(Integer.parseInt(cityId));
            router.setName(name);
            router.setPortsNum(Integer.parseInt(portsNum));
            router.IsActive(Boolean.getBoolean(isActive)); //TODO is active

            logger.debug("Updating router with SN:{} name:{} PortsNum:{} IsActive:{} CityId:{}",
                    router.getSN(),router.getName(), router.getPortsNum(), router.isActive(), router.getCityId());
            router.update();
        } catch (DAOException cause) {
            logger.warn("Cannot update router", cause);
            forwardToErrorPage("Cannot update router", req, res);
            return;
        } catch (NumberFormatException cause) {
            forwardToErrorPage("Invalid SN", req, res);
            return;
        }

        res.sendRedirect(LIST_ROUTER_URL);
    }


    protected void doDelete(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String sn = req.getParameter("SN");
        if (sn == null) {
            forwardToErrorPage("SN is not selected", req, res);
            logger.warn("Attempt to delete router without SN");
        }
        try {
            ORMRouter router = new ORMRouter();
            router.setSN(sn);
            router.delete();
        } catch (DAOException cause) {
            logger.warn("Cannot delete router", cause);
            forwardToErrorPage("Cannot delete router", req, res);
            return;
        } catch (NumberFormatException cause) {
            logger.warn("Invalid sn", cause);
            forwardToErrorPage("Invalid sn", req, res);
            return;
        }
        res.sendRedirect(LIST_ROUTER_URL);
    }
}