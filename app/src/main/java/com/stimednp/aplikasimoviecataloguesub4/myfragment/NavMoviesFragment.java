package com.stimednp.aplikasimoviecataloguesub4.myfragment;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.stimednp.aplikasimoviecataloguesub4.R;
import com.stimednp.aplikasimoviecataloguesub4.adapter.MovieItemsAdapter;
import com.stimednp.aplikasimoviecataloguesub4.addingmethod.AllMyStrings;
import com.stimednp.aplikasimoviecataloguesub4.addingmethod.CheckNetwork;
import com.stimednp.aplikasimoviecataloguesub4.mymodel.MainViewModel;
import com.stimednp.aplikasimoviecataloguesub4.mymodel.MovieItems;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavMoviesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = NavMoviesFragment.class.getSimpleName();
    private RecyclerView recyclerViewMovie;
    private MainViewModel mainViewModel;
    private MovieItemsAdapter movieItemsAdapter;
    private SwipeRefreshLayout refreshLayoutMovie;
    private RelativeLayout frameLayoutMovie;
    private ProgressBar progressBarMovie;

    public NavMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nav_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBarMovie = view.findViewById(R.id.progressbar_tab_movies);
        refreshLayoutMovie = view.findViewById(R.id.swipe_scroll_movie);
        frameLayoutMovie = view.findViewById(R.id.framel_movie);
        recyclerViewMovie = view.findViewById(R.id.rv_tab_movies);

        if (getActivity() != null) {
            mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
            mainViewModel.getListMovies().observe(getActivity(), getMovie);
            movieItemsAdapter = new MovieItemsAdapter(getContext());
        }
        refreshLayoutMovie.setOnRefreshListener(this);

        //callmethod
        checkingNetwork();
    }

    private Observer<? super ArrayList<MovieItems>> getMovie = new Observer<ArrayList<MovieItems>>() {
        @Override
        public void onChanged(ArrayList<MovieItems> movieItems) {
            if (movieItemsAdapter != null) {
                movieItemsAdapter.setMoviesData(movieItems);
                showLoading(false);
                timeRecyclerLoadFalse();
            }
        }
    };

    private void checkingNetwork() {
        AllMyStrings myStr = new AllMyStrings();
        String noInternet = myStr.getNoInet(getContext());
        String tryAgain = myStr.getTryAgain(getContext());
        String reconnect = myStr.getRecon(getContext());
        String wrongNet = myStr.getWrongNet(getContext());
        String wrongError = myStr.getWrongErr(getContext());

        if (getContext() != null) {
            showRecyclerList();
            if (CheckNetwork.isInternetAvailable(getContext())) {
                int status = CheckNetwork.statusInternet;
                if (status == 0) {//disconnect
                    showLoading(false);
                    timeRecyclerLoadFalse();
                    Snackbar snackbar = Snackbar.make(frameLayoutMovie, noInternet, Snackbar.LENGTH_SHORT).setAction(tryAgain, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkingNetwork();
                        }
                    });
                    snackbar.setActionTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                    snackbar.show();
                } else if (status == 1) {//connected
                    showRecyclerList();
                } else if (status == 2) {//reconnection
                    Toast.makeText(getContext(), reconnect, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), wrongNet, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), wrongError, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showRecyclerList() {
        if (mainViewModel != null) {
            mainViewModel.setListMovies();
            movieItemsAdapter.notifyDataSetChanged();
            recyclerViewMovie.setHasFixedSize(true);
            recyclerViewMovie.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerViewMovie.setAdapter(movieItemsAdapter);
        }
    }

    private void showLoading(boolean state) {
        if (state) {
            progressBarMovie.setVisibility(View.VISIBLE);
        } else {
            progressBarMovie.setVisibility(View.GONE);
        }
    }

    private void timeRecyclerLoadFalse() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (refreshLayoutMovie.isRefreshing()) {
                    refreshLayoutMovie.setRefreshing(false);
                }
            }
        }, 1000);
    }

    @Override
    public void onRefresh() {
        checkingNetwork();
    }
}
