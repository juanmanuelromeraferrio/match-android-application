package com.match.utils.mapper;

import com.match.client.entities.Candidate;
import com.match.client.entities.Interest;
import com.match.client.entities.User;

import java.util.List;

/**
 * Created by Juan Manuel Romera on 25/5/2016.
 */
public class MatchCandidateMapper implements CandidateMapper {

    public static int MAX_SHOW_INTEREST = 3;

    @Override
    public Candidate map(User user) {
        if (user.getPhoto() != null) {
            return new Candidate(user.getId(), user.getName(), user.getAge(), null, decodeInterest(user.getInterests()));
        } else {
            return new Candidate(user.getId(), user.getName(), user.getAge(), null, decodeInterest(user.getInterests()));
        }
    }

    private String decodeInterest(List<Interest> interests) {
        if (interests == null) {
            return "";
        }
        StringBuffer result = new StringBuffer("Le gustan: ");
        int index = 0;
        int size = interests.size() > MAX_SHOW_INTEREST ? MAX_SHOW_INTEREST : interests.size();
        for (Interest interest : interests) {
            if (index == MAX_SHOW_INTEREST) {
                break;
            }
            index++;
            result.append(interest.getValue());

            if (size > 1 && (index + 1) == size) {
                result.append(" y ");
            } else if (index == size) {
                result.append(".");
            } else {
                result.append(", ");
            }
        }

        if (index == 0) {
            return "";
        } else {
            return result.toString();
        }
    }
}
