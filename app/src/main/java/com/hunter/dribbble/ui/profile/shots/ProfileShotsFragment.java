package com.hunter.dribbble.ui.profile.shots;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hunter.dribbble.AppConstants;
import com.hunter.dribbble.R;

public class ProfileShotsFragment extends Fragment {

    public static ProfileShotsFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString(AppConstants.EXTRA_USER_ID, id);
        ProfileShotsFragment fragment = new ProfileShotsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_shots, container, false);
    }

}
