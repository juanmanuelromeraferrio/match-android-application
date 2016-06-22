package com.match.fragment.candidates;

import android.graphics.Bitmap;

import com.match.activity.api.BaseView;
import com.match.client.entities.Candidate;
import com.match.client.entities.User;

import java.util.List;

/**
 * Created by Juan Manuel Romera on 22/5/2016.
 */
public interface CandidatesView extends BaseView {

    void addCandidates(List<Candidate> user);

    void removeCurrentCandidate();

    void finishLoadingCandidates();

    void onError(String errorMsg);

    void showMatch(final Candidate candidate);

    void loadPhoto(String idCandidatePhoto, Bitmap photo);
}
