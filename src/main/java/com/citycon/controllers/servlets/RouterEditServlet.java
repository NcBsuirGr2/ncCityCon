package com.citycon.controllers.servlets;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.dao.exceptions.DublicateKeyDAOException;
import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.model.systemunits.orm.ORMRouter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by root on 16.11.16.
 */
public class RouterEditServlet extends AbstractHttpServlet {

    private String LIST_ROUTERS_PAGE = "/jsp/routers/RouterList.jsp";
    private String LIST_ROUTERS_URL = "/cityCon/app/routers";

    private String ROUTER_IS_EXIST = "Router with that SN is already exist";
    private String SERVER_ERROR = "Server error";
    private String INVALID_DATA = "Invalid Data";

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String serialNumber = req.getParameter("SN");
        if (serialNumber != null) {
            try {
                ORMRouter router = new ORMRouter();
                router.setSN(serialNumber);
                router.read();
                req.setAttribute("router", router.getEntity());
            } catch (InternalDAOException cause) {
                forwardToErrorPage(SERVER_ERROR, req, res);
                return;
            } catch (InvalidDataDAOException cause) {
                forwardToErrorPage(INVALID_DATA, req, res);
            } catch (DAOException e) {
                forwardToErrorPage(e.getMessage(), req, res);
                e.printStackTrace();
            }
        }
        RequestDispatcher editView = req.getRequestDispatcher(LIST_ROUTERS_PAGE);
        editView.forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String SN = req.getParameter("SN");
        Integer portsNum = Integer.getInteger(req.getParameter("portsNum"));
        boolean isActive = Boolean.getBoolean(req.getParameter("isActive"));
        Integer cityId = Integer.getInteger(req.getParameter("cityId"));

        if (name == null || SN == null || portsNum == null) {
            forwardToErrorPage("Not enough info to create new router", req, res);
            return;
        }

        try {
            ORMRouter newRouter = new ORMRouter();
            newRouter.setName(name);
            newRouter.setSN(SN);
            newRouter.setPortsNum(portsNum);
            newRouter.setIsActive(isActive);
            newRouter.setCityId(cityId);

            newRouter.create();
        } catch(DublicateKeyDAOException cause) {
            forwardToErrorPage(ROUTER_IS_EXIST, req, res);
        } catch(InternalDAOException cause) {
            forwardToErrorPage(SERVER_ERROR, req, res);
        } catch (InvalidDataDAOException cause) {
            forwardToErrorPage(INVALID_DATA, req, res);
        } catch (DAOException e) {
            forwardToErrorPage(e.getMessage(), req, res);
        }

        res.sendRedirect(LIST_ROUTERS_URL);
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String SN = req.getParameter("SN");
        Integer portsNum = Integer.getInteger(req.getParameter("portsNum"));
        boolean isActive = Boolean.getBoolean(req.getParameter("isActive"));
        Integer cityId = Integer.getInteger(req.getParameter("cityId"));

        if (SN == null) {
            forwardToErrorPage("Cannot update router, serial number field is empty", req, res);
            return;
        }

        try {
            ORMRouter newRouter = new ORMRouter();
            newRouter.setName(name);
            newRouter.setSN(SN);
            newRouter.setPortsNum(portsNum);
            newRouter.setIsActive(isActive);
            newRouter.setCityId(cityId);

            newRouter.update();
        } catch (DublicateKeyDAOException cause) {
            forwardToErrorPage(ROUTER_IS_EXIST, req, res);
        } catch (InvalidDataDAOException cause) {
            forwardToErrorPage(INVALID_DATA, req, res);
        } catch (InternalDAOException cause) {
            forwardToErrorPage(SERVER_ERROR, req, res);
        } catch (DAOException e) {
            e.printStackTrace();
        }

        res.sendRedirect(LIST_ROUTERS_URL);
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        String SN = req.getParameter("SN");
        if (id == null && SN == null) {
            forwardToErrorPage("Cannot delete router cause the id & SN fields are empty", req, res);
        }
        try {
            ORMRouter Router = new ORMRouter();
            Router.setId(Integer.parseInt(id));
            Router.setSN(SN);

            Router.delete();
        } catch (InvalidDataDAOException cause) {
            forwardToErrorPage(INVALID_DATA, req, res);
        } catch (InternalDAOException cause) {
            forwardToErrorPage(SERVER_ERROR, req, res);
        } catch (NumberFormatException cause) {
            forwardToErrorPage("Infalid id string", req, res);
        } catch (DAOException e) {
            e.printStackTrace();
            forwardToErrorPage(e.getMessage(), req, res);
        }
        res.sendRedirect(LIST_ROUTERS_URL);
    }

}