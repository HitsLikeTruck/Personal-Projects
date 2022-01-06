package za.ac.cput.project3safeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsAccountActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    private TextView firstName, lastName, number;
    private String updatedFirstName, updatedLastName, updatedNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_account);

        firstName = findViewById(R.id.AccountEdit_FirstName);
        lastName = findViewById(R.id.AccountEdit_LastName);
        number = findViewById(R.id.AccountEdit_Phone);
        mDatabaseHelper = new DatabaseHelper(this);

        Cursor cursor = mDatabaseHelper.fetchUserDetails();
        cursor.moveToFirst();
        firstName.setText(cursor.getString(1));
        lastName.setText(cursor.getString(2));
        number.setText(cursor.getString(3));

        ImageButton backBtn = (ImageButton) findViewById(R.id.btnBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        Button saveBtn = findViewById(R.id.btnAccountSave);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatedFirstName = firstName.getText().toString();
                updatedLastName = lastName.getText().toString();
                updatedNumber = number.getText().toString();

                if (firstName.length() != 0 &&
                        lastName.length() != 0 &&
                        number.length() == 10 ) {
                    updateUser(updatedFirstName, updatedLastName, updatedNumber);
                }
            }
        });
    }

    public void updateUser(String fName, String lName, String number) {
        boolean insertData = mDatabaseHelper.updateUser(fName, lName, number);
        if (insertData) {
            toastMessage("Data Successfully Updated!");
        } else {
            toastMessage("Something Went Wrong While Updating!");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}