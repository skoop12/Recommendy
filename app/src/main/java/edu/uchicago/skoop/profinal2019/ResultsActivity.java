package edu.uchicago.skoop.profinal2019;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// https://stackoverflow.com/questions/9605913/how-do-i-parse-json-in-android
// https://medium.com/@peterekeneeze/parsing-remote-json-to-recyclerview-android-1ad927e96d58
// https://android--code.blogspot.com/2015/08/android-progressbar-indeterminate.html
// https://bitbucket.org/csgerber/currencies2e/src/master/

public class ResultsActivity extends AppCompatActivity {

    private RecommendationViewModel recommendationViewModel;
    private List<Recommendation> recommendations;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private final String queryURL = "https://tastedive.com/api/similar?q=";
    private final String endURL = "&info=1&k=YOUR_API_KEY_HERE";
    private String type;
    private String searchItem;
    private String finalQueryURL;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Results");

        recyclerView = findViewById(R.id.resultsRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.pb);

        recommendations = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            getExtrasFromBundle(bundle);
            new ResultsReceiverTask().execute(finalQueryURL);
        }
    }

    public void getExtrasFromBundle(Bundle bundle) {
        searchItem = bundle.getString("searchItem");
        type = bundle.getString("type");
        searchItem = searchItem.replaceAll(" ", "+");
        finalQueryURL = (type != "Any") ? queryURL + searchItem + "&type=" + type.toLowerCase() + endURL : queryURL + searchItem + endURL;
    }

    private class ResultsReceiverTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                return JsonParser.fetchJson(params[0]);
            } catch (Exception e) {
                Log.e("JSON ERROR", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            progressBar.setVisibility(View.GONE);
            try {
                if (jsonObject == null){
                    throw new JSONException("No data available.");
                }
                Log.e("JSON", jsonObject.toString());
                JSONObject jsonOutput = jsonObject.getJSONObject("Similar");
                JSONArray jsonResults = jsonOutput.getJSONArray("Results");
                Log.e("Results", jsonResults.toString());
                for (int i=0; i < jsonResults.length(); i++){
                    try{
                        JSONObject singleResult = jsonResults.getJSONObject(i);
                        String name = singleResult.getString("Name");
                        String type = singleResult.getString("Type");
                        String description = singleResult.getString("wTeaser");
                        String wikiUrl = singleResult.getString("wUrl");
                        String youtubeUrl = singleResult.getString("yUrl");
                        Recommendation rec = new Recommendation(0, name, type, description, wikiUrl, youtubeUrl);
                        recommendations.add(rec);
                    } catch (JSONException e) {
                        Log.e("SINGLE RESULT ERROR", e.getMessage());
                    }

                    adapter = new RecommendationAdapter(recommendations, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
                if (jsonResults.length() < 1) {
                    Toast toast = Toast.makeText(ResultsActivity.this, R.string.no_results_found, Toast.LENGTH_LONG);
                    toast.show();
                }
            } catch (JSONException e) {
                Log.e("DATA", e.getMessage());
                Toast toast = Toast.makeText(ResultsActivity.this, "There's been a JSON exception: " + e.getMessage(), Toast.LENGTH_LONG);
                toast.show();
                onBackPressed();
            }

        }

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
                Intent intent = new Intent(ResultsActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
