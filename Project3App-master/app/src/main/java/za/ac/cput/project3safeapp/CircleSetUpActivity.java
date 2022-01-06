package za.ac.cput.project3safeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class CircleSetUpActivity extends AppCompatActivity {

    public static final int PICK_CONTACT = 1;
    private ImageButton btnAddContact;
    private TextView contactName;
    private TextView contactNumber;

    private static final int CONTACT_PERMISSION_CODE = 1;
    private static final int CONTACT_PICK_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_set_up);

        btnAddContact = (ImageButton) findViewById(R.id.circleAdd1);
        contactName = (TextView) findViewById(R.id.circleName1);
        contactNumber = (TextView) findViewById(R.id.circleNumber1);

        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkContactPermission()) {
                    pickContactIntent();
                }
                else {
                    requestContactPermission();
                }
            }
        });
    }

    private boolean checkContactPermission() {

        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == (PackageManager.PERMISSION_GRANTED);
    }

    private void requestContactPermission() {
        String[] permission = {Manifest.permission.READ_CONTACTS};

        ActivityCompat.requestPermissions(this, permission,CONTACT_PERMISSION_CODE);
    }

    private void pickContactIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, CONTACT_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CONTACT_PERMISSION_CODE) {
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickContactIntent();
            }
            else {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode == CONTACT_PICK_CODE) {
                contactName.setText("");
                contactNumber.setText("");

                Cursor cursor1, cursor2;

                Uri uri = data.getData();

                cursor1 = getContentResolver().query(uri,null, null, null, null);

                if(cursor1.moveToFirst()) {
                    String contactNameA = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String contactId = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID));
                    String idResults = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    int idResultsHold = Integer.parseInt(idResults);

                    contactName.append(contactNameA);

                    if(idResultsHold == 1) {
                        cursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                                null,
                                null
                        );

                        while(cursor2.moveToNext()) {
                            String contactNumberA = cursor2.getString((cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                            contactNumber.append(contactNumberA);
                        }
                        cursor2.close();
                    }
                    cursor1.close();
                }
            }
        }
    }
}