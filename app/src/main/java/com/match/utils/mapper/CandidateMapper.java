package com.match.utils.mapper;

import com.match.client.entities.Candidate;
import com.match.client.entities.User;

/**
 * Created by Juan Manuel Romera on 25/5/2016.
 */
public interface CandidateMapper {

    Candidate map(User user);
}
