package com.example.destressit.activities.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.destressit.R;
import com.example.destressit.activities.MainActivity;
import com.example.destressit.activities.user.DashboardFragment;
import com.example.destressit.activities.user.ProfileFragment;
import com.example.destressit.activities.user.TherapistsFragment;
import com.example.destressit.core.OnBackPressed;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;


public class NavigationActivity extends AppCompatActivity implements ProfileFragment.OnFragmentInteractionListener, DashboardFragment.OnFragmentInteractionListener, TherapistsFragment.OnFragmentInteractionListener {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    fragment = new ProfileFragment();
                    break;
                case R.id.navigation_dashboard:
                    fragment = new DashboardFragment();
                    break;
                case R.id.navigation_therapists:
                    fragment = new TherapistsFragment();
                    break;
            }
            return loadFragment(fragment);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new DashboardFragment());
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.dashboardFragment);
        if (!(fragment instanceof OnBackPressed) || !((OnBackPressed) fragment).onBackPressed()) {
            startActivity(new Intent(this, MainActivity.class));
            //            super.onBackPressed();
        }
//        super.onBackPressed();
    }
}
