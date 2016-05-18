package lucentum.com;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Rutas extends AppCompatActivity {

    ListaRutas lista;
    ListView listview;
    RequestQueue requestQueue;
    String rutasURL = "http://46.101.84.36/Rutas"; //url de las rutas en la API
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

    public void verDetalles(View v)
    {
    }

    public void MostrarToast(String mensaje)
    {
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        toast.show();
    }
}
