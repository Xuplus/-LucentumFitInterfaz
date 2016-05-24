package lucentum.com;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.graphics.drawable.Drawable;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;
import com.android.volley.AuthFailureError;
import com.google.android.gms.wallet.wobs.LabelValue;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.lang.reflect.Field;

public class EditarPerfil extends AppCompatActivity {

    RequestQueue requestQueue;

    String loginURL = "http://46.101.84.36/usuarios/sinPassCorreo/";
    String editarPerfilURL = "http://46.101.84.36/usuarios/";

    String primerInicio;
    EditText nombre,edad,altura,peso,cuidad,pais,correo;
    //AlertDialog.Builder dialogo1;
    Button save;
    Context context=this;
    Camera camera;
    int t=0;
    private final int cons=0;
    private final int MY_PERMISSIONA=100;
    private final int PHOTO_CODE=200;
    private final int SELECT_PICTURE=300;
    ImageView img;
    private String mPath=Environment.getExternalStorageDirectory()+"/AlacantFit/Pictures/FotoPerfil.jpg";;
    String nom="";
    String usuarioURL = "http://46.101.84.36/usuarios/";
    String inNombre,inEdadparametro,inAltura,inPeso,inCiudad,inPais,inCorreo,inGenero,inPass,inImagen;
    // StringRequest request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        String usuario = preferences.getString("usu", "null");

        ((TextView)findViewById(R.id.lbl_NombreUsuario)).setText(usuario);

        save= (Button) findViewById(R.id.btn_save);
        nombre= (EditText) findViewById(R.id.tbx_Nombre);
        edad= (EditText) findViewById(R.id.tbx_Edad);
        altura= (EditText) findViewById(R.id.tbx_altura);
        peso= (EditText) findViewById(R.id.tbx_peso);
        correo= (EditText) findViewById(R.id.tbx_correo);
        pais= (EditText) findViewById(R.id.tbx_pais);
        cuidad= (EditText) findViewById(R.id.tbx_cuidad);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //SharedPreferences preferences =getSharedPreferences("usuario",Context.MODE_PRIVATE);
        nom=preferences.getString("usu", "");
        img=(ImageView) findViewById(R.id.img_foto);

        loginURL+=nom;

        TextView n= (TextView) findViewById(R.id.lbl_NombreUsuario);
        n.setText(nom);

        File fil=new File(mPath);

        if(fil.exists()){
            Bitmap b= BitmapFactory.decodeFile(mPath);
            Bitmap b1=getBitmap(b, true);
            img.setImageBitmap(b1);
        }
        usuarioURL+=nom;
        // MostrarToast(usuarioURL);


        try {
            ConsultarDatos();
        }catch (Exception ex){
            MostrarToast("Falla Consultar");
        }

        // ActualizarPerfil(this.findViewById(R.id.window));
        Button Button= (Button) findViewById(R.id.btn_FotoPerfil);
        // Drawable f= R.drawable.facebook;
        //Button.setBackground(f);
        //Button.setForeground(f);
        Button.setCompoundDrawablesWithIntrinsicBounds(
                0, //left
                0, //top
                0, //right
                R.drawable.ic_file_upload_black_48dp_2x //bottom
        );
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setMessage("¿Desea Guardar Cambios?")
                        .setCancelable(false)
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Drawable f=  findViewById(R.drawable.facebook);
                                //save.setBackground;
                                try{
                                    String nom= nombre.getText().toString();
                                    String eda= edad.getText().toString();
                                    String ciuda=cuidad.getText().toString();
                                    String altur= altura.getText().toString();
                                    String pes= peso.getText().toString();
                                    String pai= pais.getText().toString();
                                    String corre= correo.getText().toString();

                                    SharedPreferences preferences=getSharedPreferences("nombr", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor= preferences.edit();
                                    editor.putString("nombre",nom);
                                    editor.commit();
                                    Toast t=Toast.makeText(context,"Guardado", Toast.LENGTH_SHORT);
                                    t.show();
                                }catch (Exception e){
                                    Toast t=Toast.makeText(context,"Fallo al guardar", Toast.LENGTH_SHORT);
                                    t.show();
                                }

                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                             /*   camera= Camera.open();
                                Camera.Parameters parameters= camera.getParameters();
                                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                               // parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                                camera.setParameters(parameters);
                                camera.startPreview();*/
                                SharedPreferences preferences =getSharedPreferences("nombr",Context.MODE_PRIVATE);
                                String nom=preferences.getString("nombre", "NO hay nada");
                                Toast t=Toast.makeText(context, nom, Toast.LENGTH_SHORT);
                                t.show();
                            }
                        });
                AlertDialog alertP= alert.create();
                // alertP.show();
                String ruta= Environment.getExternalStorageDirectory()+"/AlacantFit/Pictures/FotoPerfil.jpg";
                mPath=ruta;
                Intent came=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String file= Environment.getExternalStorageDirectory()+"/AlacantFit/Pictures/";
                File fi=new File(file);
                fi.mkdirs();
                Uri uri= Uri.fromFile(new File(ruta));
                came.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(came, cons);

            }
        });
