package com.aboutahsan.apps.database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Database

        this.database = openOrCreateDatabase("practice", MODE_PRIVATE, null);

        // (Create Table + Add a Record)

        database.execSQL("CREATE TABLE IF NOT EXISTS Friends (FriendName VARCHAR, Residence VARCHAR);");

        // Retrieve

        this.retrieveData();

    }

    public void onAddFriend (View view) {

        // Values

        EditText friendName = (EditText) findViewById(R.id.friendName);
        EditText address    = (EditText) findViewById(R.id.address);

        // (Create Table + Add a Record)

        this.database.execSQL("INSERT INTO Friends VALUES('" + friendName.getText() + "', '" + address.getText() + "');");

        // Reset

        friendName.setText("");
        address.setText("");

        // Retrieve

        this.retrieveData();

    }

    public void onDeleteAllRecords (View view) {

        this.database.execSQL("DELETE FROM Friends");

        // Retrieve

        this.retrieveData();

    }

    // Retrieve

    public void retrieveData () {

        Cursor resultSet = this.database.rawQuery("Select * from Friends",null);

        resultSet.moveToFirst();

        TextView tvFriends = (TextView) findViewById(R.id.friends);

        String friends = "";

        for (int counter = 1; counter <= resultSet.getCount(); counter++) {

            friends += resultSet.getString(0) + " (" + resultSet.getString(1) + ")\n";

            resultSet.moveToNext();

        }

        tvFriends.setText(friends);

    }

}