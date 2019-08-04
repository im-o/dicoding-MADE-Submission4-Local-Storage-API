package com.stimednp.aplikasimoviecataloguesub4.myactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.stimednp.aplikasimoviecataloguesub4.R;
import com.stimednp.aplikasimoviecataloguesub4.adapter.MovieItemsAdapter;
import com.stimednp.aplikasimoviecataloguesub4.adapter.TvShowItemsAdapter;
import com.stimednp.aplikasimoviecataloguesub4.addingmethod.AllOtherMethod;
import com.stimednp.aplikasimoviecataloguesub4.mymodel.MovieItems;
import com.stimednp.aplikasimoviecataloguesub4.mymodel.TvShowItems;
import com.stimednp.aplikasimoviecataloguesub4.testing.Movies;
import com.stimednp.aplikasimoviecataloguesub4.testing.MoviesAdapter;
import com.stimednp.aplikasimoviecataloguesub4.testing.TvShow;
import com.stimednp.aplikasimoviecataloguesub4.testing.TvShowAdapter;
import com.stimednp.aplikasimoviecataloguesub4.testingdb.DatabaseClientMovies;
import com.stimednp.aplikasimoviecataloguesub4.testingdb.DatabaseClientTvShow;

public class DetailsMovieActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_WHERE_FROM = "extra_where_from";
    Toolbar toolbarDetails;
    CollapsingToolbarLayout collapse;
    TextView tvMovieTitle, tvMovieDesc, tvMovieRelease, tvMovieRating, tvMovieVoteCount;
    ImageView imgViewFromUrl, imgViewBg;
    CardView cardViewDetails;

    private String movieTitle, movieDesc, movieRelease, movieRating, movieVoteCount, movieUrlPhoto, movieUrlBg, movieDate, movieYearRelease;
    private String tvShowTitle, tvShowDesc, tvShowRelease, tvShowRating, tvShowVoteCount, tvShowUrlPhoto, tvShowUrlBg, tvShowDate, tvShowYearRelease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);
        //inisial
        toolbarDetails = findViewById(R.id.toolbar_detail);
        collapse = findViewById(R.id.collapse_toolbar_detail);
        tvMovieTitle = findViewById(R.id.tv_title_detail);
        tvMovieDesc = findViewById(R.id.tv_desc_detail);
        tvMovieRelease = findViewById(R.id.tv_release_date_detail);
        tvMovieRating = findViewById(R.id.tv_rating_detail);
        tvMovieVoteCount = findViewById(R.id.tv_vote_count_detail);
        imgViewFromUrl = findViewById(R.id.img_movie_photo_detail);
        imgViewBg = findViewById(R.id.img_bg_detail);
        cardViewDetails = findViewById(R.id.card_view_img_detail);
        setSupportActionBar(toolbarDetails);
        collapse.setExpandedTitleColor(Color.argb(0, 0, 0, 0));

        //callmethod
        setActionBarToolbar();
        getDataParceable();
    }

    private void getDataParceable() {
        String whereFrom = getIntent().getStringExtra(EXTRA_WHERE_FROM);
        String pathImg = "https://image.tmdb.org/t/p/w500";
        cardViewDetails.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_scale_animation));

        if (whereFrom.equals(MovieItemsAdapter.TAG)) { //for details TabMoviesFragment
            MovieItems movieItems = getIntent().getParcelableExtra(EXTRA_MOVIE);
            if (movieItems != null) {
                movieTitle = movieItems.getTitle();
                movieDesc = movieItems.getOverview();
                movieRelease = movieItems.getRelease_date();
                movieRating = movieItems.getVote_average().toString();
                movieVoteCount = movieItems.getVote_count();
                movieUrlPhoto = movieItems.getPoster_path();
                movieUrlBg = movieItems.getBackdrop_path();
                AllOtherMethod allOtherMethod = new AllOtherMethod();
                movieDate = allOtherMethod.changeFormatDate(movieRelease);
                movieYearRelease = allOtherMethod.getLastYear(movieDate);

                tvMovieTitle.setText(String.format(movieTitle + " (%s)", movieYearRelease));
                tvMovieDesc.setText(movieDesc);
                tvMovieRelease.setText(movieDate);
                tvMovieRating.setText(movieRating);
                tvMovieVoteCount.setText(movieVoteCount);
                Glide.with(getApplicationContext())
                        .load(pathImg + movieUrlPhoto)
                        .into(imgViewFromUrl);

                Glide.with(getApplicationContext())
                        .load(pathImg + movieUrlBg)
                        .into(imgViewBg);
            }
        } else if (whereFrom.equals(TvShowItemsAdapter.TAG)) { //for details TabTvShowFragment
            TvShowItems tvShowItems = getIntent().getParcelableExtra(EXTRA_MOVIE);
            if (tvShowItems != null) {
                tvShowTitle = tvShowItems.getName();
                tvShowDesc = tvShowItems.getOverview();
                tvShowRelease = tvShowItems.getFirst_air_date();
                tvShowRating = tvShowItems.getVote_average().toString();
                tvShowVoteCount = tvShowItems.getVote_count();
                tvShowUrlPhoto = tvShowItems.getPoster_path();
                tvShowUrlBg = tvShowItems.getBackdrop_path();
                AllOtherMethod allOtherMethod = new AllOtherMethod();
                tvShowDate = allOtherMethod.changeFormatDate(tvShowRelease);
                tvShowYearRelease = allOtherMethod.getLastYear(tvShowDate);

                tvMovieTitle.setText(String.format(tvShowTitle + " (%s)", tvShowYearRelease));
                tvMovieDesc.setText(tvShowDesc);
                tvMovieRelease.setText(tvShowDate);
                tvMovieRating.setText(tvShowRating);
                tvMovieVoteCount.setText(tvShowVoteCount);
                Glide.with(getApplicationContext())
                        .load(pathImg + tvShowUrlPhoto)
                        .into(imgViewFromUrl);

                Glide.with(getApplicationContext())
                        .load(pathImg + tvShowUrlBg)
                        .into(imgViewBg);
            }
        } else if (whereFrom.equals(MoviesAdapter.TAG)) { //for details MoviesAdapter
            Movies movies = getIntent().getParcelableExtra(EXTRA_MOVIE);
            if (movies != null) {
                movieTitle = movies.getTitle();
                movieDesc = movies.getOverview();
                movieRelease = movies.getRelease_date();
                movieRating = movies.getVote_average().toString();
                movieVoteCount = movies.getVote_count();
                movieUrlPhoto = movies.getPoster_path();
                movieUrlBg = movies.getBackdrop_path();
                AllOtherMethod allOtherMethod = new AllOtherMethod();
                movieDate = allOtherMethod.changeFormatDate(movieRelease);
                movieYearRelease = allOtherMethod.getLastYear(movieDate);

                tvMovieTitle.setText(String.format(movieTitle + " (%s)", movieYearRelease));
                tvMovieDesc.setText(movieDesc);
                tvMovieRelease.setText(movieDate);
                tvMovieRating.setText(movieRating);
                tvMovieVoteCount.setText(movieVoteCount);
                Glide.with(getApplicationContext())
                        .load(pathImg + movieUrlPhoto)
                        .into(imgViewFromUrl);

                Glide.with(getApplicationContext())
                        .load(pathImg + movieUrlBg)
                        .into(imgViewBg);
            }
        } else if (whereFrom.equals(TvShowAdapter.TAG)) { //for details TvShowAdapter
            TvShow tvShow = getIntent().getParcelableExtra(EXTRA_MOVIE);
            if (tvShow != null) {
                tvShowTitle = tvShow.getName();
                tvShowDesc = tvShow.getOverview();
                tvShowRelease = tvShow.getFirst_air_date();
                tvShowRating = tvShow.getVote_average().toString();
                tvShowVoteCount = tvShow.getVote_count();
                tvShowUrlPhoto = tvShow.getPoster_path();
                tvShowUrlBg = tvShow.getBackdrop_path();
                AllOtherMethod allOtherMethod = new AllOtherMethod();
                tvShowDate = allOtherMethod.changeFormatDate(tvShowRelease);
                tvShowYearRelease = allOtherMethod.getLastYear(tvShowDate);

                tvMovieTitle.setText(String.format(tvShowTitle + " (%s)", tvShowYearRelease));
                tvMovieDesc.setText(tvShowDesc);
                tvMovieRelease.setText(tvShowDate);
                tvMovieRating.setText(tvShowRating);
                tvMovieVoteCount.setText(tvShowVoteCount);
                Glide.with(getApplicationContext())
                        .load(pathImg + tvShowUrlPhoto)
                        .into(imgViewFromUrl);

                Glide.with(getApplicationContext())
                        .load(pathImg + tvShowUrlBg)
                        .into(imgViewBg);
            }
        }
    }

    private void setActionBarToolbar() {
        toolbarDetails.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbarDetails.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorite, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setMode(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    private void setMode(int itemId) {
        String whereFrom = getIntent().getStringExtra(EXTRA_WHERE_FROM);
        if (itemId == R.id.menu_favorite) {
            if (whereFrom.equals(MovieItemsAdapter.TAG)){
                Toast.makeText(this, "Favorite Movies", Toast.LENGTH_SHORT).show();
                setMovies();
            } else if (whereFrom.equals(TvShowItemsAdapter.TAG)){
                Toast.makeText(this, "Favorite Tv Show", Toast.LENGTH_SHORT).show();
                setTvShows();
            }
        }
    }

    //Room
    private void setMovies() {
        @SuppressLint("StaticFieldLeak")
        class SetMovies extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                Movies movies = new Movies();
                movies.setTitle(movieTitle);
                movies.setOverview(movieDesc);
                movies.setRelease_date(movieRelease);
                movies.setVote_average(Double.parseDouble(movieRating));
                movies.setVote_count(movieVoteCount);
                movies.setPoster_path(movieUrlPhoto);
                movies.setBackdrop_path(movieUrlBg);
                movies.setFavorite(true);

//                movieTitle, movieDesc, movieRelease, movieRating, movieVoteCount, movieUrlPhoto, movieUrlBg, movieDate, movieYearRelease;

                //adding to db
                DatabaseClientMovies.getInstance(getApplicationContext()).getAppDatabase()
                        .moviesDao()
                        .insert(movies);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
//                finish();
                Toast.makeText(DetailsMovieActivity.this, "SAVEEDDD", Toast.LENGTH_SHORT).show();
            }
        }
        SetMovies setMovies = new SetMovies();
        setMovies.execute();
    }

    private void setTvShows() {
        @SuppressLint("StaticFieldLeak")
        class SetTvShow extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                TvShow tvShow = new TvShow();
                tvShow.setName(tvShowTitle);
                tvShow.setOverview(tvShowDesc);
                tvShow.setFirst_air_date(tvShowRelease);
                tvShow.setVote_average(Double.parseDouble(tvShowRating));
                tvShow.setVote_count(tvShowVoteCount);
                tvShow.setPoster_path(tvShowUrlPhoto);
                tvShow.setBackdrop_path(tvShowUrlBg);
                tvShow.setFavorite(true);

                //adding to db
                DatabaseClientTvShow.getInstance(getApplicationContext()).getAppDatabaseTvShow()
                        .tvShowDao()
                        .insert(tvShow);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
//                finish();
                Toast.makeText(DetailsMovieActivity.this, "SAVEEDDD", Toast.LENGTH_SHORT).show();
            }
        }
        SetTvShow setTvShow = new SetTvShow();
        setTvShow.execute();
    }
}
