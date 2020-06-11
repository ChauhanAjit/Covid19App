package com.example.covid19;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class Covid_Adapter extends ArrayAdapter<CovidStats> {

    private static final String LOG_TAG = Covid_Adapter.class.getSimpleName();


    public Covid_Adapter(Activity context, ArrayList<CovidStats> CovidStatss) {

        super(context, 0, CovidStatss);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.covid_list_item, parent, false);
        }


        CovidStats currentCovidStats = getItem(position);

        TextView CN=listItemView.findViewById(R.id.CountryName);
        CN.setText(currentCovidStats.getCountryName());

        TextView TC=listItemView.findViewById(R.id.TotalConfirmed);
        TC.setText("TotalConfrmed:"+Integer.toString(currentCovidStats.getTotalConfirmed()));

        TextView TR=listItemView.findViewById(R.id.TotalRecovered);
        TR.setText("TotalRecovered:"+Integer.toString(currentCovidStats.getTotalRecovered()));

        TextView TD=listItemView.findViewById(R.id.TotalDeath);
        TD.setText("TotalDeath:"+Integer.toString(currentCovidStats.getTotalDeath()));








        return listItemView;
    }

}
