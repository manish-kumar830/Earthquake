package com.example.earthquake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.graphics.drawable.GradientDrawable;

import androidx.core.content.ContextCompat;

public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake> {

    private static final String LOCATION_SEPARATOR = " of ";

    public EarthQuakeAdapter(Context context, int resource, ArrayList<EarthQuake> quakes) {
        super(context, 0, quakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View quakeListView = convertView;
        if (quakeListView == null) {
            quakeListView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        EarthQuake earthQuake = getItem(position);

        double dMag = earthQuake.getMagnitude();

        String magnitude = formattedMagnitude(dMag);

        TextView mag = quakeListView.findViewById(R.id.magnitude);

        GradientDrawable magnitudeCircle = (GradientDrawable) mag.getBackground();
        int color = getColorResource(dMag);
        magnitudeCircle.setColor(color);

        mag.setText(magnitude);

        String offSetLocation;
        String originalLocation;

        String fullPlace = earthQuake.getPlace();
        if (fullPlace.contains(LOCATION_SEPARATOR)) {
            String[] fullLocation = fullPlace.split(LOCATION_SEPARATOR);
            offSetLocation = fullLocation[0] + LOCATION_SEPARATOR;
            originalLocation = fullLocation[1];
        } else {
            offSetLocation = "Near The";
            originalLocation = fullPlace;
        }

        TextView offSet = quakeListView.findViewById(R.id.location_offset);
        offSet.setText(offSetLocation);


        TextView place = quakeListView.findViewById(R.id.location_primary);
        place.setText(originalLocation);

        long timeInMilliSecond = earthQuake.getTime();

        TextView date = quakeListView.findViewById(R.id.date);
        date.setText(dateFormat(timeInMilliSecond));

        TextView time = quakeListView.findViewById(R.id.time);
        time.setText(timeFormat(timeInMilliSecond));

        return quakeListView;
    }

    private String dateFormat(long milliSecondTime) {
        Date dateObject = new Date(milliSecondTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        String date = dateFormat.format(dateObject);
        return date;
    }

    private String timeFormat(long milliSecondTime) {
        Date dateObject = new Date(milliSecondTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        String time = dateFormat.format(dateObject);
        return time;
    }

    private int getColorResource(double magValue) {
        int colorResourceId;
        int mMagnitude = (int) Math.floor(magValue);
        switch (mMagnitude) {
            case 0:
            case 1:
                colorResourceId = R.color.magnitude1;
                break;
            case 2:
                colorResourceId = R.color.magnitude2;
                break;
            case 3:
                colorResourceId = R.color.magnitude3;
                break;
            case 4:
                colorResourceId = R.color.magnitude4;
                break;
            case 5:
                colorResourceId = R.color.magnitude5;
                break;
            case 6:
                colorResourceId = R.color.magnitude6;
                break;
            case 7:
                colorResourceId = R.color.magnitude7;
                break;
            case 8:
                colorResourceId = R.color.magnitude8;
                break;
            case 9:
                colorResourceId = R.color.magnitude9;
                break;
            default:
                colorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), colorResourceId);
    }

        private String formattedMagnitude ( double Magnitude){
            DecimalFormat formatMag = new DecimalFormat("0.0");
            return formatMag.format(Magnitude);
        }

}
