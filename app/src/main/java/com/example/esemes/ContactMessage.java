package com.example.esemes;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ContactMessage extends AppCompatActivity {

    TextView numberView;
    EditText messageContent;
    Button sendBtn;
    String number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            number = extras.getString("key");
        }
        setContentView(R.layout.contact_message);

        numberView = findViewById(R.id.contact_number);
        numberView.setText(number);

        messageContent = findViewById(R.id.contact_message);
        sendBtn = findViewById(R.id.contact_send);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendWithSmsManager();
            }
        });

    }

    private void sendWithSmsManager() {
        if (checkPermission(Manifest.permission.SEND_SMS)) {
            String text = messageContent.getText().toString();

            if (!number.equals("") && !text.equals("")) {
                SmsManager smsManager = SmsManager.getDefault();

                smsManager.sendTextMessage(number, null, text, null, null);

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
