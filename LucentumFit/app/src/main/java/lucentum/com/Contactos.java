package lucentum.com;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class Contactos extends AppCompatActivity {

    DatosContactos datosContactos;
    //ArrayList<DatosContactos> listaContactos;
    ListaContactos lista;
    ListView listview;
    RequestQueue requestQueue;
    String amigosURL = "http://46.101.84.36/amigos/LeerAmigos/";
    String usuarioURL = "http://46.101.84.36/usuarios/";
    String anadirURL = "http://46.101.84.36/amigos/Relacion";
    String eliminarURL = "http://46.101.84.36/amigos/Romper";
    EditText nuevo;
    String usuario,viejo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);
        nuevo = (EditText) findViewById(R.id.et_nuevo_contacto);

        SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        usuario = preferences.getString("usu", "null");
        System.out.println("Usuario: "+usuario+"  "+usuario.length());
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        cargarAmigos(usuario);

        listview = (ListView)findViewById(R.id.lv_Contactos);
        lista= new ListaContactos(this,R.layout.activity_lista_contactos);
        listview.setAdapter(lista);
    }


    public void cargarAmigos(String usuario) {
        StringRequest request = new StringRequest(Request.Method.GET, amigosURL+usuario, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println("Me devuelve JSON");
                System.out.println("RESPONSE "+response);
                JSONArray contactos  = null; // json de los contactos


                try {
                    contactos = new JSONArray(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //JSONObject data = jObject.getJSONObject("data"); // get data object

                try {

                    for(int i=0;i<contactos.length();i++){

                        JSONObject e = contactos.getJSONObject(i);
                        String nombreContacto = e.getString("Amigo");
                        if(!nombreContacto.equals("null")) {
                            cargarDatosAmigos(nombreContacto);
                        }

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

    public void cargarDatosAmigos(String amigo) {
        StringRequest request = new StringRequest(Request.Method.GET, usuarioURL+amigo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println("Me devuelve JSON");
                System.out.println("RESPONSE "+response);
                JSONObject datoscontactos = null; // json de los datos de los contactos

                try {
                    datoscontactos = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //JSONObject data = jObject.getJSONObject("data"); // get data object

                try {

                    String id, name, localidad,pais, ranking;

                    id = datoscontactos.getString("Usuario");
                    name = datoscontactos.getString("Nombre");

                    localidad = datoscontactos.getString("Ciudad");
                    pais = datoscontactos.getString("Pais");
                    DatosContactos datosContactos = new DatosContactos(id,name,localidad,pais);
                    lista.add(datosContactos);

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

    public void nuevoContacto(View v)
    {

        StringRequest request = new StringRequest(Request.Method.POST, anadirURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
                String usuario = preferences.getString("usu", "null");
                System.out.println("Usuario: "+usuario+"  "+usuario.length());

                requestQueue = Volley.newRequestQueue(getApplicationContext());
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("RESPONSE NO");
                MostrarToast("No existe el contacto.");

            }
        }) {


            @Override
            protected Map<String,String> getParams() throws
                    AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("Content-Type", "application/json; charset=utf-8");
                parametros.put("Usuario", usuario);
                parametros.put("Amigo", nuevo.getText().toString());
                return parametros;
                //return toJSON();
            }
        };
        requestQueue.add(request);
    }

    public void eliminarContacto(View v) {
        int pos = listview.getPositionForView(v);
        DatosContactos contacto = (DatosContactos)lista.getItem(pos);
        viejo = contacto.getId();

        StringRequest request = new StringRequest(Request.Method.POST, eliminarURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                requestQueue = Volley.newRequestQueue(getApplicationContext());
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("RESPONSE NO");
                MostrarToast("No existe el contacto.");

            }
        }) {


            @Override
            protected Map<String,String> getParams() throws
                    AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("Content-Type", "application/json; charset=utf-8");
                parametros.put("Usuario", usuario);
                parametros.put("Amigo", viejo);
                return parametros;
                //return toJSON();
            }
        };
        requestQueue.add(request);
    }



    public void MostrarToast(String mensaje)
    {
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        toast.show();
    }

}