package com.stimednp.aplikasimoviecataloguesub4.myactivity;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.stimednp.aplikasimoviecataloguesub4.R;
import com.stimednp.aplikasimoviecataloguesub4.myfragment.NavFavoritesFragment;
import com.stimednp.aplikasimoviecataloguesub4.myfragment.NavMoviesFragment;
import com.stimednp.aplikasimoviecataloguesub4.myfragment.NavTvShowFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;
    boolean doubleButtonBackExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState == null) {
            setDefaultFragment(navView);
        }
        //callmethod
    }

    private void setDefaultFragment(BottomNavigationView navView) {
        fragment = new NavMoviesFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                .commit();
        navView.getMenu().getItem(0).setChecked(true);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();
                    Fragment selectedFragment = new Fragment();
                    switch (id) {
                        case R.id.navigation_home:
                            selectedFragment = new NavMoviesFragment();
                            break;
                        case R.id.navigation_dashboard:
                            selectedFragment = new NavTvShowFragment();
                            break;
                        case R.id.navigation_notifications:
                            selectedFragment = new NavFavoritesFragment();
                            break;
                    }
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment, selectedFragment.getClass().getSimpleName())
                            .commit();
                    return true;
                }
            };

//    @Override
//    public void onBackPressed() {
//        if (doubleButtonBackExit) {
//            super.onBackPressed();
//            return;
//        }
//        this.doubleButtonBackExit = true;
//        Toast.makeText(this, "Tap 2x untuk keluar", Toast.LENGTH_SHORT).show();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                doubleButtonBackExit = false;
//            }
//        }, 2000);
//
//    }
}
