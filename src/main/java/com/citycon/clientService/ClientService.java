package com.citycon.clientService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Web service client to get all OrderExpress users.
 *
 * @author Alex
 * @version 2.0
 */
public class ClientService {
    public static UserModel[] getServiceUsers(){
        UserModel[] users = null;
        Logger logger = LoggerFactory.getLogger("com.citycon.clientService.ClientService");

        try {
            GetAllUsersServiceLocator locator = new GetAllUsersServiceLocator();
            GetAllUsers service = locator.getGetAllUsersPort();

            users = service.getUsers();

        } catch (javax.xml.rpc.ServiceException ex) {
            logger.warn("Service Exception", ex);
        } catch (java.rmi.RemoteException ex) {
            logger.warn("Remote Exception", ex);
        }

        return users;
    }
}
