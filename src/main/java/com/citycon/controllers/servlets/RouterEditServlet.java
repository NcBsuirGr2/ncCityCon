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

import java.util.HashMap;

/**
 * @author Mike
 * @verion 0.2
 */
public class RouterEditServlet extends AbstractHttpServlet {

    private static String ROUTER_LIST_PAGE = "/jsp/routers/routerList.jsp";    
    private static String ROUTER_EDIT_PAGE = "/jsp/routers/routerEdit.jsp";
    private static String ROUTER_ADD_PAGE = "/jsp/routers/routerAdd.jsp";
    private static String ROUTER_LIST_URL = "/routers";
    private static String ROUTER_EDIT_URL = "/router";

    public RouterEditServlet() {
        logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.RouterEditServlet");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) 
                                                        throws ServletException, IOException {
        if (req.getParameter("SN") != null && req.getParameter("action") != null 
                                            && req.getParameter("action").equals("edit")) {
            try { 
                String routerSN = req.getParameter("SN");
                try {
                    ORMRouter router = new ORMRouter();
                    router.setSN(routerSN);
                    router.read();

                    req.setAttribute("editRouter", router.getEntity());
                    req.getRequestDispatcher(ROUTER_EDIT_PAGE).forward(req, res);
                    return;
                } catch (DAOException cause) {
                    logger.warn("Error occur during reading router", cause);
                    forwardToErrorPage("Error occur during reading router", req, res);
                    return;
                }
            } catch (NumberFormatException exception) {
                forwardToErrorPage("Not string id value", req, res);
                return;
            } catch (Exception exception) {
                logger.warn("Unexcepted exception");
                forwardToErrorPage("Internal servler error", req, res);
                return;
            }
        }
        
        req.getRequestDispatcher(ROUTER_EDIT_PAGE).forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) 
                                                        throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            forwardToErrorPage("action parameter is null", req, res);
            return;
        }
        switch (action) {
            case "edit" : {
                doPut(req, res);
                return;
            }
            case "delete" : {
                doDelete(req, res);
                return;
            }
            default : { 
                try {
                    String portsNum = req.getParameter("portsNum");
                    String SN = req.getParameter("SN");
                    String name = req.getParameter("name");
                    String active = req.getParameter("active");
                    String cityName = req.getParameter("cityName");
                    String countryName = req.getParameter("countryName");

                    ORMRouter newRouter = new ORMRouter();
                    try {
                        newRouter.setPortsNum(Integer.parseInt(portsNum));
                        newRouter.setSN(SN);
                        newRouter.setName(name);
                        newRouter.setActive(active.equals("true"));
                        newRouter.setCityName(cityName);
                        newRouter.setCountryName(countryName);
                    } catch (Exception exception) {
                        forwardToErrorPage("Invalid router parameters", req, res);
                        logger.trace("Exception ",exception);
                        return;
                    }
                    try {
                        logger.trace("New router:"+newRouter.getEntity().toString());
                        newRouter.create();
                    } catch (DublicateKeyDAOException exception) {
                        res.sendRedirect(getRedirectPathToSamePage("dublicate", req, res).toString());
                        return;
                    } catch (InvalidDataDAOException exception) {
                        res.sendRedirect(getRedirectPathToSamePage("invalidData", req, res).toString());
                        return;
                    }
                } catch (DAOException exception) {
                    logger.warn("DAO error during adding new router", exception);
                    forwardToErrorPage("Internal DAO exception", req, res);
                    return;
                } catch (Exception exception) {
                    logger.warn("Unexcepted exception");
                    forwardToErrorPage("Internal servler error", req, res);
                    return;
                }
                res.sendRedirect(ROUTER_LIST_URL+"?success=add");
            }
        }
    }
    protected void doPut(HttpServletRequest req, HttpServletResponse res) 
                                                        throws ServletException, IOException {
        try {
            String routerId = req.getParameter("id");
            String name = req.getParameter("name");
            String active = req.getParameter("active");
            String cityName = req.getParameter("cityName");
            String countryName = req.getParameter("countryName");

            ORMRouter updateRouter = new ORMRouter();
            try {
                updateRouter.setId(Integer.parseInt(routerId));
                updateRouter.setName(name);
                updateRouter.setActive(active.equals("true"));
                updateRouter.setCityName(cityName);
                updateRouter.setCountryName(countryName);
            } catch (Exception exception) {
                forwardToErrorPage("Invalid router parameters", req, res);
                logger.trace("Exception ",exception);
                return;
            }
            
                try {
                    updateRouter.update();
                } catch (DublicateKeyDAOException exception) {
                    res.sendRedirect(getRedirectPathToSamePage("dublicate", req, res).toString());
                    return;
                } catch (InvalidDataDAOException exception) {
                    res.sendRedirect(getRedirectPathToSamePage("invalidData", req, res).toString());
                    return;
                }
            }catch (DAOException cause) {
                logger.warn("Internal DAO exception", cause);
                forwardToErrorPage("Internal DAO exception", req, res);
            } catch (Exception exception) {
                logger.warn("Unexcepted exception");
                forwardToErrorPage("Internal servler error", req, res);
                return;
            }
        res.sendRedirect(ROUTER_LIST_URL+"?success=edit");
    }
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) 
                                                        throws ServletException, IOException {
        try {
            int routerId = Integer.parseInt(req.getParameter("id"));
            try {
                ORMRouter router = new ORMRouter();
                router.setId(routerId);
                router.delete();
            } catch (DAOException cause) {
                logger.warn("Error occur during deleting router", cause);
                forwardToErrorPage("Error occur during deleting router", req, res);
                return;
            }
        } catch (NumberFormatException exception) {
            forwardToErrorPage("Not string id value", req, res);
            return;
        } catch (Exception exception) {
            logger.warn("Unexcepted exception");
            forwardToErrorPage("Internal servler error", req, res);
            return;
        }

        res.sendRedirect(ROUTER_LIST_URL+"?success=delete");
        return;
        }

    private StringBuilder getRedirectPathToSamePage(String errorType, HttpServletRequest req, HttpServletResponse res) {
        String action = req.getParameter("action");
        String portsNum = req.getParameter("portsNum");
        String SN = req.getParameter("SN");
        String name = req.getParameter("name");
        String active = req.getParameter("active");
        String cityName = req.getParameter("cityName");
        String countryName = req.getParameter("countryName");
        StringBuilder redirect = new StringBuilder();
        redirect.append(ROUTER_EDIT_URL);
        redirect.append("?action=");
        redirect.append(action);
        redirect.append("&errorType=");
        redirect.append(errorType);
        redirect.append("&portsNum=");
        redirect.append(portsNum);
        redirect.append("&SN=");
        redirect.append(SN);
        redirect.append("&name=");
        redirect.append(name);
        redirect.append("&active=");
        redirect.append(active);
        redirect.append("&city=");
        redirect.append(cityName);
        redirect.append("&country=");
        redirect.append(countryName);
        return redirect;
    }
}