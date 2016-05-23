package lucentum.com;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.content.SharedPreferences;
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
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import com.google.maps.android.kml.KmlLayer;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrearRuta extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private String contenido;
    private String postURL = "http://46.101.84.36:80/rutas/kml";
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private long updateTime = 10000;
    private GoogleMap mMap;
    private List<Location> listaPosiciones = new ArrayList<>();
    private Location miPosicion;
    private LocationManager locManager;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;
    private boolean boolUpdate = true;
    private Updater updater;
    private boolean connected;
    private String nombreRuta = "Prueba";
    private String descripcionRuta = "Descripción de la prueba";
    private String color = "ffff0000";
    private int width = 4;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                .setFastestInterval(1000);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        findViewById(R.id.btn_finalizar).setOnClickListener(onClickListener);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_finalizar:
                    AlertDialog.Builder ad = new AlertDialog.Builder(CrearRuta.this);
                    ad.setTitle("Elige nombre para la ruta");
                    //LayoutInflater factory = LayoutInflater.from(CrearRuta.this);
                    //final View view = factory.inflate(R.layout.elige_nombre_ruta, null);
                    final EditText input = new EditText(CrearRuta.this);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);
                    ad.setView(input);
                    ad.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            nombreRuta = input.getText().toString().replaceAll(" ","");
                            //finalizar();
                            AlertDialog.Builder ad = new AlertDialog.Builder(CrearRuta.this);
                            ad = new AlertDialog.Builder(CrearRuta.this);
                            ad.setTitle("Escribe una breve descripción");
                            final EditText input = new EditText(CrearRuta.this);
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                            input.setLayoutParams(lp);
                            ad.setView(input);
                            ad.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    descripcionRuta = input.getText().toString();
                                    finalizar();
                                    dialog.dismiss();
                                }
                            });
                            ad.show();
                            dialog.dismiss();
                        }
                    });
                    ad.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    ad.show();
            }
        }
    };

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
        mMap = googleMap;

        //miPosicion = getLastKnownLocation();
        updater = new Updater();
        updater.execute();
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onConnected(Bundle bundle) {
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
    }

    private class Updater extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            while(boolUpdate){
                if (connected){
                    listaPosiciones.add(miPosicion);
                    Log.d("LISTA", listaPosiciones.toString());
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

    public void finalizar(){
        try {
            boolUpdate = false;
            contenido = CreateXMLString();
            System.out.println("Escrito");
            File fichero = new File(getFilesDir(),nombreRuta + "_rute.kml");


            String filename = fichero.getName();
            System.out.println(fichero.getAbsolutePath());
            FileOutputStream outputStream;

            enviarApi();

            try {
                outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                outputStream.write(contenido.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finish();
    }

    private void enviarApi() {
        StringRequest request = new StringRequest(Request.Method.POST, postURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("SALIDA","Correcta");
                Toast.makeText(CrearRuta.this, "Ruta enviada con éxito", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("RESPONSE NO");

            }
        }) {


            @Override
            protected Map<String,String> getParams() throws
                    AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("Content-Type", "multipart/form-data");//IMPORTANTÍSIMA //por ésta linea me daba error 404
                //parametros.put("Nombre", "Admin");
                parametros.put("Xml",contenido);
                parametros.put("NombreRuta", nombreRuta);
                SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
                String usuario = preferences.getString("usu", "null");
                parametros.put("Usuario", usuario);
                return parametros;
                //return toJSON();
            }
        };

        requestQueue.add(request);
    }

    public String CreateXMLString() throws IllegalArgumentException, IllegalStateException, IOException
    {
        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        xmlSerializer.setOutput(writer);

        //Start Document
        xmlSerializer.startDocument("UTF-8", true);
        //xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        //Open Tag <file>
        xmlSerializer.startTag("","kml");
        xmlSerializer.attribute("","xmlns","http://earth.google.com/kml/2.1");
        xmlSerializer.startTag("", "Document");

        //XmlSerializer tempXml = Xml.newSerializer();
        //StringWriter tempWriter =  new StringWriter();


        xmlSerializer.startTag("", "name");
        xmlSerializer.text(nombreRuta);
        xmlSerializer.endTag("", "name");
        xmlSerializer.startTag("", "description");
        xmlSerializer.text(descripcionRuta);
        xmlSerializer.endTag("", "description");
        xmlSerializer.startTag("", "Style");
        xmlSerializer.attribute("", "id", "blueLine");
        xmlSerializer.startTag("", "LineStyle");
        xmlSerializer.startTag("", "color");
        xmlSerializer.text(color);
        xmlSerializer.endTag("", "color");
        xmlSerializer.startTag("", "width");
        xmlSerializer.text(new String().valueOf(width));
        xmlSerializer.endTag("", "width");
        xmlSerializer.endTag("", "LineStyle");
        xmlSerializer.endTag("", "Style");
        xmlSerializer.startTag("", "Placemark");
        xmlSerializer.startTag("", "name");
        xmlSerializer.text("BlueLine");
        xmlSerializer.endTag("", "name");
        xmlSerializer.startTag("", "styleUrl");
        xmlSerializer.text("#blueLine");
        xmlSerializer.endTag("", "styleUrl");
        xmlSerializer.startTag("", "LineString");
        xmlSerializer.startTag("", "altitudeMode");
        xmlSerializer.text("relative");
        xmlSerializer.endTag("", "altitudeMode");
        xmlSerializer.startTag("", "coordinates");
        for (int i=0; i < listaPosiciones.size(); i++){ //longitud, latitud, altidud
            Location aux = listaPosiciones.get(i);
            xmlSerializer.text(aux.getLongitude() + "," + aux.getLatitude() + "," + aux.getAltitude());
        }
        xmlSerializer.endTag("","coordinates");
        xmlSerializer.endTag("","LineString");
        xmlSerializer.endTag("","Placemark");
        xmlSerializer.endTag("","Document");
        xmlSerializer.endTag("","kml");
        xmlSerializer.endDocument();

        return writer.toString();
    }

    @Override
    public void onBackPressed() {
        boolUpdate = false;
        finish();
    }
}