package lucentum.com;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fran on 05/05/2016.
 */
class ListaContactos extends ArrayAdapter{

    URL imageUrl = null;
    HttpURLConnection conn = null;

    List list = new ArrayList();
    TextView eliminar;
    String usuario;
    RequestQueue requestQueue;
    String eliminarURL = "http://46.101.84.36:80/amigos/Romper";
    private Bitmap loadedImage;

    public ListaContactos(Context context, int resource) {
        super(context, R.layout.activity_lista_contactos);
    }


    public void add(DatosContactos object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;

        ContactosHolder contactosHolder;

        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.activity_lista_contactos,parent,false);
            contactosHolder = new ContactosHolder();
            contactosHolder.tv_nombre_contacto = (TextView)row.findViewById(R.id.tv_nombre_contacto);
            contactosHolder.tvlocalidad = (TextView)row.findViewById(R.id.tv_localidad);
            contactosHolder.tvpais = (TextView)row.findViewById(R.id.tv_rutas_realizadas);
            contactosHolder.foto = (ImageView)row.findViewById(R.id.iv_foto);
            row.setTag(contactosHolder);
        }
        else{
            contactosHolder = (ContactosHolder)row.getTag();

        }

        DatosContactos contactos = (DatosContactos)this.getItem(position);
        contactosHolder.tv_nombre_contacto.setText(contactos.getId());
        contactosHolder.tvlocalidad.setText(contactos.getLocalidad());
        contactosHolder.tvpais.setText(contactos.getPais());
        URL imageUrl = null;
        try {
            imageUrl = new URL(contactos.getImagen());
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
            contactosHolder.foto.setImageBitmap(loadedImage);
        } catch (IOException e) {
            contactosHolder.foto.setImageResource(R.drawable.faro);
        }

        return row;
    }

    public String eliminarUsuario(int position){
        DatosContactos eliminar = (DatosContactos)this.getItem(position);
        return eliminar.getId();
    }

    static class ContactosHolder{
        public TextView tv_nombre_contacto;
        public TextView tvlocalidad;
        public TextView tvpais;
        public ImageView foto;
    }

}