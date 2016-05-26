package com.match.utils.mapper;

import com.match.client.entities.Candidate;
import com.match.client.entities.Interest;
import com.match.client.entities.User;
import com.match.utils.PhotoUtils;

import java.util.List;

/**
 * Created by Juan Manuel Romera on 25/5/2016.
 */
public class MatchCandidateMapper implements CandidateMapper {

    public static int MAX_SHOW_INTEREST = 3;

    @Override
    public Candidate map(User user) {
        if (user.getPhoto() != null) {
            return new Candidate("", user.getName(), PhotoUtils.base64ToBitmap(user.getPhoto()), decodeInterest(user.getInterests()));
        } else {
            return new Candidate("", user.getName(), null, "");
        }
    }

    private String decodeInterest(List<Interest> interests) {
        StringBuffer result = new StringBuffer("Le gustan: ");
        int index = 0;
        for (Interest interest : interests) {
            if (index == MAX_SHOW_INTEREST) {
                break;
            }
            index++;
            result.append(interest.getValue());
            if (index != MAX_SHOW_INTEREST) {
                result.append(", ");
            } else {
                result.append(".");
            }
        }

        if (index == 0) {
            return "";
        } else {
            return result.toString();
        }
    }
}
