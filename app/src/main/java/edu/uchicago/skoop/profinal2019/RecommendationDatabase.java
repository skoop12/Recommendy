package edu.uchicago.skoop.profinal2019;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

// https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#0

@Database(entities = {Recommendation.class}, version = 1, exportSchema = false)
public abstract class RecommendationDatabase extends RoomDatabase {

    public abstract RecommendationDao recommendationDao();

    private static volatile RecommendationDatabase INSTANCE;

    static RecommendationDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RecommendationDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RecommendationDatabase.class, "recs_database")
                            .addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final RecommendationDao dao;

        PopulateDbAsync(RecommendationDatabase db) {
            dao = db.recommendationDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            return null;
        }
    }
}

