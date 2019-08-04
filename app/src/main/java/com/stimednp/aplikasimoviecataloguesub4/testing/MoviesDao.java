package com.stimednp.aplikasimoviecataloguesub4.testing;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Created by rivaldy on 8/4/2019.
 */
@Dao
public interface MoviesDao {
    @Query("SELECT * FROM movies")
    List<Movies> getAllMovies();

    @Insert
    void insert(Movies movies);

    @Delete
    void delete(Movies movies);

    @Update
    void update(Movies movies);
}
