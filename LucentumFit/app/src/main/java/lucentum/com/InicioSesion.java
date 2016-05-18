package lucentum.com;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class InicioSesion extends AppCompatActivity {

    RequestQueue requestQueue;
    String loginURL = "http://46.101.84.36:80/login/";
    EditText usuario,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inicio_sesion);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        usuario = (EditText) findViewById(R.id.et_usuario);
        pass = (EditText) findViewById(R.id.et_password);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

    }

  /* public void IniciarSesion(View v) {
       SharedPreferences preferences=getSharedPreferences("usuario", Context.MODE_PRIVATE);
       SharedPreferences.Editor editor= preferences.edit();
       editor.putString("usu",usuario.getText().toString());
       editor.commit();

       MostrarToast("Bienvenido " + usuario.getText().toString());
        cambioActivity();
    }*/
    public void IniciarSesion(View v)
    {

            StringRequest request = new StringRequest(Request.Method.POST, loginURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    SharedPreferences preferences=getSharedPreferences("usuario", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor= preferences.edit();
                    editor.putString("usu",usuario.getText().toString());
                    editor.commit();

                    MostrarToast("Bienvenido " + usuario.getText().toString());

                    cambioActivity();
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("RESPONSE NO");
                    MostrarToast("Credenciales indorrectas.");

                }
            }) {


                @Override
                protected Map<String,String> getParams() throws
                        AuthFailureError {
                    Map<String, String> parametros = new HashMap<String, String>();
                    parametros.put("Content-Type", "application/json; charset=utf-8");//IMPORTANTÍSIMA //por ésta linea me daba error 404
                    //parametros.put("Nombre", "Admin");
                    parametros.put("login", usuario.getText().toString());
                    parametros.put("password", pass.getText().toString());
                    //parametros.put("Correo", email.getText().toString());
                    // parametros.put("Ciudad", "Alicante");
                    // parametros.put("Pais", "España");
                    // parametros.put("Edad", "22");
                    // parametros.put("Altura", "174");
                    // parametros.put("Peso", "70");
                    // parametros.put("Imagen", "null");
                    return parametros;
                    //return toJSON();
                }
            };
            requestQueue.add(request);
    }


    public void Registro(View v)
    {
        Intent intent = new Intent(this,Registro.class);
        startActivity(intent);
    }

    public void MostrarToast(String mensaje)
    {
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void cambioActivity()
    {
        Intent intent = new Intent(this,Dashboard.class);
        startActivity(intent);
        System.out.println("ERROR1");
        //finish();
    }
}
