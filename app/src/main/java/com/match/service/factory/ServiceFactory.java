package com.match.service.factory;

import com.match.infrastructure.Database;
import com.match.service.api.CandidatesService;
import com.match.service.api.ChatService;
import com.match.service.api.InterestService;
import com.match.service.api.MatchService;
import com.match.service.api.ServiceType;
import com.match.service.api.Services;
import com.match.service.api.ClientService;
import com.match.service.api.UserMatchesService;
import com.match.service.api.UserService;
import com.match.service.impl.CandidatesServiceImpl;
import com.match.service.impl.CandidatesServiceMock;
import com.match.service.impl.ChatServiceImpl;
import com.match.service.impl.ChatServiceMock;
import com.match.service.impl.InterestServiceImpl;
import com.match.service.impl.InterestServiceMock;
import com.match.service.impl.ClientServiceImpl;
import com.match.service.impl.UserMatchesServiceImpl;
import com.match.service.impl.UserMatchesServiceMock;
import com.match.service.impl.UserServiceImpl;
import com.match.service.impl.UserServiceMock;
import com.match.utils.mapper.CandidateMapper;
import com.match.utils.mapper.ChatMapper;
import com.match.utils.mapper.MatchCandidateMapper;
import com.match.utils.mapper.MatchChatMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Juan Manuel Romera on 17/5/2016.
 */
public class ServiceFactory {

    private static Map<ServiceType, MatchService> services = new HashMap<>();
    private static CandidateMapper candidateMapper;
    private static ChatMapper chatMapper;


    private ServiceFactory() {
    }

    public static void init(Services type, Database database) {
        candidateMapper = new MatchCandidateMapper();
        chatMapper = new MatchChatMapper();
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
        ClientService tokenService = new ClientServiceImpl(database);
        UserService userService = new UserServiceImpl(database, tokenService);
        InterestService interestService = new InterestServiceImpl(database, tokenService);
        CandidatesService candidatesService = new CandidatesServiceImpl(database, tokenService, candidateMapper);
        UserMatchesService userMatchesService = new UserMatchesServiceImpl(database, tokenService, chatMapper);
        ChatService chatService = new ChatServiceImpl(tokenService);
        save(userService);
        save(interestService);
        save(candidatesService);
        save(userMatchesService);
        save(chatService);
    }

    private static void buildMockServices(Database database) {
        UserService userService = new UserServiceMock(database);
        InterestService interestService = new InterestServiceMock(database);
        CandidatesService candidatesService = new CandidatesServiceMock(database, candidateMapper);
        UserMatchesService userMatchesService = new UserMatchesServiceMock(database, chatMapper);
        ChatService chatService = new ChatServiceMock();
        save(userService);
        save(interestService);
        save(candidatesService);
        save(userMatchesService);
        save(chatService);
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

    public static UserMatchesService getUserMatchesService() {
        return (UserMatchesService) services.get(ServiceType.USER_MATCHES);
    }

    public static ChatService getChatService() {
        return (ChatService) services.get(ServiceType.CHAT);
    }
}
