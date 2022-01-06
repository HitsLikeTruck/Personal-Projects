package za.ac.cput.project3safeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ImageButton backBtn = (ImageButton) findViewById(R.id.btnBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Button settingsAccountButton = (Button) findViewById(R.id.settingsBtnAccount);
        settingsAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingsAccountActivity.class);
                startActivity(intent);
            }
        });

        Button settingsSosButton = (Button) findViewById(R.id.settingsBtnSOS);
        settingsSosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CircleSetUpActivity.class);
                startActivity(intent);
            }
        });

//        Button settingsLiveLocButton = (Button) findViewById(R.id.);
//        settingsLiveLocButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), .class);
//                startActivity(intent);
//            }
//        });

        Button settingsEmergServButton = (Button) findViewById(R.id.settingsBtnEmergencyServices);
        settingsEmergServButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingsEmergencyServicesActivity.class);
                startActivity(intent);
            }
        });

        Button settingsAboutButton = (Button) findViewById(R.id.settingsBtnAbout);
        settingsAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingsAboutActivity.class);
                startActivity(intent);
            }
        });

        Button settingsLogOutButton = (Button) findViewById(R.id.settingsBtnLogout);
        settingsLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LogoutActivity.class);
                startActivity(intent);
            }
        });
    }
}