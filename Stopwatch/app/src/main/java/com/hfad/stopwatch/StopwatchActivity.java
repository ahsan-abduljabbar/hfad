package com.hfad.stopwatch;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class StopwatchActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Orientation

        int orientation = getResources().getConfiguration().orientation;

        Toast.makeText(this, orientation + "",Toast.LENGTH_SHORT ).show();

        if (orientation == 1) { // Portrait

            setContentView(R.layout.activity_stopwatch);

        } else { // Landscape

            setContentView(R.layout.activity_stopwatch_landscape);

        }

        //

        if (savedInstanceState != null) {

            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");

        }

        runTimer();

        //

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);

    }

    public void onClickStart (View view) {

        running = true;

    }

    public void onClickStop (View view) {

        running = false;

    }

    public void onClickReset (View view) {

        running = false;
        seconds = 0;

    }

    private void runTimer () {

        final TextView timeView = (TextView) findViewById(R.id.time_view);

        final Handler handler = new Handler();

        handler.post(new Runnable() {

            @Override

            public void run () {

            int hours   =  seconds / 3600;
            int minutes = (seconds % 3600) / 60;
            int secs    =  seconds % 60;

            String time = String.format("%02d:%02d:%02d", hours, minutes, secs);

            timeView.setText(time);

            if(running) {

                seconds++;

            }

            handler.postDelayed(this, 1000);

            }

        });

    }

    //

    public void onShowList (View view) {

        Intent intent = new Intent(this, ListActivity.class);

        startActivity(intent);

    }

    //

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stopwatch, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // API Call

    public void callApi (View view) {

        Toast.makeText(this, "API Called", Toast.LENGTH_SHORT ).show();

        new RequestTask().execute("http://www.google.com");

    }
}

class RequestTask extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... uri) {
        String responseString = null;
        try {
            URL url = new URL(uri[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK){
                // Toast.makeText(this,""+ conn.getResponseCode(), Toast.LENGTH_SHORT ).show();
                Log.i("API Called",conn.getResponseCode()+"");
            }
            else {

                // response = "FAILED"; // See documentation for more info on response handling

            }
        } catch (IOException e) {
            //TODO Handle problems..
            Log.i("API Called",e.getMessage());
        }
        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Do anything with response..
    }
}
