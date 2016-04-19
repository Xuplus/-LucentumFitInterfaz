package lucentum.com;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

public class InicioSesion extends AppCompatActivity {

    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inicio_sesion);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);



    }
}
