package lucentum.com;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.SortedList;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.net.*;
import java.io.*;


public class Ranking extends AppCompatActivity {

    private ListView list;
    RequestQueue requestQueue;
    private String stringFinalGlobal="";
    private String global="";



    private String stringFinalPersonal="";
    private String stringFinalAmigos="";


    private String localjsonString = "{\"country\":"+"[{\"Usuario\":\"sergi\",\"Posicion\":1,\"Puntuacion\":456},{\"Usuario\":\"jorge\",\"Posicion\":2,\"Puntuacion\":123},{\"Usuario\":\"a\",\"Posicion\":3,\"Puntuacion\":1}]"+"}";

    String rankGlobal = "https://gist.githubusercontent.com/LeoRodMrez/5b0e91618d12f7ca89e0b497ce9a3e88/raw/f8be75f70dcab334490a6306fd56175ba8c731c4/json.json";
    String rankPersonalPos = "http://46.101.84.36/ranking/Posicion/"; //+id
    //public String global ="";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        ListView listView = (ListView) findViewById(R.id.user_list);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, countryList, android.R.layout.simple_list_item_1, new String[] {"country"}, new int[] {android.R.id.text1});
        listView.setAdapter(simpleAdapter);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //cargarRanking(rankGlobal, );
        //initList(global);



/*

        //SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        //String usuario = preferences.getString("usu", "null");

       /*

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

        });*/

        //BOTON PARA ELEGIR EL RANKING GLOBAL
        final Button button_global = (Button) findViewById(R.id.btn_ranking_global);
        button_global.setOnClickListener(new View.OnClickListener() {
            //String global="";

            public void onClick(View v) {

                StringRequest request = new StringRequest(Request.Method.GET, rankGlobal, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        System.out.println("Me devuelve JSON   iuyf");
                        System.out.println("RESPONSE " + response);
                        try {
                            stringFinalGlobal = response;
                            String completo = "{\"country\":"+stringFinalGlobal+"}";
                            //initList(completo);
                            global = completo;

                            System.out.println(completo);
                            System.out.println(localjsonString);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("NO");
                    }
                }) {
                };
                requestQueue.add(request);
               // System.out.println("ESTO SALE: " +global);
               // initList(global);
                //initList(localjsonString);
            }

        });

/*
        // BOTON PARA ELEGIR EL RANKING PERSONAL
        final Button button_personal = (Button) findViewById(R.id.btn_ranking_personal);
        button_personal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                list = (ListView)

                        findViewById(R.id.user_list);

                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(Ranking.this, android.R.layout.simple_list_item_1, personal);
                list.setAdapter(adaptador);

            }

        });*/
        
    }

    public void stringTest(String s){
        System.out.println("SALE: "+s);
        //global = s;
    }


    List<Map<String,String>> countryList = new ArrayList<Map<String,String>>();
    private void initList(String s){
        System.out.println("ENTRA ESTO A INIT: " + s);

        try{
            JSONObject jsonResponse = new JSONObject(s);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("country");

            for(int i = 0; i<jsonMainNode.length();i++){
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                String usuario = jsonChildNode.optString("Usuario");
                int posicion = jsonChildNode.optInt("Posicion");
                int puntuacion = jsonChildNode.optInt("Puntuacion");
                String outPut = posicion+"º" + "    " +usuario+"    "+puntuacion+" FitPoints";
                countryList.add(createEmployee("country", outPut));
            }
        }
        catch(JSONException e){
            Toast.makeText(getApplicationContext(), "Error"+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private HashMap<String, String>createEmployee(String usuario,String puntuacion){
        HashMap<String, String> employeeNameNo = new HashMap<String, String>();
        employeeNameNo.put(usuario, puntuacion);
        return employeeNameNo;
    }
}



