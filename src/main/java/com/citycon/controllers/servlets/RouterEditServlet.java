package com.citycon.controllers.servlets;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.dao.exceptions.DublicateKeyDAOException;
import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.model.systemunits.entities.RouterEntity;
import com.citycon.model.systemunits.orm.ORMRouter;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.HashMap;

/**
 * Used to perform CRUD operations with routers. On GET returns page for
 * editing or adding new router. POST must contain 'type' parameter with values
 * 'add', 'delete' or 'edit'. Redirects to the main routes page on success 
 * POST.
 * 
 * @author Mike
 * @verion 0.3
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
                RouterEntity router = new RouterEntity();
                router.setSN(routerSN);
                ORMRouter editRouter = new ORMRouter();
                editRouter.setEntity(router);                
                try {
                    editRouter.read();
                    req.setAttribute("editRouter", editRouter.getEntity());
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
                        RouterEntity router = new RouterEntity();
                        router.setPortsNum(Integer.parseInt(portsNum));
                        router.setSN(SN);
                        router.setName(name);
                        router.setActive(active.equals("true"));
                        router.getCity().setName(cityName);
                        router.getCity().setCountryName(countryName);

                        newRouter.setEntity(router);
                    } catch (Exception exception) {
                        forwardToErrorPage("Invalid router parameters", req, res);
                        logger.warn("Exception ",exception);
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
                res.sendRedirect(ROUTER_LIST_URL+"?success=add&country="+req.getParameter("country") 
                                                                + "&city="+req.getParameter("city"));
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
                    RouterEntity router = new RouterEntity();
                    router.setId(Integer.parseInt(routerId));
                    router.setName(name);
                    router.setActive(active.equals("true"));
                    router.getCity().setName(cityName);
                    router.getCity().setCountryName(countryName);

                    updateRouter.setEntity(router);
                } catch (Exception exception) {
                    forwardToErrorPage("Invalid router parameters", req, res);
                    logger.warn("Exception ",exception);
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
        res.sendRedirect(ROUTER_LIST_URL+"?success=edit&country="+req.getParameter("country") 
                                                                + "&city="+req.getParameter("city"));
    }
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) 
                                                        throws ServletException, IOException {
        try {
            int routerId = Integer.parseInt(req.getParameter("id"));
            try {
                ORMRouter router = new ORMRouter();
                router.getEntity().setId(routerId);
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

        res.sendRedirect(ROUTER_LIST_URL+"?success=delete&country="+req.getParameter("country") 
                                                                + "&city="+req.getParameter("city"));
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