package edu.uchicago.skoop.profinal2019;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

// https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#0

@Dao
public interface RecommendationDao {

    @Insert
    void insertRecommendation(Recommendation recommendation);

    @Query("SELECT * from recs_table")
    LiveData<List<Recommendation>> getAllRecs();

    @Query("SELECT * from recs_table ORDER BY name ASC")
    LiveData<List<Recommendation>> getAllRecsTitleSort();

    @Query("SELECT * from recs_table ORDER BY type ASC")
    LiveData<List<Recommendation>> getAllRecsTypeSort();

    @Query("SELECT * from recs_table ORDER BY name,type ASC")
    LiveData<List<Recommendation>> getAllRecsTitleTypeSort();

    @Delete
    void deleteRecommendation(Recommendation recommendation);

}
