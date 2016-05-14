package lucentum.com;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class Perfil extends AppCompatActivity {

    ImageView fotoPerfil;
    RequestQueue requestQueue;
    String usuarioURL = "http://46.101.84.36:3000/usuarios/";
    TextView inUsuarioPerfil;
    TextView inNombre;
    TextView inEdad;
    TextView inSexo;
    TextView inAltura;
    TextView inPeso;
    TextView inCiudad;
    TextView inPais;
    TextView inCorreo;
    TextView inUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        fotoPerfil = (ImageView) findViewById(R.id.iv_fotoPerfil);

        //extraemos el drawable en un bitmap
       /* Drawable originalDrawable = getResources().getDrawable(R.drawable.faro);
        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();

        //creamos el drawable redondeado
        RoundedBitmapDrawable roundedDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);

        //asignamos el CornerRadius
        roundedDrawable.setCornerRadius(originalBitmap.getHeight())*/

        fotoPerfil.setImageBitmap(getRoundedCornerBitmap(getResources().getDrawable(R.drawable.faro), true));

        SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        String usuario = preferences.getString("usu", "null");
        System.out.println("Usuario: "+usuario+"  "+usuario.length());
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        if (!usuario.equals("null")) {

            inUsuarioPerfil = (TextView) findViewById(R.id.tv_inUsuario_perfil);
            inUsuarioPerfil.setText(usuario);
            inNombre = (TextView) findViewById(R.id.tv_inNombre);
            inEdad = (TextView) findViewById(R.id.tv_inEdad);
            inSexo = (TextView) findViewById(R.id.tv_inSexo);
            inAltura = (TextView) findViewById(R.id.tv_inAltura);
            inPeso = (TextView) findViewById(R.id.tv_inPeso);
            inCiudad = (TextView) findViewById(R.id.tv_inCiudad);
            inPais = (TextView) findViewById(R.id.tv_inPais);
            inCorreo = (TextView) findViewById(R.id.tv_inCorreo);
            inUsuario = (TextView) findViewById(R.id.tv_inUsuario);
            inUsuario.setText(usuario);
            cargarDatosUsuario(usuario);
        }

        //fotoPerfil.setImageDrawable(roundedDrawable);
    }

    public static Bitmap getRoundedCornerBitmap(Drawable drawable, boolean square) {
        int width = 0;
        int height = 0;

        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

        if (square) {
            if (bitmap.getWidth() < bitmap.getHeight()) {
                width = bitmap.getWidth();
                height = bitmap.getWidth();
            } else {
                width = bitmap.getHeight();
                height = bitmap.getHeight();
            }
        } else {
            height = bitmap.getHeight();
            width = bitmap.getWidth();
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);
        final float roundPx = 90;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


    public void cargarDatosUsuario(String usuario) {
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
                    String parametro = jObject.getString("Nombre");
                    if(!parametro.equals("null"))
                    inNombre.setText(parametro);

                    parametro = jObject.getString("Edad");
                    if(!parametro.equals("null"))
                    inEdad.setText(parametro);

                   // parametro = jObject.getString("Sexo");
                   // if(!parametro.equals("null"))
                   // inSexo.setText(parametro);

                    parametro = jObject.getString("Altura");
                    if(!parametro.equals("null"))
                    inAltura.setText(parametro);

                    parametro = jObject.getString("Peso");
                    if(!parametro.equals("null"))
                    inPeso.setText(parametro);

                    parametro = jObject.getString("Ciudad");
                    if(!parametro.equals("null"))
                    inCiudad.setText(parametro);

                    parametro = jObject.getString("Pais");
                    if(!parametro.equals("null"))
                    inPais.setText(parametro);

                    parametro = jObject.getString("Correo");
                    //if(!parametro.equals("null"))
                    inCorreo.setText(parametro);


                    //usu.setText(projectname, TextView.BufferType.EDITABLE);
                    System.out.println("RESPONSE "+parametro);
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
        requestQueue.add(request);
    }



    public void MostrarToast(String mensaje)
    {
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        toast.show();
    }



    public void EditarPerfil(View v){

        Intent intent = new Intent(this, EditarPerfil.class);
        startActivity(intent);

    }
}
