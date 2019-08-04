package com.stimednp.aplikasimoviecataloguesub4.testingdb;

import android.content.Context;

import androidx.room.Room;

/**
 * Created by rivaldy on 8/4/2019.
 */

public class DatabaseClientMovies {
    private Context context;
    private static DatabaseClientMovies mInstance;

    private AppDatabaseMovies appDatabase;
    private DatabaseClientMovies(Context context) {
        this.context = context;
        //create database name, my db name is : mymovies_database
        appDatabase = Room.databaseBuilder(context, AppDatabaseMovies.class, "mymovies_database").build();
    }

    public static synchronized DatabaseClientMovies getInstance(Context context1){
        if (mInstance == null){
            mInstance = new DatabaseClientMovies(context1);
        }
        return mInstance;
    }

    public AppDatabaseMovies getAppDatabase(){
        return appDatabase;
    }
}
