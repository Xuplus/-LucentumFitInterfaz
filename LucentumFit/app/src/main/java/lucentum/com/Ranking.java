package lucentum.com;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.view.View;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class Ranking extends AppCompatActivity {

    private ListView list;
    RequestQueue requestQueue;
    String rankGlobal = "http://46.101.84.36/ranking";
    String rankAmigos ="http://46.101.84.36/ranking/"; //+id
    String rankPersonal ="http://46.101.84.36/ranking/Personal/"; //+id
    String rankPersonalPos = "http://46.101.84.36/ranking/Posicion/"; //+id
    private String[] amigos = {};
    private String[] global = {};
    private String[] personal = {};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        String usuario = preferences.getString("usu", "null");

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        cargarRankingAmigos(usuario);
        cargarRankingGlobal();
        cargarRankingPersonal(usuario);

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

    public void cargarRankingAmigos(String usuario) {
        StringRequest request = new StringRequest(Request.Method.GET, rankAmigos+usuario, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                JSONObject jObject  = null; // json
                try {
                    jObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    String parametro = jObject.getString("Nombre");

                    System.out.println("RESPONSE "+parametro);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {};
        requestQueue.add(request);
    }

    public void cargarRankingGlobal() {
        StringRequest request = new StringRequest(Request.Method.GET, rankGlobal, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                JSONObject jObject  = null; // json
                try {
                    jObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    String parametro = jObject.getString("Nombre");

                    System.out.println("RESPONSE "+parametro);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {};
        requestQueue.add(request);
    }

    public void cargarRankingPersonal(String usuario) {
        StringRequest request = new StringRequest(Request.Method.GET, rankPersonal+usuario, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                JSONObject jObject  = null; // json
                try {
                    jObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    String parametro = jObject.getString("Nombre");

                    System.out.println("RESPONSE "+parametro);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {};
        requestQueue.add(request);
    }
}

