package lucentum.com;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PrimerInicio extends AppCompatActivity {

    RequestQueue requestQueue;
    String editarPerfilURL = "http://46.101.84.36/usuarios/sinPassCorreo/";
    String usuarioURL = "http://46.101.84.36/usuarios/";


    EditText inNombre;
    EditText inCiudad;
    EditText inPais;
    EditText inEdad;
    EditText inAltura;
    EditText inPeso;
   // EditText inSexo;


    String usuario;
    String pass;
    String correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primer_inicio);
        EditText nombre = (EditText)findViewById(R.id.et_inNombre);

        SharedPreferences preferences=getSharedPreferences("inicio", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putString("primer", "false");
        editor.commit();

        SharedPreferences pre = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        usuario = pre.getString("usu", "null");

        if (!usuario.equals("null")) {

            inNombre = (EditText) findViewById(R.id.et_inNombre);
            inEdad = (EditText) findViewById(R.id.et_inEdad);
            //inSexo = (EditText) findViewById(R.id.et_inSexo);
            inAltura = (EditText) findViewById(R.id.et_inAltura);
            inPeso = (EditText) findViewById(R.id.et_inPeso);
            inCiudad = (EditText) findViewById(R.id.et_inCiudad);
            inPais = (EditText) findViewById(R.id.et_inPais);
           // inCorreo = (TextView) findViewById(R.id.tv_inCorreo);
           // inUsuario = (TextView) findViewById(R.id.tv_inUsuario);
           // inUsuario.setText(usuario);
           // cargarDatosUsuario(usuario);
        }

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //Recupero el correo y la pass, para poder enviarlos de nuevo al editar
        //getPerfil(usuario);

    }

    public void CambioDashboard(View v){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
        finish();
    }

    public void ModificaPerfil(View v){



        StringRequest request = new StringRequest(Request.Method.PUT, editarPerfilURL+usuario, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                /*SharedPreferences preferences=getSharedPreferences("usuario", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= preferences.edit();
                editor.putString("primerInicio","true");
                editor.commit();*/

                MostrarToast("Perfil editado");
               // cambioActivity();
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("RESPONSE NO"+ error.toString());
                MostrarToast("No se ha podido editar to perfil "+error.toString());

            }
        }) {

            @Override
            protected Map<String,String> getParams() throws
                    AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                    parametros.put("Content-Type", "application/json; charset=utf-8");/*IMPORTANTÍSIMA*/ //por ésta linea me daba error 404
                    String sub = inNombre.getText().toString();
                    if(!sub.equals("")) {
                        parametros.put("Nombre", sub);
                    }else{
                        parametros.put("Nombre","");
                    }


                parametros.put("Usuario", usuario);


                sub = inCiudad.getText().toString();
                if(!sub.equals("")) {
                    parametros.put("Ciudad", sub);
                }else{
                    parametros.put("Ciudad","");
                }

                sub = inPais.getText().toString();
                if(!sub.equals("")) {
                    parametros.put("Pais", sub);
                }else{
                    parametros.put("Pais","");
                }

                    sub = inEdad.getText().toString();
                    if(!sub.equals("")) {
                        parametros.put("Edad", sub);
                    }else{
                        parametros.put("Edad","");
                    }

                sub = inAltura.getText().toString();
                if(!sub.equals("")) {
                    parametros.put("Altura", sub);
                }else{
                    parametros.put("Altura","");
                }

                sub = inPeso.getText().toString();
                if(!sub.equals("")) {
                    parametros.put("Peso", sub);
                }else{
                    parametros.put("Peso","");
                }



                parametros.put("Genero","1");


                parametros.put("Imagen", "");

               /* parametros.put("Nombre","Jose");
                parametros.put("Usuario",usuario);
                parametros.put("Ciudad","Alicante");
                parametros.put("Pais","España");
                parametros.put("Edad", "22");
                parametros.put("Altura", "184");
                parametros.put("Peso", "80");
                parametros.put("Genero","1");
                parametros.put("Imagen","");*/


                return parametros;
                //return toJSON();
            }
        };
        //System.out.println("REQ: "+request);
        requestQueue.add(request);
    }


    public void getPerfil(String u){
        StringRequest request = new StringRequest(Request.Method.GET, usuarioURL+usuario, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Me devuelve JSON");
                System.out.println("RESPONSE "+response);
                JSONObject jObject  = null; // json
                try {
                    jObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //JSONObject data = jObject.getJSONObject("data"); // get data object
                try {

                    correo = jObject.getString("Correo");
                    pass = jObject.getString("Pass");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
               MostrarToast("Error cogiendo datos del usuario.");
                CambioDashboardE();
            }

        }) {



        };
        requestQueue.add(request);
    }

    public void CambioDashboardE() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
        finish();
    }

    public void MostrarToast(String mensaje)
    {
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        toast.show();
    }

}
