package com.match.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.match.R;
import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * Created by Juan Manuel Romera on 22/5/2016.
 */
public class ProgressViewHolder extends RecyclerView.ViewHolder {

    private ProgressWheel progressBar;

    public ProgressViewHolder(View v) {
        super(v);
        progressBar = (ProgressWheel) v.findViewById(R.id.progressBar);
    }

    public void spin() {
        progressBar.spin();
    }
}