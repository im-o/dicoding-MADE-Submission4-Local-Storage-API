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
public interface TvShowDao {
    @Query("SELECT * FROM tvshows")
    List<TvShow> getAllTvShow();

    @Insert
    void insert(TvShow tvShow);

    @Delete
    void delete(TvShow tvShow);

    @Update
    void update(TvShow tvShow);
}
