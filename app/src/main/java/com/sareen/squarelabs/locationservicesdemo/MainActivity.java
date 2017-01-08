package com.sareen.squarelabs.locationservicesdemo;

import android.location.Location;
import android.os.Bundle;
import android.renderscript.Double2;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
{
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private TextView mLatitudeText;
    private TextView mLongitudeText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLatitudeText = (TextView)findViewById(R.id.latitude_text);
        mLongitudeText = (TextView)findViewById(R.id.longitude_text);

        buildGoogleApiClient();


    }

    private void buildGoogleApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .build();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        // Connect the Client
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop()
    {
        // disconnect the client
        super.onStop();
        if(mGoogleApiClient.isConnected())
        {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        Log.i(LOG_TAG, "onConnected");
        // Provides a simple way of getting a device location and is well suited for
        // application that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.

        //TODO Add runtime permission
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation
                (mGoogleApiClient);
        if(mLastLocation != null)
        {
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        }
        else
        {
            Log.e(LOG_TAG, "mLastLocation is NULL");
        }

    }

    @Override
    public void onConnectionSuspended(int i)
    {
        Log.i(LOG_TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        Log.i(LOG_TAG, "Connection failed: " + connectionResult.getErrorCode());
    }
}