/*
        Button btn= (Button) findViewById(R.id.btn_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        });
  */  }
    public  Bitmap getBitmap(Bitmap drawable, boolean square) {
        int width = 0;
        int height = 0;

        Bitmap bitmap =  drawable;

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
    public void ConsultarDatos(){
        StringRequest request = new StringRequest(Request.Method.GET, usuarioURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jObject  = null; // json
                try {
                    jObject = new JSONObject(response);
                } catch (JSONException e) {
                    MostrarToast("devuelve null");
                }
                //JSONObject data = jObject.getJSONObject("data"); // get data object
                try {
                    String parametro = jObject.getString("Nombre");
                    // if(!parametro.equals("null"))
                    inNombre=parametro;

                    parametro = jObject.getString("Edad");
                    // if(!parametro.equals("null"))
                    inEdadparametro=(parametro);

                    // parametro = jObject.getString("Sexo");
                    // if(!parametro.equals("null"))
                    // inSexo.setText(parametro);

                    parametro = jObject.getString("Altura");
                    //if(!parametro.equals("null"))
                    inAltura=(parametro);

                    parametro = jObject.getString("Peso");
                    //if(!parametro.equals("null"))
                    inPeso=(parametro);

                    parametro = jObject.getString("Ciudad");
                    //if(!parametro.equals("null"))
                    inCiudad=(parametro);

                    parametro = jObject.getString("Pais");
                    //if(!parametro.equals("null"))
                    inPais=(parametro);

                    parametro = jObject.getString("Correo");
                    //if(!parametro.equals("null"))
                    inCorreo=(parametro);

                    parametro = jObject.getString("Genero");
                    //if(!parametro.equals("null"))
                    inGenero=(parametro);

                    parametro = jObject.getString("Pass");
                    //if(!parametro.equals("null"))
                    inPass=(parametro);

                    parametro = jObject.getString("Imagen");
                    //if(!parametro.equals("null"))
                    inImagen=(parametro);

                    //usu.setText(projectname, TextView.BufferType.EDITABLE);
                    //usu.setText("hola");

                } catch (JSONException e) {
                    MostrarToast("fallo al coger");
                }

               /* MostrarToast(inNombre);
                MostrarToast(inEdadparametro);
                MostrarToast(inAltura);
                MostrarToast(inPeso);
                MostrarToast(inCiudad);
                MostrarToast(inPais);
                MostrarToast(inCorreo);
                MostrarToast(inGenero);
                MostrarToast(inPass);
                MostrarToast(inImagen);*/
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
    public void ModificaPerfill(View v){


        StringRequest request = new StringRequest(Request.Method.PUT, editarPerfilURL+nom, new Response.Listener<String>() {
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
                MostrarToast("No se ha podido editar to perfil");
                MostrarToast(error.getMessage());

            }
        }) {

            @Override
            protected Map<String,String> getParams() throws
                    AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("Content-Type", "application/json; charset=utf-8");/*IMPORTANTÍSIMA*/ //por ésta linea me daba error 404
                String sub = nombre.getText().toString();
                if(!sub.equals("")) {
                    parametros.put("Nombre", sub);
                }else{
                    parametros.put("Nombre",inNombre);
                }


                parametros.put("Usuario", nom);
                parametros.put("Pass","abcdef");

                sub = correo.getText().toString();
                if(!sub.equals("")) {
                    parametros.put("Correo",sub);
                }else{
                    parametros.put("Correo",inCorreo);
                }
                sub = cuidad.getText().toString();
                if(!sub.equals("")) {
                    parametros.put("Ciudad", sub);
                }else{
                    parametros.put("Ciudad",inCiudad);
                }

                sub = pais.getText().toString();
                if(!sub.equals("")) {
                    parametros.put("Pais", sub);
                }else{
                    parametros.put("Pais",inPais);
                }

                sub = edad.getText().toString();
                if(!sub.equals("")) {
                    parametros.put("Edad", sub);
                }else{
                    parametros.put("Edad",inEdadparametro);
                }

                sub = altura.getText().toString();
                if(!sub.equals("")) {
                    parametros.put("Altura", sub);
                }else{
                    parametros.put("Altura",inAltura);
                }

                sub = peso.getText().toString();
                if(!sub.equals("")) {
                    parametros.put("Peso", sub);
                }else{
                    parametros.put("Peso",inPeso);
                }

                RadioButton m=(RadioButton)findViewById(R.id.rbtn_mujer);
                RadioButton h=(RadioButton)findViewById(R.id.rbtn_hombre);

                if(m.isChecked()){
                    parametros.put("Genero","0");

                }else if(h.isChecked()){
                    parametros.put("Genero","1");

                }else {
                    parametros.put("Genero", inGenero);
                }

                parametros.put("Imagen", inImagen);

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.dashboard,menu);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {

        try {
            super.onActivityResult(requestCode, resultCode, data);

            // Toast t = Toast.makeText(context, "1", Toast.LENGTH_SHORT);
            // t.show();
            if (resultCode == Activity.RESULT_OK) {
                // t=Toast.makeText(context, "2", Toast.LENGTH_SHORT);
                //t.show();
                MediaScannerConnection.scanFile(this,
                        new String[]{mPath},null,
                        new MediaScannerConnection.OnScanCompletedListener(){
                            @Override
                            public void onScanCompleted(String path,Uri uri){
                                Log.i("ExternalStorage","Scaned"+path+":");
                                Log.i("ExternalStorage","-> Uri = "+uri);
                            }
                        });

                Bitmap bit = (Bitmap) BitmapFactory.decodeFile(mPath);
                Bitmap b=getBitmap(bit,true);
                img.setImageBitmap(b);
            }
        }catch (Exception e){
            Toast t = Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT);
            t.show();
        }
    }
    public void ActualizarPerfil(View v)
    {

        MostrarToast(loginURL);
        StringRequest request = new StringRequest(Request.Method.PUT, loginURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                MostrarToast("Cambios Guardados");
                return;
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("RESPONSE NO");
                MostrarToast("Error De conexion");
                MostrarToast(error.getMessage());
            }
        }) {


            @Override
            protected Map<String,String> getParams() throws
                    AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("Content-Type", "application/json; charset=utf-8");//IMPORTANTÍSIMA //por ésta linea me daba error 404

                //  if(nombre.getText().toString()!=""){
                MostrarToast(nombre.getText().toString());
                parametros.put("Nombre", "Carlos");
                       /* }else{
                            parametros.put("Nombre", inNombre);
                        }*/
                parametros.put("Usuario", "carlos24");
                parametros.put("Pass", "abcdef");
                       /* if(correo.getText().toString()!=""){
                            parametros.put("Correo", correo.getText().toString());

                        }else{*/
                parametros.put("Correo", "carlosps@gmail.com");
                       /* }

                        if(cuidad.getText().toString()!=""){
                            parametros.put("Ciudad", cuidad.getText().toString());
                        }else{*/
                parametros.put("Ciudad", "Alicante");
                        /*}

                        if(pais.getText().toString()!=""){
                            parametros.put("Pais", pais.getText().toString());

                        }else{*/
                parametros.put("Pais", "España");
                        /*}
                        if(edad.getText().toString()!=""){
                            parametros.put("Altura", edad.getText().toString());

                        }else{*/
                parametros.put("Altura", "30");
                        /*}

                        if(altura.getText().toString()!=""){
                            parametros.put("Altura", altura.getText().toString());

                        }else{9*/
                parametros.put("Altura", "185");
                        /*}

                        if(peso.getText().toString()!=""){
                            parametros.put("Peso", peso.getText().toString());

                        }else{/*/
                parametros.put("Peso", "65");
                        /*}
                        RadioButton h=(RadioButton) findViewById(R.id.rbtn_hombre);
                        RadioButton m=(RadioButton) findViewById(R.id.rbtn_mujer);
                        if(h.isChecked()){
                            parametros.put("Genero", "1");

                        }else if(m.isChecked()){
                            parametros.put("Genero", "0");
                        }else{*/
                parametros.put("Genero", "1");
                //}



                parametros.put("Imagen", "null");
                return parametros;
                //return toJSON();
            }
        };
        //MostrarToast(request.toString());
        requestQueue.add(request);


    }
    public void MostrarToast(String mensaje)
    {
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void cambioActivity()
    {
        Intent intent;
        if(primerInicio.equals("true")) {
            intent = new Intent(this, PrimerInicio.class);
        }else{
            MostrarToast("primerInicio: "+primerInicio);
            intent = new Intent(this, Dashboard.class);
        }

        startActivity(intent);
        //System.out.println("ERROR1");
        //finish();
    }
    /*
    String ruta= Environment.getExternalStorageDirectory()+"/test.jpg";

    if(requestCode==1){
        if(data!=null){
            if(data.hasExtra("data")){
                ImageView b=(ImageView) findViewById(R.id.img_foto);
                b.setImageBitmap((Bitmap) data.getParcelableExtra("data"));
            }else{
                ImageView b=(ImageView) findViewById(R.id.img_foto);
                b.setImageBitmap(BitmapFactory.decodeFile(ruta));
                //Toast t=Toast.makeText(context, "no se puede", Toast.LENGTH_SHORT);
               // t.show();
            }
        }else{

        }
    }else{

    }
}
// @TargetApi(16)
/* public void onClickSave(){
    ImageButton imageButton= (ImageButton) findViewById(R.id.ibtn_FotoPerfil);
   // EditText nom=(EditText)findViewById(R.id.tbx_Nombre);

    //nombre=(EditText)((EditText) findViewById(R.id.tbx_Nombre)).getText();

    Toast t=Toast.makeText(getApplicationContext(),"Bienvenido a probar el programa.", Toast.LENGTH_SHORT);
    t.show();
   // dialogo1.show();

    //Drawable f= findViewById(R.drawable.facebook);
    //imageButton.setImageDrawable(getDrawable(R.drawable.facebook));
    //int i=0;
    //imageButton.setBackground(getResources().getDrawable(i,getTheme()));
  //  imageButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));

}
public void aceptar() {
    Toast t=Toast.makeText(this,"Bienvenido a probar el programa.", Toast.LENGTH_SHORT);
    t.show();
}

public void cancelar() {
    finish();
}*/
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        RadioButton hombre= (RadioButton)findViewById(R.id.rbtn_hombre);
        RadioButton mujer= (RadioButton)findViewById(R.id.rbtn_mujer);



        try {
            boolean checked = ((RadioButton) view).isChecked();

            // Check which radio button was clicked
            switch (view.getId()) {
                case R.id.rbtn_hombre:
                    if (checked)
                        //  mujer.setChecked(false);
                        break;
                case R.id.rbtn_mujer:
                    if (checked)
                        //  hombre.setChecked(false);
                        break;
            }
        }catch (Exception e){

        }
    }
/*
    public void onClickSexoHombre(){

       // hombre.setOnCheckedChangeListener( new );

        boolean hb;
        hombre.getResources().getBoolean());



    }

    public void onClickSexoMujer(){

    }*/
}
