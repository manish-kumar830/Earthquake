package com.example.earthquake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2023-01-01&minmagnitude=6";

    EarthQuakeAdapter quakeAdapter;
    ProgressBar loading_indicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loading_indicator = findViewById(R.id.loading_indicator);
        EarthquakeTask earthquakeTask = new EarthquakeTask();
        earthquakeTask.execute(USGS_REQUEST_URL);

        ListView quakeListView = findViewById(R.id.list);

        quakeAdapter = new EarthQuakeAdapter(MainActivity.this, 0, new ArrayList<EarthQuake>());

        quakeListView.setAdapter(quakeAdapter);

        quakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                EarthQuake currentEarthquake = quakeAdapter.getItem(position);
                Uri uri = Uri.parse(currentEarthquake.getUrl());
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });

    }

    protected class EarthquakeTask extends AsyncTask<String,Void,List<EarthQuake>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading_indicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<EarthQuake> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            List<EarthQuake> result = null;
            try {
                result = QueryUtils.extractEarthquakeData(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<EarthQuake> earthQuakes) {
            loading_indicator.setVisibility(View.GONE);
            quakeAdapter.clear();
            if (earthQuakes!=null && !earthQuakes.isEmpty()){
                quakeAdapter.addAll(earthQuakes);
            }
        }
    }

}
