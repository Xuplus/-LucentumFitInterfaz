package lucentum.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class InicioSesion extends AppCompatActivity {



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
