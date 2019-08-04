package com.stimednp.aplikasimoviecataloguesub4.testingdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.stimednp.aplikasimoviecataloguesub4.testing.TvShow;
import com.stimednp.aplikasimoviecataloguesub4.testing.TvShowDao;

@Database(entities = {TvShow.class}, version = 1, exportSchema = false)
public abstract class AppDatabaseTvShow extends RoomDatabase {
    public abstract TvShowDao tvShowDao();
}
