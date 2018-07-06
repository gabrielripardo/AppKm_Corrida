package folderapp.bar;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
/*
Problema de permissão do localizador
https://stackoverflow.com/questions/38431587/error-client-must-have-access-coarse-location-or-access-fine-location?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
 */
public class GPS extends AsyncTask<Void, Void, Void> {
    public Context context;
    protected Thread threadCorrida;
    protected FusedLocationProviderClient mFusedLocationClient;
    protected LocationCallback mLocationCallback;
 //   private Button btnUpdate, btnDelete;
  //  private TextView tVLocation, tVMetros;
    protected String locAnterior;
    protected LocationRequest mLocationRequest;
    protected double totalKms;
    protected double currentKm;

    int cont = 0;
    double startLati = 0;
    double startLong = 0;

    public GPS(Context context){
        this.context = context;
        totalKms = 0;
        currentKm = 0;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        createLocationRequest();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        }
    }

    public boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ) {//Can add more as per requirement

            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
            return true;
        }
        return false;
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(200);
        mLocationRequest.setFastestInterval(100);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void startLocationUpdates() {
        //CallBack
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                null /* Looper */);
    }

    protected void stopLocationUpdate(){
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
       // tVMetros.setText("000");
    }
    private double calcDistance(double lat_inicial, double long_inicial, double lat_final, double long_final){
        double d2r = 0.017453292519943295769236;

        double dlong = (long_final - long_inicial) * d2r;
        double dlat = (lat_final - lat_inicial) * d2r;

        double temp_sin = sin(dlat/2.0);
        double temp_cos = cos(lat_inicial * d2r);
        double temp_sin2 = sin(dlong/2.0);

        double a = (temp_sin * temp_sin) + (temp_cos * temp_cos) * (temp_sin2 * temp_sin2);
        double c = 2.0 * atan2(sqrt(a), sqrt(1.0 - a));

        this.currentKm = 6368.1 * c;

        return (6368.1 * c);
    }

    protected double someDistances(Location location){
        if(cont==0) {
            startLati = location.getLatitude();
            startLong = location.getLongitude();
            cont++;
        }else {
            double endLati = location.getLatitude();
            double endLong = location.getLongitude();
            cont=0;
            totalKms = totalKms + calcDistance(startLati, startLong, endLati, endLong);
         //   tVMetros.setText(String.valueOf(totalMetters));
            Log.i("Contador", " cont = " + cont);
        }
        return totalKms;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        return null;
    }
}
