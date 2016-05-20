package com.match.service.factory;

import com.match.infrastructure.Database;
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

    private static Map<ServiceType, MatchService> services = new HashMap<>();

    private ServiceFactory() {
    }

    public static void init(Services type, Database database) {
        buildServices(type, database);
    }

    private static void buildServices(Services type, Database database) {
        if (type.equals(Services.REAL)) {
            buildRealServices(database);
        } else {
            buildMockServices(database);
        }
    }

    private static void buildRealServices(Database database) {
        UserService userService = new UserServiceImpl(database);
        services.put(userService.getType(), userService);
    }

    private static void buildMockServices(Database database) {
        UserService userService = new UserServiceMock(database);
        services.put(userService.getType(), userService);
    }

    public static UserService getUserService() {
        return (UserService) services.get(ServiceType.USER);
    }
}
