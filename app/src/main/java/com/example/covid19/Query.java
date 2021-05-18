package com.example.covid19;

import android.text.TextUtils;
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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class Query {


    private static final String LOG_TAG = Query.class.getSimpleName();

    
    private Query() {
    }


    public static List<CovidStats> fetchCovidStatsData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }



        // Extract relevant fields from the JSON response and create a list of {@link CovidStats}s
        List<CovidStats> CovidStatss = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link CovidStats}s
        return CovidStatss;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the CovidStats JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link CovidStats} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<CovidStats> extractFeatureFromJson(String CovidStatsJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(CovidStatsJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding CovidStatss to
        List<CovidStats> CovidStatss = new ArrayList<>();


        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(CovidStatsJSON);


            JSONArray CovidStatsArray = baseJsonResponse.getJSONArray("Countries");

            // For each CovidStats in the CovidStatsArray, create an {@link CovidStats} object
            for (int i = 0; i < CovidStatsArray.length(); i++) {

                // Get a single CovidStats at position i within the list of CovidStatss
                JSONObject currentCovidStats = CovidStatsArray.getJSONObject(i);


                String CN=currentCovidStats.getString("Country");

                int TC=currentCovidStats.getInt("TotalConfirmed");

                int TR=currentCovidStats.getInt("TotalRecovered");

                int TD=currentCovidStats.getInt("TotalDeaths");




                CovidStats CovidStats = new CovidStats(CN,TC,TR,TD);

                // Add the new {@link CovidStats} to the list of CovidStatss.
                CovidStatss.add(CovidStats);
            }

        } catch (JSONException e) {

            Log.e("Query", "Problem parsing the CovidStats JSON results", e);
        }

        // Return the list of CovidStatss
        return CovidStatss;
    }

}
