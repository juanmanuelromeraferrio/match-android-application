package com.match.fragment.candidates;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.match.R;
import com.match.client.entities.Candidate;

/**
 * Created by Juan Manuel Romera on 22/5/2016.
 */
public class CandidateViewHolder extends RecyclerView.ViewHolder {

    private ImageView image;
    private TextView name;
    private TextView interests;
    private CardView cardView;


    public CandidateViewHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView.findViewById(R.id.candidateCard);
        image = (ImageView) itemView.findViewById(R.id.candidateImage);
        name = (TextView) itemView.findViewById(R.id.candidateName);
        interests = (TextView) itemView.findViewById(R.id.candidateInterests);
    }

    public void loadCandidate(Candidate candidate) {
        if (candidate.getPhoto() != null) {
            BitmapDrawable photoDrawable = new BitmapDrawable(itemView.getResources(), candidate.getPhoto());
            image.setImageDrawable(photoDrawable);
        } else {
            image.setImageResource(R.drawable.profile);
        }
        name.setText(candidate.getName());
        interests.setText(candidate.getInterests());
    }

    public void setVisibility(int visibility) {
        cardView.setVisibility(visibility);
    }

}
