package lucentum.com;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Fran on 05/05/2016.
 */
class ListaContactos extends ArrayAdapter{

    List list = new ArrayList();
    TextView eliminar;
    String usuario;
    RequestQueue requestQueue;
    String eliminarURL = "http://46.101.84.36:3000/amigos/Romper";

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
            //contactosHolder.buton = (ImageButton)row.findViewById(R.id.ib_eliminar);
            row.setTag(contactosHolder);
        }
        else{
            contactosHolder = (ContactosHolder)row.getTag();

        }

        DatosContactos contactos = (DatosContactos)this.getItem(position);
        contactosHolder.tv_nombre_contacto.setText(contactos.getId());
        contactosHolder.tvlocalidad.setText(contactos.getLocalidad());
        contactosHolder.tvpais.setText(contactos.getPais());
        usuario = contactos.getId();
       /* contactosHolder.buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = get;
            }
        });
*/

        return row;
    }

    public String getUsuario(){
        return usuario;
    }



    public String eliminarUsuario(int position){
        DatosContactos eliminar = (DatosContactos)this.getItem(position);
        return eliminar.getId();
    }

    static class ContactosHolder{
        public TextView tv_nombre_contacto;
        public TextView tvlocalidad;
        public TextView tvpais;
        //public ImageButton buton;
        //public TextView tvranking;
    }

    /*public void eliminarContacto(View v)
    {

        StringRequest request = new StringRequest(Request.Method.POST, eliminarURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                requestQueue = Volley.newRequestQueue(co);
                /*Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("RESPONSE NO");
                //MostrarToast("Contacto no eliminado.");

            }
        }) {


            @Override
            protected Map<String,String> getParams() throws
                    AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("Content-Type", "application/json; charset=utf-8");
                parametros.put("Usuario", usuario);
                parametros.put("Amigo", eliminar.getText().toString());
                return parametros;
                //return toJSON();
            }
        };
        requestQueue.add(request);
    }*/


}