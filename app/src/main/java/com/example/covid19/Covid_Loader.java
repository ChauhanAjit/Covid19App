package com.example.covid19;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class Covid_Loader extends AsyncTaskLoader<List<CovidStats>> {


    private static final String LOG_TAG = Covid_Loader.class.getName();


    private String mUrl;


    public Covid_Loader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Override
    public List<CovidStats> loadInBackground() {
        if (mUrl == null) {
            return null;
        }


        List<CovidStats> CovidStatss = Query.fetchCovidStatsData(mUrl);
        return CovidStatss;
    }
}
