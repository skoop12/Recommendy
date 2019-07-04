package edu.uchicago.skoop.profinal2019;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// https://developer.android.com/guide/topics/ui/dialogs.html
// https://stackoverflow.com/questions/45797735/how-to-save-user-input-from-alert-dialog
// https://developer.android.com/training/data-storage/shared-preferences
// https://stackoverflow.com/questions/38609262/change-text-color-of-alert-dialog
// https://stackoverflow.com/questions/7491287/android-how-to-use-sharedpreferences-in-non-activity-class
// https://medium.com/@101/android-toolbar-for-appcompatactivity-671b1d10f354
// https://google-developer-training.github.io/android-developer-fundamentals-course-practicals/en/Unit%204/92_p_adding_settings_to_an_app.html
// https://stackoverflow.com/questions/9570237/android-check-internet-connection
// https://developer.android.com/training/monitoring-device-state/connectivity-monitoring

public class MainActivity extends AppCompatActivity {

    private Button btnSearch;
    private String searchItem;
    private String searchTypeChosen;
    private Button btnFavorites;
    public static Context applicationContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        applicationContext = getApplicationContext();

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.new_app_name);

        // status bar color wasn't showing so changed min SDK to 21 to support below method
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this ,R.color.colorPrimaryDark));

        btnSearch = findViewById(R.id.btnSearch);
        btnFavorites = findViewById(R.id.btnFavorites);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildAndShowAlertDialog();
            }
        });

        btnFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
                startActivity(intent);
            }
        });

    }

    public Boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public void putExtrasAndPerformIntent() {
        Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
        intent.putExtra("searchItem", searchItem);
        intent.putExtra("type", searchTypeChosen);
        startActivity(intent);
    }

    public static Context getContextOfApplication(){
        return applicationContext;
    }

    public void buildAndShowAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogCustom);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_search, null);

        builder.setView(dialogLayout);
        final EditText editTextSearch = dialogLayout.findViewById(R.id.editTextSearch);
        final Spinner searchType = dialogLayout.findViewById(R.id.spinnerTypes);

        builder.setPositiveButton(R.string.search, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isConnected()) {
                    searchItem = editTextSearch.getText().toString();
                    searchTypeChosen = searchType.getSelectedItem().toString();
                    putExtrasAndPerformIntent();
                    dialog.dismiss();
                } else {
                    Toast toast = Toast.makeText(MainActivity.this, R.string.check_internet, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


}
