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
 * @author Mike
 * @verion 0.1
 */
public class RouterEditServlet extends AbstractHttpServlet {

  private static String ROUTER_LIST_PAGE = "/jsp/routers/routerList.jsp";    
    private static String ROUTER_EDIT_PAGE = "/jsp/routers/routerEdit.jsp";
    private static String ROUTER_LIST_URL = "/routers";
    private static String ROUTER_EDIT_URL = "/router";

    public RouterEditServlet() {
        logger = LoggerFactory.getLogger("com.citycon.controllers.servlets");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) 
                                                        throws ServletException, IOException {
        if (req.getParameter("SN") != null) {
            try {
                String routerSN = req.getParameter("SN");
                try {
                    ORMRouter router = new ORMRouter();
                    router.setSN(routerSN);
                    router.read();

                    req.setAttribute("router", router.getEntity());
                } catch (DAOException cause) {
                    logger.warn("Error occur during reading connection", cause);
                    forwardToErrorPage("Error occur during reading connection", req, res);
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
        
        RequestDispatcher editView = req.getRequestDispatcher(ROUTER_EDIT_PAGE);
        editView.forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) 
                                                        throws ServletException, IOException {
        String type = req.getParameter("type");
        if (type == null) {
            forwardToErrorPage("type parameter is null", req, res);
            return;
        }
        switch (type) {
            case "edit" : {
                doPut(req, res);
                return;
            }
            case "delete" : {
                doDelete(req, res);
                return;
            }
            default : {
                // String SN1 = req.getParameter("SN1");
                // String SN2 = req.getParameter("SN2");
                // // Temporary hack
                // ORMRouter router1 = new ORMRouter();
                // ORMRouter router2 = new ORMRouter();
                // router1.setSN(SN1);
                // router2.setSN(SN2);
                // try {
                //     try {
                //         router1.read();
                //         router2.read();
                //     } catch (InvalidDataDAOException exception) {
                //         // No routers with such SN, redirect to add/edit page
                //         res.sendRedirect(ROUTER_EDIT_URL+"?errorType=invalidSN&SN1="+SN1+"&SN2="+SN2);
                //         return;
                //     } 
                //     ORMRouter newConnection = new ORMRouter();
                //     newConnection.setFirstRouterId(router1.getId());
                //     newConnection.setSecondRouterId(router2.getId());
                //     try {
                //         newConnection.create();
                //     } catch (DublicateKeyDAOException exception) {
                //         res.sendRedirect(ROUTER_EDIT_URL+"?errorType=noFreePorts&SN1="+SN1+"&SN2="+SN2);
                //         return;
                //     }
                // } catch (DAOException exception) {
                //     logger.warn("DAO error during adding new connection", exception);
                //     forwardToErrorPage("Internal DAO exception", req, res);
                // }
                // res.sendRedirect(ROUTER_LIST_URL+"?success=add");
            }
        }
    }
    protected void doPut(HttpServletRequest req, HttpServletResponse res) 
                                                        throws ServletException, IOException {
        // try {
        //     int connectionId = Integer.parseInt(req.getParameter("id"));
        //     String SN1 = req.getParameter("SN1");
        //     String SN2 = req.getParameter("SN2");
        //     // Temporary hack
        //     ORMRouter router1 = new ORMRouter();
        //     ORMRouter router2 = new ORMRouter();

        //     router1.setSN(SN1);
        //     router2.setSN(SN2);
        //     try {
        //         try {
        //             router1.read();
        //             router2.read();
        //         } catch (InvalidDataDAOException exception) {
        //             // No routers with such SN, redirect to add/edit page
        //             res.sendRedirect(ROUTER_EDIT_URL+"?action=edit?id="+connectionId+"&errorType=invalidSN&SN1="+SN1+"&SN2="+SN2);
        //             return;
        //         }

        //         ORMRouter updateConnection = new ORMRouter();
        //         updateConnection.setId(connectionId);
        //         updateConnection.setFirstRouterId(router1.getId());
        //         updateConnection.setSecondRouterId(router2.getId());
            
        //         try {
        //             updateConnection.update();
        //         } catch (DublicateKeyDAOException exception) {
        //             res.sendRedirect(ROUTER_EDIT_URL+"?action=edit?id="+connectionId+"?errorType=noFreePorts&SN1="+SN1+"&SN2="+SN2);
        //             return;
        //         }
        //     }catch (DAOException cause) {
        //         logger.warn("Internal DAO exception", cause);
        //         forwardToErrorPage("Internal DAO exception", req, res);
        //     }
        // } catch (NumberFormatException exception) {
        //     forwardToErrorPage("Not string id value", req, res);
        //     return;
        // }
        // res.sendRedirect(ROUTER_LIST_URL+"?success=add");
    }
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) 
                                                        throws ServletException, IOException {
        // try {
        //     int connectionId = Integer.parseInt(req.getParameter("id"));
        //     try {
        //         ORMRouter connection = new ORMRouter();
        //         connection.setId(connectionId);
        //         connection.delete();
        //     } catch (DAOException cause) {
        //         logger.warn("Error occur during deleting connection", cause);
        //         forwardToErrorPage("Error occur during deleting connection", req, res);
        //         return;
        //     }
        // } catch (NumberFormatException exception) {
        //     forwardToErrorPage("Not string id value", req, res);
        // }

        // res.sendRedirect(ROUTER_LIST_URL+"?success=delete");
        // return;
        }
}