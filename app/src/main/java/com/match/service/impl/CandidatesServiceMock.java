package com.match.service.impl;

import android.graphics.Bitmap;

import com.match.MatchApplication;
import com.match.R;
import com.match.client.entities.Candidate;
import com.match.client.entities.Interest;
import com.match.client.entities.User;
import com.match.error.service.ServiceException;
import com.match.infrastructure.Database;
import com.match.service.api.CandidatesService;
import com.match.service.api.ServiceType;
import com.match.utils.mapper.CandidateMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan Manuel Romera on 22/5/2016.
 */
public class CandidatesServiceMock extends CandidatesService {

    public CandidatesServiceMock(Database database, CandidateMapper candidateMapper) {
        super(database, candidateMapper);
    }

    @Override
    public List<Candidate> findCandidates(User user) throws ServiceException {
        List<User> users = new ArrayList<>();

        //Erica
        List<Interest> interestsErica = new ArrayList<>();
        interestsErica.add(new Interest("Bandas", "Arctic Monkeys"));
        interestsErica.add(new Interest("Bandas", "The Strokes"));
        interestsErica.add(new Interest("Hobby", "Viajar"));
        User erica = new User("Erica Atanasoff");
        erica.setInterests(interestsErica);
        users.add(erica);

        //Juan
        List<Interest> interestsJuan = new ArrayList<>();
        interestsJuan.add(new Interest("Bandas", "Led Zeppelin"));
        interestsJuan.add(new Interest("Clubs", "Independiente"));
        interestsJuan.add(new Interest("Hobby", "Viajar"));
        User juan = new User("Juan Manuel Romera");
        juan.setInterests(interestsJuan);
        users.add(juan);

        //Agustin
        List<Interest> interestsAgustin = new ArrayList<>();
        interestsAgustin.add(new Interest("Clubs", "Boca"));
        interestsAgustin.add(new Interest("Juegos", "Dota"));
        interestsAgustin.add(new Interest("Deporte", "Tenis"));
        User agustin = new User("Agustin Linari");
        agustin.setInterests(interestsAgustin);
        users.add(agustin);

        List<Candidate> candidates = new ArrayList<>();
        for (User user_ : users) {
            Candidate candidate = mapper.map(user_);
            candidates.add(candidate);
        }

        return candidates;
    }

    @Override
    public Boolean voteYes(String userId, String candidateID) throws ServiceException {
        return Boolean.TRUE;
    }

    @Override
    public void voteNo(String userId, String candidateID) throws ServiceException {

    }

    @Override
    public String findPhoto(String id) {
        return null;
    }

    @Override
    public ServiceType getType() {
        return ServiceType.CANDIDATES;
    }
}
