package lucentum.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        if (extras != null) {
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
        String url = "http://46.101.84.36/rutas/info/" + nombre +"/imagenes/" + nombre; //puede que sea nombre + ".png"
        Picasso.with(this.getApplicationContext()).load(url).resize(250, 350).centerInside().into(imagenruta);

        volver = (Button) this.findViewById(R.id.button3);
        empezar = (Button) this.findViewById(R.id.button4);


        volver.setOnClickListener(this);
        empezar.setOnClickListener(this);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button3://empezar
                Intent i = new Intent(RutaYEmpezar.this,rutadetallada.class);
                i.putExtra("nombre",nombre);
                startActivity(i);
                break;

            case R.id.button4://volver
                //startActivity(new Intent(this,Rutas.class));
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
