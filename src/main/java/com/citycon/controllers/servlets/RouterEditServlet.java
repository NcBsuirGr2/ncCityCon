package com.citycon.controllers.servlets;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.dao.exceptions.DublicateKeyDAOException;
import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.model.systemunits.entities.RouterEntity;
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

    private String LIST_ROUTERS_PAGE = "/jsp/routers/routerEdit.jsp";
    private String LIST_ROUTERS_URL = "/routers";

    private String ROUTER_IS_EXIST = "Router with that SN is already exist";
    private String SERVER_ERROR = "Server error";
    private String INVALID_DATA = "Invalid Data";

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        switch (req.getParameter("action")) {
            case "add" : {
                break;
            }
            case "edit" : {
                try {
                    String sn = req.getParameter("SN");
                    editRouter(sn, req, res);
                } catch (Exception e) {
                    forwardToErrorPage("", req, res);
                }
                break;
            }
            case "delete" : {
                break;
            }
        }

        RequestDispatcher editView = req.getRequestDispatcher(LIST_ROUTERS_PAGE);
        editView.forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            ORMRouter newRouter = new ORMRouter();
            newRouter.setName(req.getParameter("name"));
            newRouter.setSN(req.getParameter("SN"));
            newRouter.setPortsNum(Integer.getInteger(req.getParameter("portsNum")));
            newRouter.setIsActive(Boolean.parseBoolean(req.getParameter("isActive")));
            newRouter.setCityId(Integer.getInteger(req.getParameter("cityId")));

            newRouter.create();
            res.sendRedirect("/routers");
        } catch(DublicateKeyDAOException cause) {
            forwardToErrorPage(ROUTER_IS_EXIST, req, res);
        } catch(InternalDAOException cause) {
            forwardToErrorPage(SERVER_ERROR, req, res);
        } catch (InvalidDataDAOException cause) {
            forwardToErrorPage(INVALID_DATA, req, res);
        } catch (DAOException e) {
            forwardToErrorPage(e.getMessage(), req, res);
        }

    }

    protected void editRouter(String sn, HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try{
            ORMRouter router = new ORMRouter();
            router.setSN(sn);
            router.read();
            req.setAttribute("router", router.getEntity());
        } catch (InternalDAOException cause) {
            forwardToErrorPage(SERVER_ERROR, req, res);
        } catch (InvalidDataDAOException cause) {
            forwardToErrorPage(INVALID_DATA, req, res);
        } catch (DAOException e) {
            forwardToErrorPage(e.getMessage(), req, res);
            e.printStackTrace();
        }

    }


}