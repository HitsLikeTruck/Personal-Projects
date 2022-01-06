package za.ac.cput.project3safeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    private EditText loginFirstName, loginLastName, loginPhoneNumber, loginPhoneNumberVerify;
    private Button btnLogin;
    private String firstName, lastName, number, numberVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginFirstName = findViewById(R.id.editTextLoginFirstName);
        loginLastName = findViewById(R.id.editTextLoginLastName);
        loginPhoneNumber = findViewById(R.id.editTextLoginNumber);
        loginPhoneNumberVerify = findViewById(R.id.editTextLoginNumberVerify);
        btnLogin = findViewById(R.id.btnLogin);
        mDatabaseHelper = new DatabaseHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = loginFirstName.getText().toString();
                lastName = loginLastName.getText().toString();
                number = loginPhoneNumber.getText().toString();
                numberVerify = loginPhoneNumberVerify.getText().toString();

                if (loginFirstName.length() != 0 &&
                        loginLastName.length() != 0 &&
                        loginPhoneNumber.length() == 10 &&
                        loginPhoneNumberVerify.length() != 0) {
                    if(number.equals(numberVerify)) {
                        setUpTables();
                        addUser(firstName, lastName, number);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        toastMessage("Phone Numbers do not match! Try again.");
                        loginPhoneNumber.setText("");
                        loginPhoneNumberVerify.setText("");
                    }
                } else {
                    toastMessage("Please enter values in the fields and a 10 digit phone number");
                }
            }
        });
    }

    public void addUser(String fName, String lName, String number) {
        boolean insertData = mDatabaseHelper.addUser(fName, lName, number);
        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something Went Wrong!");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void setUpTables() {
        mDatabaseHelper.clearTables(); // Clears the table in the event of new install
        mDatabaseHelper.fillEmergencyServices();
    }

    @Override
    public void onBackPressed() {
        // Implement for when user presses system back button
    }
}