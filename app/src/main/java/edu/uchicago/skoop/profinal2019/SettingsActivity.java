package edu.uchicago.skoop.profinal2019;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Switch;

// https://developer.android.com/guide/topics/ui/settings/use-saved-values

public class SettingsActivity extends AppCompatActivity {

    public EditText editTextNameSetting;
    public Switch switchSortTitle;
    public Switch switchSortType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.new_app_name);

        editTextNameSetting = findViewById(R.id.editTextNameSetting);
        switchSortTitle = findViewById(R.id.switchSortTitle);
        switchSortType = findViewById(R.id.switchSortType);

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        String userName = sharedPreferences.getString("userName", "Name");
        String editTextDisplay = (userName.isEmpty()) ? "Name" : userName;
        editTextNameSetting.setText(editTextDisplay);
        Boolean sortTitle = sharedPreferences.getBoolean("sortTitle", false);
        switchSortTitle.setChecked(sortTitle);
        Boolean sortType = sharedPreferences.getBoolean("sortType", false);
        switchSortType.setChecked(sortType);

    }

    public void saveUserPreferences() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", editTextNameSetting.getText().toString());
        editor.putBoolean("sortTitle", switchSortTitle.isChecked());
        editor.putBoolean("sortType", switchSortType.isChecked());
        editor.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                saveUserPreferences();
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
