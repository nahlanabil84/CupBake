package com.nanodegree.nahla.cupbake.fragments;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.nanodegree.nahla.cupbake.R;
import com.nanodegree.nahla.cupbake.models.recipeListing.Step;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StepsFragment extends Fragment {

    public static final String RECIPE_STEP_KEY = "RECIPE_STEP_KEY";
    private static final String POSITION_KEY = "POSITION_KEY";
    private static final String PLAY_WHEN_READY_KEY = "PLAY_WHEN_READY_KEY";
    private static final String CURRENT_WINDOW_KEY = "CURRENT_WINDOW_KEY";

    Step step;
    @BindView(R.id.exoPlayer)
    SimpleExoPlayerView exoPlayer;
    @BindView(R.id.stepDescriptionTV)
    TextView stepDescriptionTV;
    Unbinder unbinder;
    @BindView(R.id.stepThumbnailIV)
    ImageView stepThumbnailIV;
    @BindView(R.id.stepDetailsContainerLL)
    LinearLayout stepDetailsContainerLL;
    private SimpleExoPlayer player;
    private boolean playWhenReady = true;
    private long playbackPosition = 0;
    private int currentWindow = 0;

    public StepsFragment() {
    }

    public static StepsFragment newInstance(Step step) {
        StepsFragment fragment = new StepsFragment();
        Bundle args = new Bundle();
        args.putParcelable(RECIPE_STEP_KEY, step);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            step = getArguments().getParcelable(RECIPE_STEP_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steps, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (savedInstanceState != null && savedInstanceState.containsKey(POSITION_KEY)) {
            playbackPosition = savedInstanceState.getLong(POSITION_KEY);
            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY_KEY);
        }

        setUpData();

        return view;
    }

    private void setUpData() {
        if (step.getDescription() != null)
            stepDescriptionTV.setText(step.getDescription());
        else
            stepDescriptionTV.setVisibility(View.GONE);

        if (step.getThumbnailURL() == null || step.getThumbnailURL().isEmpty() || step.getThumbnailURL().contains(""))
            stepThumbnailIV.setVisibility(View.GONE);
        else
            Glide.with(getContext()).load(step.getThumbnailURL()).into(stepThumbnailIV);


        if (step.getVideoURL() == null || step.getVideoURL().isEmpty() || step.getVideoURL().equals(""))
            exoPlayer.setVisibility(View.GONE);
        else {
            if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                exoPlayer.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                stepDetailsContainerLL.setVisibility(View.GONE);
            } else if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                exoPlayer.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
                stepDetailsContainerLL.setVisibility(View.VISIBLE);
            }

            exoPlayer.setVisibility(View.VISIBLE);
            initPlayer();
        }
    }

    private void initPlayer() {

        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(), new DefaultLoadControl());

            exoPlayer.setPlayer(player);
            player.seekTo(currentWindow, playbackPosition);
            player.setPlayWhenReady(playWhenReady);
        }

        MediaSource mediaSource = buildMediaSource(Uri.parse(step.getVideoURL()));
        player.prepare(mediaSource, true, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong(POSITION_KEY, playbackPosition);
        outState.putLong(CURRENT_WINDOW_KEY, currentWindow);
        outState.putBoolean(PLAY_WHEN_READY_KEY, playWhenReady);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("cup-bake")).
                createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23)
            initPlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (step.getVideoURL() != null || !step.getVideoURL().isEmpty())
            initPlayer();

    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
