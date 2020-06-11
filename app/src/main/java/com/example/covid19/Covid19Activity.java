package com.example.covid19;


import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Covid19Activity extends AppCompatActivity implements LoaderCallbacks<List<CovidStats>> {

    private TextView mEmptyStateTextView;


    private static final String LOG_TAG = Covid19Activity.class.getName();


    private static final String COVID_REQUEST_URL =
            "https://api.covid19api.com/summary";


    private static final int COVID_LOADER_ID = 1;

    /** Adapter for the list of earthquakes */
    private Covid_Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        ListView ListView = (ListView) findViewById(R.id.list);
        ListView.setEmptyView(mEmptyStateTextView);

        mAdapter = new Covid_Adapter(this, new ArrayList<CovidStats>());

        ListView.setAdapter(mAdapter);

        LoaderManager loaderManager = getLoaderManager();

        loaderManager.initLoader(COVID_LOADER_ID, null, this);

    }


    @Override
    public Loader<List<CovidStats>> onCreateLoader(int i, Bundle bundle) {

        // Create a new loader for the given URL
        return new Covid_Loader(this, COVID_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<CovidStats>> loader, List<CovidStats> data) {

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No stats found."
        mEmptyStateTextView.setText(R.string.no_new_stats);


        mAdapter.clear();
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
    }


    @Override
    public void onLoaderReset(Loader<List<CovidStats>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}
