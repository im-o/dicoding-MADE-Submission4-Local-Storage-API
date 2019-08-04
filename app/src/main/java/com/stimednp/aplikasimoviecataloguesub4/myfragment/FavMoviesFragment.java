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
import com.stimednp.aplikasimoviecataloguesub4.adapter.MovieItemsAdapter;
import com.stimednp.aplikasimoviecataloguesub4.mymodel.MainViewModel;
import com.stimednp.aplikasimoviecataloguesub4.testing.Movies;
import com.stimednp.aplikasimoviecataloguesub4.testing.MoviesAdapter;
import com.stimednp.aplikasimoviecataloguesub4.testingdb.DatabaseClientMovies;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavMoviesFragment extends Fragment {
    private static final String TAG = NavMoviesFragment.class.getSimpleName();
    private RecyclerView recyclerViewMovie;
    private MainViewModel mainViewModel;
    private MovieItemsAdapter movieItemsAdapter;
    private SwipeRefreshLayout refreshLayoutMovie;
    private FrameLayout frameLayoutMovie;
    private ProgressBar progressBarMovie;

    public FavMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fav_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBarMovie = view.findViewById(R.id.progressbar_tab_movies_room);
        refreshLayoutMovie = view.findViewById(R.id.swipe_scroll_movie_room);
        frameLayoutMovie = view.findViewById(R.id.framel_movie_room);
        recyclerViewMovie = view.findViewById(R.id.rv_tab_movies_room);
        recyclerViewMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        getMovies();
    }

    private void getMovies() {
        @SuppressLint("StaticFieldLeak")
        class GetMovies extends AsyncTask<Void, Void, ArrayList<Movies>> {

            @Override
            protected ArrayList<Movies> doInBackground(Void... voids) {
                ArrayList<Movies> moviesList = (ArrayList<Movies>) DatabaseClientMovies
                        .getInstance(getContext())
                        .getAppDatabase()
                        .moviesDao()
                        .getAllMovies();
                return moviesList;
            }

            @Override
            protected void onPostExecute(ArrayList<Movies> movies) {
                super.onPostExecute(movies);
                MoviesAdapter adapter = new MoviesAdapter(getContext(), movies);
                recyclerViewMovie.setAdapter(adapter);
            }
        }
        GetMovies getMovies = new GetMovies();
        getMovies.execute();
    }
}
