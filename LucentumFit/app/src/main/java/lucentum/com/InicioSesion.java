package lucentum.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class InicioSesion extends AppCompatActivity {

    RequestQueue requestQueue;
    String loginURL = "http://alacantfit.herokuapp.com/usuarios";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inicio_sesion);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);


    }

    public void Registro(View v)
    {
        Intent intent = new Intent(this,Registro.class);
        startActivity(intent);
    }
}
