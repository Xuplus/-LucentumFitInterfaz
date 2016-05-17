package lucentum.com;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fran on 05/05/2016.
 */
class ListaContactos extends ArrayAdapter{

    List list = new ArrayList();
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
            row.setTag(contactosHolder);
        }
        else{
            contactosHolder = (ContactosHolder)row.getTag();

        }

        DatosContactos contactos = (DatosContactos)this.getItem(position);
        contactosHolder.tv_nombre_contacto.setText(contactos.getId());
        contactosHolder.tvlocalidad.setText(contactos.getLocalidad());
        contactosHolder.tvpais.setText(contactos.getPais());

        return row;
    }

    static class ContactosHolder{
        public TextView tv_nombre_contacto;
        public TextView tvlocalidad;
        public TextView tvpais;
        //public TextView tvranking;
    }

}