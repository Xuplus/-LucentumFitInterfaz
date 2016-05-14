package lucentum.com;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.view.View;

public class Ranking extends AppCompatActivity {

    private ListView list;
    //String rankGlobal = "http://46.101.84.36:3000/ranking";
    private String[] amigos = {"juan", "carla", "pedro", "paco", "ana", "carlos", "esteban", "jose", "sara", "chema", "sandra", "carol", "cesar", "fernando"};
    private String[] global = {"bolt", "jornet", "stevenson", "sergio", "smith", "charlie", "martinez", "jose luis", "amaya", "petrov", "champion", "april", "leslie", "ron"};
    private String[] personal = {"personal", "jornet", "stevenson", "sergio", "smith", "charlie", "martinez", "jose luis", "amaya", "petrov", "champion", "april", "leslie", "ron"};



    //TODO:
        //  MÉTODO PARA LEER DE JSON O LO QUE SEA Y GENERAR EL STRING

    //TODO:
        //  MÉTODO PARA AÑADIR LA NUMERACIÓN



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        //SE MUESTRA POR DEFECTO EL RANKING DE AMIGOS
        list = (ListView)

                findViewById(R.id.user_list);

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(Ranking.this, android.R.layout.simple_list_item_1, amigos);
        list.setAdapter(adaptador);

        //BOTON PARA ELEGIR EL RANKING DE AMIGOS
        final Button button_amigos = (Button) findViewById(R.id.btn_ranking_amigos);
        button_amigos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                list = (ListView)

                        findViewById(R.id.user_list);

                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(Ranking.this, android.R.layout.simple_list_item_1, amigos);
                list.setAdapter(adaptador);
            }

        });

        //BOTON PARA ELEGIR EL RANKING GLOBAL
        final Button button_global = (Button) findViewById(R.id.btn_ranking_global);
        button_global.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                list = (ListView)

                        findViewById(R.id.user_list);

                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(Ranking.this, android.R.layout.simple_list_item_1, global);
                list.setAdapter(adaptador);

            }

        });

        // BOTON PARA ELEGIR EL RANKING PERSONAL
        final Button button_personal = (Button) findViewById(R.id.btn_ranking_personal);
        button_personal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                list = (ListView)

                        findViewById(R.id.user_list);

                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(Ranking.this, android.R.layout.simple_list_item_1, personal);
                list.setAdapter(adaptador);

            }

        });

    }
}
