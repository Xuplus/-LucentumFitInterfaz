package lucentum.com;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Field;

public class EditarPerfil extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        save= (Button) findViewById(R.id.btn_save);
        nombre= (EditText) findViewById(R.id.tbx_Nombre);
        edad= (EditText) findViewById(R.id.tbx_Edad);
        altura= (EditText) findViewById(R.id.tbx_altura);
        peso= (EditText) findViewById(R.id.tbx_peso);
        correo= (EditText) findViewById(R.id.tbx_correo);
        pais= (EditText) findViewById(R.id.tbx_pais);
        cuidad= (EditText) findViewById(R.id.tbx_cuidad);


        img=(ImageView) findViewById(R.id.img_foto);

        File fil=new File(mPath);

        if(fil.exists()){
            Bitmap b= BitmapFactory.decodeFile(mPath);
            img.setImageBitmap(b);
        }




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

                img.setImageBitmap(bit);
            }
        }catch (Exception e){
            Toast t = Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT);
             t.show();
        }
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
