package lucentum.com;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

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

public class Registro extends AppCompatActivity {

    RequestQueue requestQueue;
    String registroURL = "http://alacantfit.herokuapp.com/usuarios/";
    EditText usu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        usu = (EditText)findViewById(R.id.editText3);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, registroURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("RESPONSE NO");
            }
        }) {


            /*protected String toJSON(){

                Usuario obj = new Usuario("Admin", "admin", "admin");
                JSONObject jsonObject= new JSONObject();
                try {
                    jsonObject.put("id", obj.getNombre());
                    jsonObject.put("nombre", obj.getUsuario());
                    jsonObject.put("precio", obj.getPass());

                    System.out.println("RESPONSE " + jsonObject.toString());
                    return jsonObject.toString();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println("RESPONSE ERROR");
                    return "";
                }

            }*/



           @Override
            protected Map<String,String> getParams() throws
                    AuthFailureError {
               Map<String, String> parametros = new HashMap<String, String>();
               parametros.put("Content-Type", "application/json; charset=utf-8");/*IMPORTANTÍSIMA*/ //por ésta linea me daba error 404
                parametros.put("Nombre", "Admin");
                parametros.put("Usuario", "admin");
                parametros.put("Pass", "admin");
                parametros.put("Correo", "admin@gmail.com");
                parametros.put("Ciudad", "Alicante");
                parametros.put("Pais", "España");
                parametros.put("Edad", "22");
                parametros.put("Altura", "174");
                parametros.put("Peso", "70");
                parametros.put("Imagen", "null");
                return parametros;
                //return toJSON();
            }
        };
        requestQueue.add(request);

       /* StringRequest request = new StringRequest(Request.Method.GET, registroURL, new Response.Listener<String>() {
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
                    String projectname = jObject.getString("Usuario");

                    usu.setText(projectname, TextView.BufferType.EDITABLE);
                    System.out.println("RESPONSE "+projectname);
                    //usu.setText("hola");

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
        requestQueue.add(request);*/
    }


}
