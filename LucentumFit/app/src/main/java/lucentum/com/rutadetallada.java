package lucentum.com;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class rutadetallada extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutadetallada);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        KmlLayer layer = null;

        try {
            layer = new KmlLayer(mMap, R.raw.sierraaitana, getApplicationContext());//pedir el fichero a servidor
            layer.addLayerToMap();

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //el inicio y meta de coordenadas son al reves que las que hay en el fichero de coordenadas
        //LatLng inicio = new LatLng(38.343962,-0.481135);
        //LatLng meta = new LatLng(38.348689,-0.472549); //esta es de alicante playa

        //LatLng inicio = new LatLng(38.345943,-0.480482);//esta es de monforte alicante
        //LatLng meta = new LatLng(38.380696,-0.730359);

        LatLng inicio = new LatLng(38.657537,-0.24194);//esta es de sierra de aitana
        LatLng meta = new LatLng(38.658036,-0.241692);

        mMap.addMarker(new MarkerOptions().position(inicio).title("INICIO"));
        mMap.addMarker(new MarkerOptions().position(meta).title("META"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(inicio));
    }
}
