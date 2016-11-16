package com.citycon.controllers.servlets;

import com.citycon.dao.exceptions.DAOException;
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
public class RouterListServlet  extends AbstractHttpServlet {
    private static String RUTERS_LIST_PAGE = "/jsp/routers/routerList.jsp";

    private String ROUTER_IS_EXIST = "Router with that SN is already exist";
    private String SERVER_ERROR = "Server error";
    private String INVALID_DATA = "Invalid Data";


    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        RouterEntity routers[] = null;
        RequestDispatcher view = null;
        try {

            routers = ORMRouter.getPage(1,20,"Name",true);
            request.setAttribute("entityArray", routers);

            view = request.getRequestDispatcher(RUTERS_LIST_PAGE);
        } catch (InvalidDataDAOException e) {
            forwardToErrorPage(INVALID_DATA,request,response);
            e.printStackTrace();
        } catch (InternalDAOException e) {
            forwardToErrorPage(SERVER_ERROR,request,response);
        } catch (DAOException e) {
            forwardToErrorPage(e.getMessage(),request,response);
            e.printStackTrace();
        }

        view.forward(request, response);

    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}