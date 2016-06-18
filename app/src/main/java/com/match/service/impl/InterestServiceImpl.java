package com.match.service.impl;

import com.match.client.MatchClient;
import com.match.client.entities.Interest;
import com.match.client.entities.response.InterestResponse;
import com.match.error.service.APIError;
import com.match.error.service.ServiceException;
import com.match.infrastructure.Database;
import com.match.service.api.InterestService;
import com.match.service.api.ClientService;
import com.match.utils.ErrorUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Juan Manuel Romera on 29/5/2016.
 */
public class InterestServiceImpl extends InterestService {

    private ClientService clientService;

    public InterestServiceImpl(Database database, ClientService clientService) {
        super(database);
        this.clientService = clientService;
    }

    @Override
    public Map<String, List<String>> getInterests() throws ServiceException {

        Map<String, List<String>> localInterests = getLocalInterests();
        if (localInterests != null) {
            return localInterests;
        }

        MatchClient matchClient = clientService.getAuthClient();
        Call<InterestResponse> call = matchClient.interests.getInterests();
        try {
            Response<InterestResponse> response = call.execute();
            if (response.isSuccessful()) {
                //Save Interests
                InterestResponse interestResponse = response.body();
                Map<String, List<String>> map = convertToMap(interestResponse.getInterests());
                this.database.setInterests(map);
                //Save Token
                clientService.saveToken(response.headers());
                return map;
            } else {
                APIError error = ErrorUtils.parseError(response);
                throw new ServiceException(error);
            }
        } catch (IOException e) {
            throw new ServiceException(e.getLocalizedMessage());
        }


    }

    @Override
    public Map<String, List<String>> getLocalInterests() {
        return database.getInterests();
    }

    private Map<String, List<String>> convertToMap(List<Interest> interestList) {
        Map<String, List<String>> map = new TreeMap<>();
        for (Interest interest : interestList) {
            String category = interest.getCategory();
            if (!map.containsKey(category)) {
                map.put(category, new ArrayList<String>());
            }
            map.get(category).add(interest.getValue());
        }

        return map;
    }
}
