package deltazero.smartcrutch.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import deltazero.smartcrutch.R;
import deltazero.smartcrutch.core.API;

public class SettingsActivity extends AppCompatActivity {

    private TextView tvPhoneInfo, tvHomeInfo, tvLanguageInfo;
    private API.Settings settings;

    private int easterEggTriggerCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Init UI
        getSupportActionBar().setTitle(getString(R.string.settings_activity_title));
        tvPhoneInfo = findViewById(R.id.settings_tv_phone_info);
        tvHomeInfo = findViewById(R.id.settings_tv_home_info);
        tvLanguageInfo = findViewById(R.id.settings_tv_language_info);

        Log.d("settings", "Language: " + Locale.getDefault().getLanguage());

        switch (Locale.getDefault().getLanguage()) {
            case "zh":
                tvLanguageInfo.setText(getString(R.string.language_zh));
                break;
            default:
                tvLanguageInfo.setText(getString(R.string.language_en));
        }

        // Get settings
        String uuid = getSharedPreferences("deltazero.smartcrutch.prefs", MODE_PRIVATE)
                        .getString("uuid", null);
        API.getSettings(this, uuid);
    }

    public void setEmergencyTel(View view) {
    }

    public void setHomeLoc(View view) {
    }

    public void setPassword(View view) {
    }

    public void setLanguage(View view) {
        easterEggTriggerCount ++;
        if (easterEggTriggerCount > 10) {
            startActivity(new Intent(this, EasterEggActivity.class));
            easterEggTriggerCount = 0;
        }
    }

    public void loadSettings(int code, String msg, API.Settings settings) {
        switch (code) {
            case 0:
                this.settings = settings;

                if (settings.phone == null)
                    tvPhoneInfo.setText(getString(R.string.clink_to_set));
                else
                    tvPhoneInfo.setText(settings.phone);

                if (settings.home == null)
                    tvHomeInfo.setText(getString(R.string.clink_to_set));
                else
                    tvHomeInfo.setText(settings.home);

                break;

            case 1:
                Toast.makeText(this, getString(R.string.error_login_expired), Toast.LENGTH_SHORT).show();
            case -1:
                Toast.makeText(this, getString(R.string.error_network) + msg, Toast.LENGTH_SHORT).show();
        }
    }
}