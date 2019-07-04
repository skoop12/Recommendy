package edu.uchicago.skoop.profinal2019;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

// https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#0

public class RecommendationViewModel extends AndroidViewModel {

    private RecommendationRepository recommendationRepository;

    private LiveData<List<Recommendation>> allRecs;

    public RecommendationViewModel (Application application) {
        super(application);
        recommendationRepository = new RecommendationRepository(application);
        allRecs = recommendationRepository.getAllRecs();
    }

    LiveData<List<Recommendation>> getAllRecs() { return allRecs; }

    // Using this to sort the data if user changes sort preferences before refreshing the whole dataset
    LiveData<List<Recommendation>> getAllRecsSortRefreshed() {
        allRecs = recommendationRepository.getAllRecsSortRefreshed();
        return allRecs;
    }

    public void insert(Recommendation recommendation) { recommendationRepository.insertRec(recommendation); }

    public void delete(Recommendation recommendation) { recommendationRepository.deleteRec(recommendation); }

}
