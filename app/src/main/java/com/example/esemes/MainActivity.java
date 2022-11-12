package com.example.esemes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Telephony;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.esemes.adapter.CustomAdapter;
import com.example.esemes.data.DataModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    CustomAdapter adapter;
    ListView listView;
    Button deleteBtn;
    ArrayList<DataModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String value = intent.getStringExtra("key");

        showAdapter();

//        receiver = new Receiver();
//        filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVER");
//
//        String action = "START";
//        final Intent intentReceiver = new Intent(this, Receiver.class);
//        intentReceiver.setAction(action);
//        startService(intent);
//
//        if (!havePermission()) {
//            havePermission();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.dodaj_numer:
                alertAdd();
                return true;

            case R.id.wiadomosc:
                Intent intent = new Intent(this, DirectMessagge.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                this.startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void alertAdd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_numer, null);

        builder.setView(inflater.inflate(R.layout.dialog_numer, null))
                .setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Dialog f = (Dialog) dialogInterface;

                        EditText editNazwa = f.findViewById(R.id.edit_nazwa);
                        String t1 = editNazwa.getText().toString();

                        EditText editNumer = f.findViewById(R.id.edit_numer);
                        String t2 = editNumer.getText().toString();

                        if(!t2.isEmpty() && !t1.isEmpty()){
                            Log.v("TAG", "t2---------> " + t2);
                            FeedReaderDBHelper dbHelper = new FeedReaderDBHelper(getApplicationContext());
                            SQLiteDatabase dbw = dbHelper.getWritableDatabase();

                            ContentValues values = new ContentValues();
                            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, t1);
                            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, t2);

                            long newRowId = dbw.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

                            Toast.makeText(getApplicationContext(), "Dodano pomyślnie", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Empty values", Toast.LENGTH_SHORT).show();
                        }

                        recreate();
                    }
                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }


    public void showAdapter(){
        listView = findViewById(R.id.listView);
        deleteBtn = findViewById(R.id.deleteBtn);

        FeedReaderDBHelper dbHelper = new FeedReaderDBHelper(getApplicationContext());

        SQLiteDatabase dbr = dbHelper.getReadableDatabase();

        list = new ArrayList<>();

        Cursor cursor = dbr.rawQuery("SELECT * FROM KONTAKTY", null);

        while (cursor.moveToNext()) {

            list.add(new DataModel(cursor.getString(1), cursor.getString(2)));
        }

        adapter = new CustomAdapter(list, getApplicationContext());

        adapter.notifyDataSetChanged();

        if(list.size() != 0){
            listView.setAdapter(adapter);
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("dodaj kontakty do swojej listy wykorzystując menu").setTitle("Brak kontaktów");

            AlertDialog dialog = builder.create();
            dialog.show();
        }


    }
//
//    public boolean havePermission() {
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (checkSelfPermission(Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED) {
//                return true;
//            } else {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);
//                return false;
//            }
//        } else {
//            return true;
//        }
//    }
//
//    @Override
//    protected void onResume(){
//        super.onResume();
//        registerReceiver(receiver, filter);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        unregisterReceiver(receiver);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
}
