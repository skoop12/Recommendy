package edu.uchicago.skoop.profinal2019;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.telephony.SmsManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

// https://stackoverflow.com/questions/6298275/how-to-finish-every-activity-on-the-stack-except-the-first-in-android
// https://stackoverflow.com/questions/26651602/display-back-arrow-on-toolbar
// https://developer.android.com/training/sharing/send

public class DescriptionActivity extends AppCompatActivity {

    private TextView textNameDetail;
    private Button btnFavorites;
    private Button btnHome;
    private Button btnSave;
    private Button btnDelete;
    private ImageView imageViewDetail;
    private TextView textDescription;
    private HashMap<String, Integer> imageMap = new HashMap();
    private RecommendationViewModel recommendationViewModel;
    private String wikipedia;
    private String youtube;
    private String type;
    private int id;
    private boolean saved;
    private boolean deleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        recommendationViewModel = ViewModelProviders.of(this).get(RecommendationViewModel.class);

        textNameDetail = findViewById(R.id.textNameDetail);
        textDescription = findViewById(R.id.textDescription);
        textDescription.setMovementMethod(new ScrollingMovementMethod());
        btnFavorites = findViewById(R.id.btnFavorites);
        btnHome = findViewById(R.id.btnHome);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        imageViewDetail = findViewById(R.id.imageViewDetail);

        saved = false;
        deleted = false;

        setImageMap();

        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            setViewsFromBundle(bundle);
        }

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DescriptionActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btnFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DescriptionActivity.this, FavoritesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((id == 0 && !saved) || deleted) {
                    Recommendation addFavRec = new Recommendation(id, textNameDetail.getText().toString(), type, textDescription.getText().toString(), wikipedia, youtube);
                    recommendationViewModel.insert(addFavRec);
                    saved = true;
                    deleted = false;
                } else {
                    Toast toast = Toast.makeText(DescriptionActivity.this, R.string.already_saved, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((id != 0 && !deleted) || saved) {
                    Recommendation deleteFavRec = new Recommendation(id, textNameDetail.getText().toString(), type, textDescription.getText().toString(), wikipedia, youtube);
                    recommendationViewModel.delete(deleteFavRec);
                    deleted = true;
                    saved = false;
                } else {
                    Toast toast = Toast.makeText(DescriptionActivity.this, R.string.not_in_fav, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.rec_title);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Let's check out " + textNameDetail.getText().toString() + " together!  See more info here: " + wikipedia);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
    }

    public void setImageMap() {
        imageMap.put("movie", R.drawable.ic_movie);
        imageMap.put("show", R.drawable.ic_show);
        imageMap.put("music", R.drawable.ic_music);
        imageMap.put("podcast", R.drawable.ic_podcast);
        imageMap.put("book", R.drawable.ic_book);
        imageMap.put("author", R.drawable.ic_person);
        imageMap.put("question", R.drawable.ic_question);
    }

    public void setViewsFromBundle(Bundle bundle) {
        textNameDetail.setText(bundle.getString("name"));
        textDescription.setText(bundle.getString("description"));
        type = bundle.getString("type");
        id = (bundle.getString("id") != null) ? Integer.parseInt(bundle.getString("id")) : 0;
        wikipedia = (bundle.get("wiki") != null) ? bundle.get("wiki").toString() : "null";
        youtube = (bundle.get("youtube") != null) ? bundle.get("youtube").toString() : "null";
        int imageInt = (imageMap.containsKey(type)) ? imageMap.get(type) : imageMap.get("question");
        imageViewDetail.setImageResource(imageInt);
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
                Intent intent = new Intent(DescriptionActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }



}
