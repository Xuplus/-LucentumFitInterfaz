package lucentum.com;

import android.Manifest;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class CrearRuta extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private long updateTime = 10000;
    private GoogleMap mMap;
    private List<Location> listaPosiciones;
    private Location miPosicion;
    private LocationManager locManager;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;
    private boolean boolUpdate = true;
    private Updater updater;
    private boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Main empieza");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_ruta);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                        //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1000 * 5)        //cada 5 segundos actualiza
                .setFastestInterval(1 * 1000);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        System.out.println("Main finaliza");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Now lets connect to the API
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");

        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    /*private Runnable update = new Runnable() {
        @Override
        public void run() {

            System.out.println("update empieza");
            while (boolUpdate){
                Handler myHandler = new Handler();
                Context context = getApplicationContext();
                CharSequence text = "Cargando posicion...";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                //myHandler.postDelayed(mMyRunnable, 1000);//Message will be delivered in 1 second.
                myHandler.postDelayed(mMyRunnable,10000);
            }
            System.out.println("update finaliza");
        }
    };

    //Here's a runnable/handler combo
    private Runnable mMyRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            //Change state here
            System.out.println("runnable empieza");
            miPosicion = getLastKnownLocation();
            listaPosiciones.add(miPosicion);
            Context context = getApplicationContext();
            CharSequence text = "Posición guardada:\n" + miPosicion.toString();
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            System.out.println("runnable finaliza");
        }
    };*/

    /*public Location getMiPosicion(){
        //miPosicion = mLocationRequest.
        return miPosicion;
    }*/

    private Location getLastKnownLocation() {
        System.out.println("getlastlocation empieza");
        locManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                System.out.println("ERROR EN LOCALIZACION");
                return null;
            }
            Location l = locManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        System.out.println("getlastlocation finaliza");
        return bestLocation;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        System.out.println("onMapReady empieza");
        mMap = googleMap;

        //miPosicion = getLastKnownLocation();
        updater = new Updater();
        updater.execute();
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        System.out.println("onMapReady finaliza");
    }

    @Override
    public void onConnected(Bundle bundle) {
        System.out.println(" @@@@@ onConnected empieza");
        connected = true;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);//el ultimo numero es 0 o 1
        } else {
            // permission has been granted, continue as usual
            miPosicion = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }

        if (miPosicion == null) {

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = miPosicion.getLatitude();
            currentLongitude = miPosicion.getLongitude();
            Log.d("POSICION",miPosicion.toString());
            newPosition();
        }
        System.out.println(" @@@@@ onConnected finaliza");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("LOC", "location changed!");
        miPosicion = location;
        newPosition();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        System.out.println(" @@@@@ onConnectionFailed empieza");
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                    /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
                /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
        System.out.println(" @@@@@ onConnectionFailed termina");
    }

    private class Updater extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            while(boolUpdate){
                //miPosicion = getLastKnownLocation();
                if (connected){
                    //newPosition();
                    listaPosiciones.add(miPosicion);
                }

                try {
                    Thread.sleep(updateTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    private void newPosition() {
        LatLng pos = new LatLng(miPosicion.getLatitude(), miPosicion.getLongitude());
        mMap.addMarker(new MarkerOptions().position(pos));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
    }
}