package lucentum.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class RutaYEmpezar extends AppCompatActivity implements View.OnClickListener{

    private Button volver,empezar;

    private String nombre = "";
    private String km = "";
    private String descargas = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta_yempezar);


        //poner los campos
        ImageView imagenruta = (ImageView) this.findViewById(R.id.imageView7);
        TextView textnom = (TextView) this.findViewById(R.id.aquiNombre);
        TextView textkm = (TextView) this.findViewById(R.id.aquiKm);
        TextView textDesca = (TextView) this.findViewById(R.id.aquiDesca);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            nombre = extras.getString("nombre");
            km = extras.getString("km");
            descargas = extras.getString("descargas");
        }

        //cargar nombre
        textnom.setText(nombre);

        //cargar km
        textkm.setText(km);

        //cargar descargas
        textDesca.setText(descargas);

        //cargar imagen
        //Picasso.with(this.getApplicationContext()).load(url1).resize(192, 192).centerInside().into(imagenruta);

        volver = (Button) this.findViewById(R.id.button3);
        empezar = (Button) this.findViewById(R.id.button4);


        volver.setOnClickListener(this);
        empezar.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button3://empezar

                /*Bundle bundle = new Bundle();
                bundle.putString("nombre", googlenombre);
                bundle.putString("url", googleurl);
                PerfilFragment fragInfo = new PerfilFragment();
                fragInfo.setArguments(bundle);
                fn.beginTransaction().replace(R.id.content_frame, fragInfo).commit();*/

                startActivity(new Intent(this, rutadetallada.class));
                break;

            case R.id.button4://volver
                startActivity(new Intent(this,Rutas.class));
                break;
        }
    }
}
