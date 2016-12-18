package com.citycon.clientService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Vojts on 18.12.2016.
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
