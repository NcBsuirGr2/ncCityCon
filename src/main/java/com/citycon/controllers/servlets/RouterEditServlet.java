package com.citycon.controllers.servlets;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.dao.exceptions.DublicateKeyDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.model.systemunits.entities.RouterEntity;
import com.citycon.model.systemunits.orm.ORMRouter;
import com.citycon.model.systemunits.entities.validationgroups.RouterAddGroup;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.groups.Default;


import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Used to perform CRUD operations with routers. On GET returns page for
 * editing or adding new router. Tries fill as much field as it can. POST must
 * contain 'type' parameter with values 'add', 'delete' or 'edit'. Redirects
 * to the main routes page on success POST.
 * 
 * @author Mike
 * @verion 1.4
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
        if (notEmpty(req.getParameter("SN"))) {
            try { 
                String routerSN = req.getParameter("SN");
                RouterEntity router = new RouterEntity();
                router.setSN(routerSN);
                ORMRouter editRouter = new ORMRouter();
                editRouter.setEntity(router);
                editRouter.read();
                req.setAttribute("editRouter", editRouter.getEntity());
            } catch (DAOException cause) {
                logger.warn("Error occur during reading router", cause);
                forwardToErrorPage("Error occur during reading router", req, res);
                return;
            } catch (Exception exception) {
                logger.warn("Unexpected exception", exception);
                forwardToErrorPage("Internal servler error", req, res);
                return;
            }
        }
        req.getRequestDispatcher(ROUTER_EDIT_PAGE).forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) 
                                                        throws ServletException, IOException {
        String action = req.getParameter("action");
        if (!notEmpty(action)) {
            addRouter(req, res);
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
                addRouter(req, res);
                return;
            }
        }
    }

    protected void addRouter(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String portsNum = req.getParameter("portsNum");
        String SN = req.getParameter("SN");
        String name = req.getParameter("name");
        String active = req.getParameter("active");
        String cityName = req.getParameter("cityName");
        String countryName = req.getParameter("countryName");

        RouterEntity router = new RouterEntity();
        try {
            router.setPortsNum(Integer.parseInt(portsNum));
        } catch (NumberFormatException e) {
            forwardToErrorPage("Invalid router ports num", req, res);
            return;
        }
        router.setSN(SN);
        router.setName(name);
        router.setActive(active.equals("true"));
        router.getCity().setName(cityName);
        router.getCity().setCountryName(countryName);

        /*Validation*/
        String validationMessage = validate(router, Default.class, RouterAddGroup.class);
        if (validationMessage != null) {
            forwardToErrorPage(validationMessage, req, res);
            return;
        }
        ORMRouter newRouter = new ORMRouter();
        newRouter.setEntity(router);
        try {
            newRouter.create();
            logger.trace("Creating router:"+newRouter.getEntity());
        } catch (DublicateKeyDAOException exception) {
            res.sendRedirect(getRedirectPathToSamePage("dublicate", req, res));
            return;
        } catch (InvalidDataDAOException exception) {
            res.sendRedirect(getRedirectPathToSamePage("invalidData", req, res));
            return;
        } catch (DAOException exception) {
            logger.warn("DAO error during adding new router", exception);
            forwardToErrorPage("Internal DAO exception", req, res);
            return;
        } catch (Exception exception) {
            logger.warn("Unexcepted exception", exception);
            forwardToErrorPage("Internal servler error", req, res);
            return;
        }
        res.sendRedirect(ROUTER_LIST_URL+"?success=add&"+req.getParameter("sameSelect"));
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse res) 
                                                        throws ServletException, IOException {
        String routerId = req.getParameter("id");
        String name = req.getParameter("name");
        String active = req.getParameter("active");
        String cityName = req.getParameter("cityName");
        String countryName = req.getParameter("countryName");

        RouterEntity router = new RouterEntity();
        try {
            router.setId(Integer.parseInt(routerId));
        } catch (NumberFormatException e) {
            forwardToErrorPage("Invalid router id", req, res);
            return;
        }
        router.setName(name);
        router.setActive(active.equals("true"));
        router.getCity().setName(cityName);
        router.getCity().setCountryName(countryName);

        /*Validation*/
        String validationMessage = validate(router);
        if (validationMessage != null) {
            forwardToErrorPage(validationMessage, req, res);
            return;
        }

        ORMRouter updateRouter = new ORMRouter();
        updateRouter.setEntity(router);
        try {
            updateRouter.update();
        } catch (DublicateKeyDAOException exception) {
            res.sendRedirect(getRedirectPathToSamePage("dublicate", req, res));
            return;
        } catch (InvalidDataDAOException exception) {
            res.sendRedirect(getRedirectPathToSamePage("invalidData", req, res));
            return;
        } catch (DAOException cause) {
            logger.warn("Internal DAO exception", cause);
            forwardToErrorPage("Internal DAO exception", req, res);
        } catch (Exception exception) {
            logger.warn("Unexpected exception", exception);
            forwardToErrorPage("Internal servler error", req, res);
            return;
        }
        res.sendRedirect(ROUTER_LIST_URL+"?success=edit&"+req.getParameter("sameSelect"));
    }
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) 
                                                        throws ServletException, IOException {
        try {
            int routerId = Integer.parseInt(req.getParameter("id"));

            ORMRouter router = new ORMRouter();
            router.getEntity().setId(routerId);
            router.delete();
        } catch (NumberFormatException exception) {
            forwardToErrorPage("Invalid router id", req, res);
            return;
        } catch (DAOException cause) {
            logger.warn("Error occur during deleting router", cause);
            forwardToErrorPage("Error occur during deleting router", req, res);
            return;
        } catch (Exception exception) {
            logger.warn("Unexpected exception", exception);
            forwardToErrorPage("Internal server error", req, res);
            return;
        }
        res.sendRedirect(ROUTER_LIST_URL+"?success=delete&"+req.getParameter("sameSelect"));
    }

    private String getRedirectPathToSamePage(String errorType, HttpServletRequest req, HttpServletResponse res) {
        StringBuilder redirect = new StringBuilder();
        redirect.append(ROUTER_EDIT_URL);
        redirect.append("?");
        redirect.append(req.getParameter("sameSelect"));
        redirect.append("&action=");
        redirect.append(req.getParameter("action"));
        redirect.append("&errorType=");
        redirect.append(errorType);
        redirect.append("&portsNum=");
        redirect.append(req.getParameter("portsNum"));
        redirect.append("&SN=");
        try {
            redirect.append(URLEncoder.encode(req.getParameter("SN"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.warn("The router SN has unexpected encoding", e);
            redirect.append(req.getParameter("SN"));
        }
        redirect.append("&name=");
        redirect.append(req.getParameter("name"));
        redirect.append("&active=");
        redirect.append(req.getParameter("active"));
        redirect.append("&city=");
        redirect.append(req.getParameter("cityName"));
        redirect.append("&country=");
        redirect.append(req.getParameter("countryName"));
        return redirect.toString();
    }
}