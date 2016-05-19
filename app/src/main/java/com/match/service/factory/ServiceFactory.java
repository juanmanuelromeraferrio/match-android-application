package com.match.service.factory;

import com.match.service.api.MatchService;
import com.match.service.api.ServiceType;
import com.match.service.api.Services;
import com.match.service.api.UserService;
import com.match.service.impl.UserServiceImpl;
import com.match.service.impl.UserServiceMock;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Juan Manuel Romera on 17/5/2016.
 */
public class ServiceFactory {

    private static ServiceFactory instance = null;
    private Map<ServiceType, MatchService> services = new HashMap<>();

    private ServiceFactory(Services type) {
        buildServices(type);
    }

    public static ServiceFactory getInstance() {
        if (instance == null) {
            throw new IllegalAccessError();
        }
        return instance;
    }

    public static ServiceFactory init(Services type) {
        if (instance == null) {
            instance = new ServiceFactory(type);
        }
        return instance;
    }

    public void buildServices(Services type) {
        if (type.equals(Services.REAL)) {
            buildRealServices();
        } else {
            buildMockServices();
        }
    }

    private void buildRealServices() {
        UserService userService = new UserServiceImpl();
        services.put(userService.getType(), userService);
    }

    private void buildMockServices() {
        UserService userService = new UserServiceMock();
        services.put(userService.getType(), userService);
    }

    public UserService getUserService() {
        return (UserService) services.get(ServiceType.USER);
    }
}
