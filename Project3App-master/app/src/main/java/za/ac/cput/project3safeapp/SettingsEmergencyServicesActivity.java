package za.ac.cput.project3safeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SettingsEmergencyServicesActivity extends AppCompatActivity {
    DatabaseHelper mDatabaseHelper;
    EditText ambulanceNum, policeNum, fireNum, nWatchNum;
    private final String name = "NEIGHBOURHOOD WATCH";
    private String updatedNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_emergency_services);

        mDatabaseHelper = new DatabaseHelper(this);
        ambulanceNum = findViewById(R.id.Ambulance);
        policeNum = findViewById(R.id.Police);
        fireNum = findViewById(R.id.FireRescue);
        nWatchNum = findViewById(R.id.neighbourhoodwatch);

        Cursor amb = mDatabaseHelper.fetchAmbulanceDetails();
        Cursor pol = mDatabaseHelper.fetchPoliceDetails();
        Cursor fir = mDatabaseHelper.fetchFireRescueDetails();
        Cursor nWa = mDatabaseHelper.fetchNeighbourhoodWatchDetails();

        ambulanceNum.setText(amb.getString(2));
        policeNum.setText(pol.getString(2));
        fireNum.setText(fir.getString(2));
        nWatchNum.setText(nWa.getString(2));

        ImageButton backBtn = (ImageButton) findViewById(R.id.btnBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        Button saveBtn = findViewById(R.id.Submit);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatedNum = nWatchNum.getText().toString();
                updateNeighbourhoodWatch(name, updatedNum);
            }
        });
    }

    public void updateNeighbourhoodWatch(String name, String number) {
        boolean insertData = mDatabaseHelper.updateNeighbourhoodWatch(name, number);
        if (insertData) {
            toastMessage("Neighbourhood Watch Successfully Updated!");
        } else {
            toastMessage("Something Went Wrong While Updating!");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}