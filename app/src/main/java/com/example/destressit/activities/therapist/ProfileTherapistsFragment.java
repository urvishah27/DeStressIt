package com.example.destressit.activities.therapist;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.destressit.activities.EditProfileActivity;
import com.example.destressit.R;
import com.example.destressit.activities.RegistrationActivity;
import com.example.destressit.core.PreferenceUtil;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileTherapistsFragment extends Fragment {

    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_therapists_profile, container, false);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        getView().findViewById(R.id.edit_therapist_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), EditProfileActivity.class));
            }
        });
        getView().findViewById(R.id.therapist_signOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                PreferenceUtil.clearAllPreferences(getContext());
                startActivity(new Intent(getContext(), RegistrationActivity.class));
            }
        });
    }
}