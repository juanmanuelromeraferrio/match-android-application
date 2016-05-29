package com.match.service.factory;

import com.match.infrastructure.Database;
import com.match.service.api.CandidatesService;
import com.match.service.api.InterestService;
import com.match.service.api.MatchService;
import com.match.service.api.ServiceType;
import com.match.service.api.Services;
import com.match.service.api.UserService;
import com.match.service.impl.CandidatesServiceImpl;
import com.match.service.impl.CandidatesServiceMock;
import com.match.service.impl.InterestServiceImpl;
import com.match.service.impl.InterestServiceMock;
import com.match.service.impl.UserServiceImpl;
import com.match.service.impl.UserServiceMock;
import com.match.utils.mapper.CandidateMapper;
import com.match.utils.mapper.MatchCandidateMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Juan Manuel Romera on 17/5/2016.
 */
public class ServiceFactory {

    private static Map<ServiceType, MatchService> services = new HashMap<>();
    private static CandidateMapper candidateMapper;

    private ServiceFactory() {
    }

    public static void init(Services type, Database database) {
        candidateMapper = new MatchCandidateMapper();
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
        InterestService interestService = new InterestServiceImpl(database);
        CandidatesService candidatesService = new CandidatesServiceImpl(database, candidateMapper);
        save(userService);
        save(interestService);
        save(candidatesService);
    }

    private static void buildMockServices(Database database) {
        UserService userService = new UserServiceMock(database);
        InterestService interestService = new InterestServiceMock(database);
        CandidatesService candidatesService = new CandidatesServiceMock(database, candidateMapper);

        save(userService);
        save(interestService);
        save(candidatesService);
    }

    private static void save(MatchService service) {
        services.put(service.getType(), service);
    }

    public static UserService getUserService() {
        return (UserService) services.get(ServiceType.USER);
    }

    public static InterestService getInterestService() {
        return (InterestService) services.get(ServiceType.INTEREST);
    }

    public static CandidatesService getCandidatesService() {
        return (CandidatesService) services.get(ServiceType.CANDIDATES);
    }
}
