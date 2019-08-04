package com.stimednp.aplikasimoviecataloguesub4.testingdb;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClientTvShow {
    private Context context;
    private static DatabaseClientTvShow mInstance;

    private AppDatabaseTvShow appDatabaseTvShow;
    private DatabaseClientTvShow(Context context) {
        this.context = context;
        //create database name, my db name is : mymovies_database
        appDatabaseTvShow = Room.databaseBuilder(context, AppDatabaseTvShow.class, "mytvshow_database").build();
    }

    public static synchronized DatabaseClientTvShow getInstance(Context context1){
        if (mInstance == null){
            mInstance = new DatabaseClientTvShow(context1);
        }
        return mInstance;
    }

    public AppDatabaseTvShow getAppDatabaseTvShow(){
        return appDatabaseTvShow;
    }
}
