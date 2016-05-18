package lucentum.com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Fran on 05/05/2016.
 */
class ListaContactos extends ArrayAdapter<String>{
    public ListaContactos(Context context, List<String> datos) {
        super(context, R.layout.activity_lista_contactos, datos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
       // LayoutInflater inflater = LayoutInflater.from(getContext());
        //View CustoView = inflater.inflate(R.layout.activity_lista_contactos, parent, false);

    }
}
