package com.stimednp.aplikasimoviecataloguesub4.myfragment;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.stimednp.aplikasimoviecataloguesub4.R;
import com.stimednp.aplikasimoviecataloguesub4.adapter.TvShowItemsAdapter;
import com.stimednp.aplikasimoviecataloguesub4.mymodel.MainViewModel;
import com.stimednp.aplikasimoviecataloguesub4.testing.TvShow;
import com.stimednp.aplikasimoviecataloguesub4.testing.TvShowAdapter;
import com.stimednp.aplikasimoviecataloguesub4.testingdb.DatabaseClientTvShow;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavTvShowFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = FavTvShowFragment.class.getSimpleName();
    private RecyclerView recyclerViewMovie;
    private MainViewModel mainViewModel;
    private TvShowItemsAdapter tvShowItemsAdapter;
    private SwipeRefreshLayout refreshLayoutMovie;
    private FrameLayout frameLayoutMovie;
    private ProgressBar progressBarMovie;

    public FavTvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fav_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBarMovie = view.findViewById(R.id.progressbar_tab_tvshow_room);
        refreshLayoutMovie = view.findViewById(R.id.swipe_scroll_tvshow_room);
        frameLayoutMovie = view.findViewById(R.id.framel_tvshow_room);
        recyclerViewMovie = view.findViewById(R.id.rv_tab_tvshow_room);
        recyclerViewMovie.setLayoutManager(new LinearLayoutManager(getContext()));

        refreshLayoutMovie.setOnRefreshListener(this);
        getTvShow();
    }

    private void getTvShow() {
        @SuppressLint("StaticFieldLeak")
        class GetTvShow extends AsyncTask<Void, Void, ArrayList<TvShow>> {

            @Override
            protected ArrayList<TvShow> doInBackground(Void... voids) {
                ArrayList<TvShow> tvShowList = (ArrayList<TvShow>) DatabaseClientTvShow
                        .getInstance(getContext())
                        .getAppDatabaseTvShow()
                        .tvShowDao()
                        .getAllTvShow();
                return tvShowList;
            }

            @Override
            protected void onPostExecute(ArrayList<TvShow> tvShows) {
                super.onPostExecute(tvShows);
                TvShowAdapter adapter = new TvShowAdapter(getContext(), tvShows);
                recyclerViewMovie.setAdapter(adapter);
            }
        }
        GetTvShow getTvShow = new GetTvShow();
        getTvShow.execute();
    }

    @Override
    public void onRefresh() {

    }
}
