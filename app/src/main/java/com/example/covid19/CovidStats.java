package com.example.covid19;

public class CovidStats {

private String countryName;
private int totalConfirmed;
private int totalRecovered;

private int totalDeath;


public CovidStats(String cN,int tC,int tR,int tD)
{
    countryName=cN;
    totalConfirmed=tC;
    totalRecovered=tR;
    totalDeath=tD;

}

    public String getCountryName() {
        return countryName;
    }

    public int getTotalConfirmed() {
        return totalConfirmed;
    }

    public int getTotalRecovered() {
        return totalRecovered;
    }


    public int getTotalDeath() {
        return totalDeath;
    }


}
