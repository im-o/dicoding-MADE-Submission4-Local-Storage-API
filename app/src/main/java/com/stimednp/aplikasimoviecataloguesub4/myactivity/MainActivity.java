package com.stimednp.aplikasimoviecataloguesub4.myactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.stimednp.aplikasimoviecataloguesub4.R;
import com.stimednp.aplikasimoviecataloguesub4.myfragment.NavFavoritesFragment;
import com.stimednp.aplikasimoviecataloguesub4.myfragment.NavMoviesFragment;
import com.stimednp.aplikasimoviecataloguesub4.myfragment.NavTvShowFragment;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;
    boolean doubleButtonBackExit;
    Toolbar toolbar;
    TextView tvToolbarMain;
    String strMovies, strTvShow, strFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.main_tollbar);
        tvToolbarMain = findViewById(R.id.tv_toolbar_main);
        setSupportActionBar(toolbar);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState == null) {
            setDefaultFragment(navView);
        }

        strMovies = getResources().getString(R.string.name_tab1_movies);
        strTvShow = getResources().getString(R.string.name_tab2_tvshow);
        strFav = getResources().getString(R.string.favorite);

        //callmethod
        setActionBarToolbar();
    }

    private void setActionBarToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            setTitleToolbar(strMovies);
        }
    }

    private void setTitleToolbar(String s) {
        tvToolbarMain.setText(s);
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
                        case R.id.nav_movies:
                            setTitleToolbar(strMovies);
                            selectedFragment = new NavMoviesFragment();
                            break;
                        case R.id.nav_tvhow:
                            setTitleToolbar(strTvShow);
                            selectedFragment = new NavTvShowFragment();
                            break;
                        case R.id.nav_favorite:
                            setTitleToolbar(strFav);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_right_appbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.change_set_lang) {
            Intent changeLangIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(changeLangIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        String tapclose = getResources().getString(R.string.tap_to_close);
        if (doubleButtonBackExit) {
            super.onBackPressed();
            return;
        }
        this.doubleButtonBackExit = true;
        Toast.makeText(this, tapclose, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleButtonBackExit = false;
            }
        }, 2000);

    }
}
