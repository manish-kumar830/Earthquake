package com.example.earthquake;

public class EarthQuake {

    private double mMagnitude;
    private String mPlace;
    private long mTimeInMilliSeconds;
    private String mUrl;

    public EarthQuake(double magnitude, String place, long timeInMilliSeconds, String url){

        mMagnitude = magnitude;
        mPlace = place;
        mTimeInMilliSeconds = timeInMilliSeconds;
        mUrl = url;

    }


    public double getMagnitude() {
        return mMagnitude;
    }

    public String getPlace() {
        return mPlace;
    }

    public String getUrl() {
        return mUrl;
    }

    public long getTime() {
        return mTimeInMilliSeconds;
    }


}
