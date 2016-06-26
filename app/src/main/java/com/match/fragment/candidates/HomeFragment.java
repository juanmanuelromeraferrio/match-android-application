package com.match.fragment.candidates;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.match.R;
import com.match.activity.chat.ChatLobbyActivity;
import com.match.activity.login.LoginActivity;
import com.match.client.entities.Candidate;
import com.match.service.factory.ServiceFactory;
import com.match.utils.UiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Juan Manuel Romera Ferrio on 26/10/16.
 */
public class HomeFragment extends Fragment implements CandidatesView {

    private static final String TAG = "HomeFragment";

    private List<Candidate> candidates;
    private Map<String, String> photos;

    private CandidateViewHolder candidateViewHolder;
    private TextView emptyView;
    private Context activity;
    private LinearLayout linearLayoutHeaderProgress;
    private ImageButton buttonYes;
    private ImageButton buttonNo;
    private ImageButton reloadButton;

    private CandidatesController controller;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();
        controller = new CandidatesControllerImpl(this);
        // Toolbar:
        setHasOptionsMenu(true);

        // View:
        View mainView = inflater.inflate(R.layout.fragment_home, container, false);
        mainView.setTag(TAG);

        candidateViewHolder = new CandidateViewHolder(mainView);
        linearLayoutHeaderProgress = (LinearLayout) mainView.findViewById(R.id.linlaHeaderProgress);

        photos = new ConcurrentHashMap<>();

        // ListView
        emptyView = (TextView) mainView.findViewById(R.id.empty_view);
        candidates = controller.getCandidates();

        //Buttons
        buttonYes = (ImageButton) mainView.findViewById(R.id.yesButton);
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeCandidate();
            }
        });
        buttonYes.setEnabled(Boolean.FALSE);
        buttonNo = (ImageButton) mainView.findViewById(R.id.noButton);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dislikeCandidate();
            }
        });
        buttonNo.setEnabled(Boolean.FALSE);
        reloadButton = (ImageButton) mainView.findViewById(R.id.reloadButton);
        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadCandidates();
            }
        });
        reloadButton.setEnabled(Boolean.FALSE);

        if (candidates.isEmpty()) {
            controller.findCandidates();
        } else {
            findAllPhotos(candidates);
            refreshCandidate();
        }

        return mainView;
    }

    private void likeCandidate() {
        Candidate currentCandidate = getCurrentCandidate();
        if (currentCandidate != null) {
            controller.likeCandidate(currentCandidate);
        }
    }

    private void dislikeCandidate() {
        Candidate currentCandidate = getCurrentCandidate();
        if (currentCandidate != null) {
            controller.dislikeCandidate(currentCandidate);
        }
    }

    private void reloadCandidates() {
        controller.findCandidates();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_logout:
                logout();
                return true;
            case R.id.action_chat:
                goToChat();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        ServiceFactory.getUserService().logout();
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goToChat() {
        Intent intent = new Intent(activity, ChatLobbyActivity.class);
        startActivity(intent);
    }

    @Override
    public void showProgress() {
        if (candidates.isEmpty()) {
            this.candidateViewHolder.setVisibility(View.GONE);
            this.emptyView.setVisibility(View.GONE);
            this.linearLayoutHeaderProgress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        this.linearLayoutHeaderProgress.setVisibility(View.GONE);
    }

    @Override
    public void goToNext() {

    }

    @Override
    public void sessionExpired() {
        UiUtils.showSessionExpired(activity);
    }

    @Override
    public void addCandidates(List<Candidate> candidates) {
        findAllPhotos(candidates);
        this.candidates.addAll(candidates);
        refreshCandidate();
    }

    private void findAllPhotos(List<Candidate> candidates) {
        for (Candidate candidate : candidates) {
            if (candidate.getPhoto() == null) {
                this.controller.getPhoto(candidate);
            }
        }
    }

    @Override
    public void removeCurrentCandidate() {
        Candidate remove = this.candidates.remove(0);
        this.photos.remove(remove.getId());
        refreshCandidate();

    }

    @Override
    public void finishLoadingCandidates() {
        if (controller.isFindingCandidates()) {
            showProgress();
        } else {
            if (candidates.isEmpty()) {
                candidateViewHolder.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
                buttonNo.setEnabled(Boolean.FALSE);
                buttonYes.setEnabled(Boolean.FALSE);
                reloadButton.setEnabled(Boolean.TRUE);
            } else {
                candidateViewHolder.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
                buttonNo.setEnabled(Boolean.TRUE);
                buttonYes.setEnabled(Boolean.TRUE);
                reloadButton.setEnabled(Boolean.FALSE);
            }
        }

    }

    @Override
    public void onError(String errorMsg) {
        Toast.makeText(activity, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMatch(final Candidate candidate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogCustom);
        builder.setTitle(R.string.candidate_match_title);
        builder.setMessage(getString(R.string.candidate_match_message, candidate.getName()));
        builder.setCancelable(Boolean.FALSE);
        builder.setPositiveButton(getString(R.string.aceptar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                controller.acceptMatch(candidate);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void loadPhoto(String idCandidatePhoto, String photo) {
        if (!this.candidates.isEmpty()) {
            Candidate candidate = candidates.get(0);
            if (candidate.getId().equals(idCandidatePhoto)) {
                candidateViewHolder.loadPhoto(candidate, photo);
            } else {
                photos.put(idCandidatePhoto, photo);
            }
        }
    }

    private void refreshCandidate() {
        if (!this.candidates.isEmpty()) {
            Candidate candidate = this.candidates.get(0);
            candidateViewHolder.loadCandidate(candidate);
            if (photos.containsKey(candidate.getId())) {
                candidateViewHolder.loadPhoto(candidate, photos.get(candidate.getId()));
            }
        }
        finishLoadingCandidates();
    }

    private Candidate getCurrentCandidate() {
        if (!candidates.isEmpty()) {
            return this.candidates.get(0);
        }
        return null;
    }
}
