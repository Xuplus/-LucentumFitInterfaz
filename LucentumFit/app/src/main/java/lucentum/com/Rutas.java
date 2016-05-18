package lucentum.com;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Rutas extends AppCompatActivity{

    ListaRutas lista;
    ListView listview;
    RequestQueue requestQueue;
    String rutasURL = "http://46.101.84.36/Rutas"; //url de las rutas en la API
    String rutasAmigosURL = "http://46.101.84.36/rutas/amigos/"; //url de las rutas en la API
    String rutasMayorURL = "http://46.101.84.36/Rutas/MayorMenor"; //url de las rutas en la API
    String rutasMenorURL = "http://46.101.84.36/Rutas/MenorMayor"; //url de las rutas en la API
    String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutas);

        //recoge el id del usuario que usa la app
        SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        usuario = preferences.getString("usu", "null");
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        cargarRutas();
        listview = (ListView)findViewById(R.id.lv_rutas);
        lista= new ListaRutas(this,R.layout.activity_lista_rutas);
        listview.setAdapter(lista);

    }

    public void cargarRutas() {
        StringRequest request = new StringRequest(Request.Method.GET, rutasURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONArray datosrutas = null; // json de los datos de los contactos

                try {
                    datosrutas = new JSONArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //JSONObject data = jObject.getJSONObject("data"); // get data object

                try {
                    String  name, des , kim;
                    for(int i=0;i<datosrutas.length();i++){

                        JSONObject e = datosrutas.getJSONObject(i);
                        name = e.getString("Nombre");
                        des = e.getString("VecesDescargadas");
                        kim = e.getString("Distancia");
                        DatosRutas datosRutas = new DatosRutas(name,des,kim);
                        lista.add(datosRutas);
                    }






                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("NO");
            }

        }) {



        };
        requestQueue.add(request);

    }

    public void cargarRutasGeneral(View v){
        cargarRutas();
        listview = (ListView)findViewById(R.id.lv_rutas);
        lista= new ListaRutas(this,R.layout.activity_lista_rutas);
        listview.setAdapter(lista);
    }

    public void mayormenor(View v){
        cargarMayorMenor();
        listview = (ListView)findViewById(R.id.lv_rutas);
        lista= new ListaRutas(this,R.layout.activity_lista_rutas);
        listview.setAdapter(lista);
    }

    public void cargarMayorMenor(){
        StringRequest request = new StringRequest(Request.Method.GET, rutasMayorURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONArray datosrutas = null; // json de los datos de los contactos

                try {
                    datosrutas = new JSONArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //JSONObject data = jObject.getJSONObject("data"); // get data object

                try {
                    String  name, des , kim;
                    for(int i=0;i<datosrutas.length();i++){

                        JSONObject e = datosrutas.getJSONObject(i);
                        name = e.getString("Nombre");
                        des = e.getString("VecesDescargadas");
                        kim = e.getString("Distancia");
                        MostrarToast(name);
                        DatosRutas datosRutas = new DatosRutas(name,des,kim);
                        lista.add(datosRutas);
                    }






                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("NO");
            }

        }) {



        };
        requestQueue.add(request);
    }

    public void menormayor(View v){
        cargarMenorMayor();
        listview = (ListView)findViewById(R.id.lv_rutas);
        lista= new ListaRutas(this,R.layout.activity_lista_rutas);
        listview.setAdapter(lista);
    }

    public void cargarMenorMayor(){
        StringRequest request = new StringRequest(Request.Method.GET, rutasMenorURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONArray datosrutas = null; // json de los datos de los contactos

                try {
                    datosrutas = new JSONArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //JSONObject data = jObject.getJSONObject("data"); // get data object

                try {
                    String  name, des , kim;
                    for(int i=0;i<datosrutas.length();i++){

                        JSONObject e = datosrutas.getJSONObject(i);
                        name = e.getString("Nombre");
                        des = e.getString("VecesDescargadas");
                        kim = e.getString("Distancia");
                        MostrarToast(name);
                        DatosRutas datosRutas = new DatosRutas(name,des,kim);
                        lista.add(datosRutas);
                    }






                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("NO");
            }

        }) {



        };
        requestQueue.add(request);
    }

    public void cargarRutasAmigos(View v){
        cargarRutasAmigos2();
        listview = (ListView)findViewById(R.id.lv_rutas);
        lista= new ListaRutas(this,R.layout.activity_lista_rutas);
        listview.setAdapter(lista);
    }

    public void cargarRutasAmigos2() {
        StringRequest request = new StringRequest(Request.Method.GET, rutasAmigosURL+usuario, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONArray datosrutas = null; // json de los datos de los contactos
                MostrarToast(response);

                try {
                    datosrutas = new JSONArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //JSONObject data = jObject.getJSONObject("data"); // get data object

                try {
                    String  name, des , kim;
                    for(int i=0;i<datosrutas.length();i++){

                        JSONObject e = datosrutas.getJSONObject(i);
                        name = e.getString("Nombre");
                        des = e.getString("VecesDescargadas");
                        kim = e.getString("Distancia");
                        MostrarToast(name);
                        DatosRutas datosRutas = new DatosRutas(name,des,kim);
                        lista.add(datosRutas);
                    }






                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("NO");
            }

        }) {



        };
        requestQueue.add(request);

    }


    public void verDetalles(View v)
    {
        int pos = listview.getPositionForView(v);
        DatosRutas rutas = (DatosRutas) lista.getItem(pos);
        startActivity(new Intent(this,RutaYEmpezar.class));
        Intent i = new Intent(Rutas.this,RutaYEmpezar.class);
        i.putExtra("nombre",rutas.getNombre());
        i.putExtra("descargas",rutas.getDescargas());
        i.putExtra("km",rutas.getKM());

        startActivity(i);
    }

    public void MostrarToast(String mensaje)
    {
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        toast.show();
    }


}
