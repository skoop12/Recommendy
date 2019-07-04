package edu.uchicago.skoop.profinal2019;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import androidx.lifecycle.LiveData;

import java.util.List;

// https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#0
// https://stackoverflow.com/questions/7491287/android-how-to-use-sharedpreferences-in-non-activity-class

public class RecommendationRepository {

    private RecommendationDao recommendationDao;
    private LiveData<List<Recommendation>> allRecs;

    RecommendationRepository(Application application) {
        RecommendationDatabase db = RecommendationDatabase.getDatabase(application);
        recommendationDao = db.recommendationDao();

        Context applicationContext = MainActivity.getContextOfApplication();

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(applicationContext);

        Boolean sortTitle = sharedPreferences.getBoolean("sortTitle", false);
        Boolean sortType = sharedPreferences.getBoolean("sortType", false);
        setAllRecsSorted(sortTitle, sortType);
    }

    LiveData<List<Recommendation>> getAllRecs() {
        return allRecs;
    }

    LiveData<List<Recommendation>> getAllRecsSortRefreshed() {
        Context applicationContext = MainActivity.getContextOfApplication();
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(applicationContext);

        Boolean sortTitle = sharedPreferences.getBoolean("sortTitle", false);
        Boolean sortType = sharedPreferences.getBoolean("sortType", false);
        setAllRecsSorted(sortTitle, sortType);
        return allRecs;
    }

    public void setAllRecsSorted(Boolean sortTitle, Boolean sortType) {
        if (sortTitle && sortType) {
            allRecs = recommendationDao.getAllRecsTitleTypeSort();
        } else if (sortTitle) {
            allRecs = recommendationDao.getAllRecsTitleSort();
        } else if (sortType) {
            allRecs = recommendationDao.getAllRecsTypeSort();
        } else {
            allRecs = recommendationDao.getAllRecs();
        }
    }

    public void insertRec (Recommendation recommendation) {
        new insertAsyncTask(recommendationDao).execute(recommendation);
    }

    private static class insertAsyncTask extends AsyncTask<Recommendation, Void, Void> {

        private RecommendationDao asyncTaskDao;

        insertAsyncTask(RecommendationDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Recommendation... params) {
            asyncTaskDao.insertRecommendation(params[0]);
            return null;
        }
    }

    public void deleteRec(Recommendation recommendation) {
        new DeleteAsyncTask(recommendationDao).execute(recommendation);
    }

    private static class DeleteAsyncTask extends AsyncTask<Recommendation, Void, Void> {

        private RecommendationDao asyncTaskDao;

        DeleteAsyncTask(RecommendationDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Recommendation... params) {
            asyncTaskDao.deleteRecommendation(params[0]);
            return null;
        }
    }
}
