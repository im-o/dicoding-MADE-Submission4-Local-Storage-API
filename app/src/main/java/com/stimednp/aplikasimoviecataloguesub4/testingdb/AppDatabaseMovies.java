package com.stimednp.aplikasimoviecataloguesub4.testingdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.stimednp.aplikasimoviecataloguesub4.testing.Movies;
import com.stimednp.aplikasimoviecataloguesub4.testing.MoviesDao;

/**
 * Created by rivaldy on 8/4/2019.
 */

@Database(entities = {Movies.class}, version = 1, exportSchema = false)
public abstract class AppDatabaseMovies extends RoomDatabase {
    public abstract MoviesDao moviesDao();
}


