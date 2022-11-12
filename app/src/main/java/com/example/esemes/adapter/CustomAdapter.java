package com.example.esemes.adapter;

import static com.example.esemes.R.drawable.ic_action_message;
import static com.example.esemes.R.drawable.ic_action_name;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.esemes.ContactMessage;
import com.example.esemes.FeedReaderContract;
import com.example.esemes.FeedReaderDBHelper;

import com.example.esemes.MainActivity;
import com.example.esemes.R;
import com.example.esemes.data.DataModel;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener{
    private ArrayList<DataModel> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView txtNazwa;
        TextView txtNumer;
        Button btnDelete;
        Button btnMessage;
    }

    public CustomAdapter(ArrayList<DataModel> data, Context context){

        super(context, R.layout.list_view_items, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public void onClick(View view) {

        int position = (Integer) view.getTag();
        Object object = getItem(position);
        DataModel dataModel = (DataModel) object;

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        DataModel dataModel = getItem(position);

        ViewHolder viewHolder;
        final View result;

        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_view_items, parent, false);
            viewHolder.txtNazwa = (TextView) convertView.findViewById(R.id.nazwa);
            viewHolder.txtNumer = (TextView) convertView.findViewById(R.id.numer);
            viewHolder.btnDelete = (Button) convertView.findViewById(R.id.deleteBtn);
            viewHolder.btnMessage = (Button) convertView.findViewById(R.id.messageBtn);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String numer = (String) viewHolder.txtNumer.getText();

                Toast.makeText(mContext, "Usunięto: " + numer, Toast.LENGTH_SHORT).show();

                FeedReaderDBHelper dbHelper = new FeedReaderDBHelper(getContext());

                SQLiteDatabase dbw = dbHelper.getWritableDatabase();

                String[] selectionArgs = {viewHolder.txtNumer.getText().toString()};

                int rowsDeleted = dbw.delete("KONTAKTY", "numer=?", selectionArgs);

                Intent myIntent = new Intent(getContext(), MainActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(myIntent);
            }
        });

        viewHolder.btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getContext(), ContactMessage.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                myIntent.putExtra("key", viewHolder.txtNumer.getText().toString());
                getContext().startActivity(myIntent);
            }
        });

        viewHolder.txtNazwa.setText(dataModel.getNazwa());
        viewHolder.txtNumer.setText(dataModel.getNumer());
        viewHolder.btnDelete.setBackgroundResource(ic_action_name);
        viewHolder.btnMessage.setBackgroundResource(ic_action_message);
        viewHolder.btnMessage.setTag(position);

        return convertView;
    }




}
