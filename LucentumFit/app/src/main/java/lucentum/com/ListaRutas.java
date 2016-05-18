package lucentum.com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fran on 18/05/2016.
 */
class ListaRutas  extends ArrayAdapter {

    List lista = new ArrayList();

    public ListaRutas(Context context, int resource) {
        super(context, R.layout.activity_lista_rutas);
    }

    public void add(DatosRutas object) {
        super.add(object);
        lista.add(object);
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

        RutasHolder rutasHolder;

        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.activity_lista_rutas,parent,false);
            rutasHolder = new RutasHolder();
            rutasHolder.tv_nombre_ruta = (TextView)row.findViewById(R.id.tv_nombreruta);
            rutasHolder.tvdescargas = (TextView)row.findViewById(R.id.tv_vecesdescargada);
            rutasHolder.tvKM = (TextView)row.findViewById(R.id.tv_km);
            row.setTag(rutasHolder);
        }
        else{
            rutasHolder = (RutasHolder)row.getTag();

        }

        DatosRutas rutas = (DatosRutas) this.getItem(position);
        rutasHolder.tv_nombre_ruta.setText(rutas.getNombre());
        rutasHolder.tvdescargas.setText(rutas.getDescargas());
        rutasHolder.tvKM.setText(rutas.getKM());


        return row;
    }


    static class RutasHolder{
        public TextView tv_nombre_ruta;
        public TextView tvdescargas;
        public TextView tvKM;
    }


}
