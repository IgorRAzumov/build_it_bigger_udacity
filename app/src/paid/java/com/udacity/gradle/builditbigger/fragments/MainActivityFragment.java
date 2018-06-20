package com.udacity.gradle.builditbigger.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



import com.udacity.gradle.builditbigger.BuildConfig;
import com.udacity.gradle.builditbigger.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivityFragment extends Fragment {
    private static final String AD_MOB_BANNER_KEY = BuildConfig.API_KEY;

    @BindView(R.id.bt_fr_main_start_joke_lib)
    Button getJokeButton;

    private OnActivityCallback onActivityCallback;
    private Unbinder unbinder;

    public MainActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        initUi();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivityFragment.OnActivityCallback) {
            onActivityCallback = (MainActivityFragment.OnActivityCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + getString(R.string.on_activity_callback__fragment_error) +
                    this.getClass().getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onActivityCallback = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public void onLoadingEnded() {
        getJokeButton.setEnabled(true);
    }

    private void initUi() {
        getJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActivityCallback.startJoke();
                getJokeButton.setEnabled(false);
            }
        });
    }

    public interface OnActivityCallback {
        void startJoke();
    }
}
