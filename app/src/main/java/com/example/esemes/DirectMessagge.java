package com.example.esemes;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class DirectMessagge extends AppCompatActivity {

    EditText phoneNumber;
    EditText messageContent;
    Button dBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_messagge);

        phoneNumber = findViewById(R.id.d_message_number);
        messageContent = findViewById(R.id.d_message);
        dBtn = findViewById(R.id.d_sendbutton);

        dBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendWithSmsManager();
            }
        });


    }

    private void sendWithSmsManager() {
        if (checkPermission(Manifest.permission.SEND_SMS)) {
            String destinationAddress = phoneNumber.getText().toString();
            String text = messageContent.getText().toString();

            if (!destinationAddress.equals("") && !text.equals("")) {
                SmsManager smsManager = SmsManager.getDefault();

                smsManager.sendTextMessage(destinationAddress, null, text, null, null);

                Toast.makeText(this, "SMS send", Toast.LENGTH_SHORT).show();
                Log.v("sms123", "SMS send");
            } else {
                Toast.makeText(getApplicationContext(), "Permissions denied", Toast.LENGTH_SHORT).show();
                Log.v("sms123", "Permission denied");
            }
        }
    }

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {

                } else {

                }
            });

    private boolean checkPermission(String sendSms) {

        if(ContextCompat.checkSelfPermission(this, sendSms) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestPermissionLauncher.launch(
                    sendSms);
            return false;
        }

    }
}