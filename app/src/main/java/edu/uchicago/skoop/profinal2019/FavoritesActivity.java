package edu.uchicago.skoop.profinal2019;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

// https://developer.android.com/guide/topics/ui/settings

public class FavoritesActivity extends AppCompatActivity {

    private RecommendationViewModel recViewModel;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle();

        RecyclerView recyclerView = findViewById(R.id.recyclerViewFavs);
        final FavoritesAdapter adapter = new FavoritesAdapter(FavoritesActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recViewModel = ViewModelProviders.of(this).get(RecommendationViewModel.class);
        recViewModel.getAllRecs().observe(this, new Observer<List<Recommendation>>() {
            @Override
            public void onChanged(@Nullable final List<Recommendation> recs) {
                adapter.setRecs(recs);
            }
        });
    }

    @Override
    public void onRestart() {
        super.onRestart();
        setTitle();

        RecyclerView recyclerView = findViewById(R.id.recyclerViewFavs);
        final FavoritesAdapter adapter = new FavoritesAdapter(FavoritesActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recViewModel = ViewModelProviders.of(this).get(RecommendationViewModel.class);
        // This changes the sort of the data (if user goes to settings and changes sort then goes back to favorites screen)
        recViewModel.getAllRecsSortRefreshed();
        recViewModel.getAllRecs().observe(this, new Observer<List<Recommendation>>() {
            @Override
            public void onChanged(@Nullable final List<Recommendation> recs) {
                adapter.setRecs(recs);
            }
        });

    }

    public void setTitle() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        userName = sharedPreferences.getString("userName", "Name");
        String title = (!userName.isEmpty() && !userName.equals("Name")) ? userName + "'s Favorites" : "Favorites";
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(FavoritesActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
