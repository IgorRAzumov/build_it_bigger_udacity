package com.udacity.gradle.builditbigger.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.BuildConfig;
import com.udacity.gradle.builditbigger.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivityFragment extends Fragment {
    private static final String AD_MOB_BANNER_KEY = BuildConfig.BANNER_KEY;
    private static final String AD_MOB_INTERSTITIAL_KEY = BuildConfig.INTERSTITIAL_KEY;
    @BindView(R.id.ll_fr_main_root_layout)
    LinearLayout linearLayout;

    @BindView(R.id.bt_fr_main_start_joke_lib)
    Button getJokeButton;

    private InterstitialAd interstitialAd;
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

    public interface OnActivityCallback {
        void startJoke();
    }

    private void initUi() {
        initAd();
        initButton();
    }

    private void initAd() {
        initBanner();
        initInterstitial();
    }

    private void initButton() {
        getJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitialAd != null && interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }else{
                    loadJoke();
                }
            }
        });
    }

    private void initInterstitial() {
        interstitialAd = new InterstitialAd(getContext());
        interstitialAd.setAdUnitId(AD_MOB_INTERSTITIAL_KEY);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                interstitialAd.loadAd(new AdRequest.Builder().build());
                loadJoke();
            }

        });
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        interstitialAd.loadAd(adRequest);

    }

    private void initBanner() {
        AdView adView = new AdView(getContext());
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(AD_MOB_BANNER_KEY);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        adView.setLayoutParams(layoutParams);
        linearLayout.addView(adView);


        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(adRequest);

    }

    private void loadJoke() {
        onActivityCallback.startJoke();
        getJokeButton.setEnabled(false);
    }
}
