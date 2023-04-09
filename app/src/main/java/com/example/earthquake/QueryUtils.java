package com.example.earthquake;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public final class QueryUtils {



    private QueryUtils() {
    }

    public static List<EarthQuake> extractEarthquakeData(String Url) throws IOException {
        URL requestedUrl = createUrl(Url);
        String jsonResponse = makeHttpRequest(requestedUrl);
        List<EarthQuake> earthQuakeList = extractFeatureFromJson(jsonResponse);
        return earthQuakeList;
    }

    private static URL createUrl(String mUrl){
        URL requestedUrl=null;
        try {
            requestedUrl = new URL(mUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return requestedUrl;
    }


    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (urlConnection!=null){
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line = reader.readLine();
        while (line!=null){
            output.append(line);
            line = reader.readLine();
        }
        return output.toString();
    }



    private static List<EarthQuake> extractFeatureFromJson(String responseJSON) {

        // Create an empty ArrayList that we can start adding earthquakes to
        List<EarthQuake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Array("features") > Object("Index") > Object("properties") > getValue("mag,place,time")

            JSONObject jsonRootObject = new JSONObject(responseJSON);
            JSONArray jsonArray = jsonRootObject.optJSONArray("features");
            for (int i=0; i<jsonArray.length(); i++){

                JSONObject jsonObject = jsonArray.optJSONObject(i);
                JSONObject properties = jsonObject.optJSONObject("properties");

                double mag = properties.optDouble("mag");
                String place = properties.optString("place");
                String url = properties.optString("url");
                long timeInMilliSecond = properties.optLong("time");

                earthquakes.add(new EarthQuake(mag,place,timeInMilliSecond,url));

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Return the list of earthquakes
        return earthquakes;
    }

}