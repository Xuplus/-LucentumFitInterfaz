package lucentum.com;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Ranking extends AppCompatActivity {

    //private String globalJSON = "{\"country\":"+"[{\"Usuario\":\"leo25\",\"Posicion\":1,\"Puntuacion\":280},{\"Usuario\":\"guapa22\",\"Posicion\":3,\"Puntuacion\":250},{\"Usuario\":\"chispica\",\"Posicion\":5,\"Puntuacion\":215},{\"Usuario\":\"manu\",\"Posicion\":6,\"Puntuacion\":177},{\"Usuario\":\"lola14\",\"Posicion\":7,\"Puntuacion\":155},{\"Usuario\":\"thiago11\",\"Posicion\":8,\"Puntuacion\":146},{\"Usuario\":\"carlos24\",\"Posicion\":10,\"Puntuacion\":140},{\"Usuario\":\"stephen\",\"Posicion\":12,\"Puntuacion\":122},{\"Usuario\":\"JorgiRunner\",\"Posicion\":13,\"Puntuacion\":122},{\"Usuario\":\"DaniRunBoy\",\"Posicion\":14,\"Puntuacion\":114},{\"Usuario\":\"choto\",\"Posicion\":15,\"Puntuacion\":95},{\"Usuario\":\"pascu008\",\"Posicion\":16,\"Puntuacion\":88},{\"Usuario\":\"chapa\",\"Posicion\":17,\"Puntuacion\":81},{\"Usuario\":\"maria90\",\"Posicion\":18,\"Puntuacion\":80},{\"Usuario\":\"tamara96\",\"Posicion\":19,\"Puntuacion\":80},{\"Usuario\":\"luis\",\"Posicion\":20,\"Puntuacion\":75},{\"Usuario\":\"Confi\",\"Posicion\":21,\"Puntuacion\":41},{\"Usuario\":\"guti\",\"Posicion\":22,\"Puntuacion\":30},{\"Usuario\":\"juanMA\",\"Posicion\":23,\"Puntuacion\":20},{\"Usuario\":\"susi\",\"Posicion\":24,\"Puntuacion\":14}]"+"}";
    //private String personalJSON = "{\"country\":"+"[{\"Usuario\":\"manu\",\"Posicion\":4,\"Puntuacion\":177},{\"Usuario\":\"lola14\",\"Posicion\":5,\"Puntuacion\":155},{\"Usuario\":\"thiago11\",\"Posicion\":6,\"Puntuacion\":146},{\"Usuario\":\"carlos24\",\"Posicion\":7,\"Puntuacion\":140},{\"Usuario\":\"stephen\",\"Posicion\":8,\"Puntuacion\":122},{\"Usuario\":\"JorgiRunner\",\"Posicion\":9,\"Puntuacion\":122},{\"Usuario\":\"DaniRunBoy\",\"Posicion\":10,\"Puntuacion\":114},{\"Usuario\":\"choto\",\"Posicion\":11,\"Puntuacion\":95},{\"Usuario\":\"pascu008\",\"Posicion\":12,\"Puntuacion\":88}]"+"}";
    //private String amigosJSON = "{\"country\":"+"[{\"Usuario\":\"lola14\",\"Posicion\":1,\"Puntuacion\":155},{\"Usuario\":\"thiago11\",\"Posicion\":2,\"Puntuacion\":146},{\"Usuario\":\"carlos24\",\"Posicion\":3,\"Puntuacion\":140},{\"Usuario\":\"stephen\",\"Posicion\":4,\"Puntuacion\":122},{\"Usuario\":\"JorgiRunner\",\"Posicion\":5,\"Puntuacion\":122},{\"Usuario\":\"DaniRunBoy\",\"Posicion\":6,\"Puntuacion\":114},{\"Usuario\":\"choto\",\"Posicion\":7,\"Puntuacion\":95},{\"Usuario\":\"pascu008\",\"Posicion\":8,\"Puntuacion\":88}]"+"}";

    RequestQueue requestQueue;

    private String urlGlobal="http://46.101.84.36:80/ranking";
    private String urlPersonal="http://46.101.84.36:80/ranking/"; //+id
    private String urlAmigos="http://46.101.84.36:80/ranking/Personal/"; //+id

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        listView = (ListView) findViewById(R.id.user_list);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, countryList, android.R.layout.simple_list_item_1, new String[] {"country"}, new int[] {android.R.id.text1});
        listView.setAdapter(simpleAdapter);

        SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        final String usuario = preferences.getString("usu", "null");
        System.out.println("EL USUARIO ACTUAL ES: "+usuario+".");

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //SE MUESTRA POR DEFECTO EL RANKING GLOBAL

        StringRequest request = new StringRequest(Request.Method.GET, urlGlobal, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println("Me devuelve JSON   iuyf");
                System.out.println("RESPONSE " + response);
                try {
                    String completo = "{\"country\":" + response + "}";
                    stringInit.setS(completo);
                    initList(stringInit.getS());
                    System.out.println(completo);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("NO");
            }
        }) {
        };
        requestQueue.add(request);

        //BOTON PARA ELEGIR EL RANKING PERSONAL

        final Button button_personal = (Button) findViewById(R.id.btn_ranking_personal);
        assert button_personal != null;

        button_personal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StringRequest request = new StringRequest(Request.Method.GET, urlPersonal + usuario, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        System.out.println("Me devuelve JSON   iuyf");
                        System.out.println("RESPONSE " + response);
                        try {
                            String completo = "{\"country\":" + response + "}";
                            stringInit.setS(completo);
                            initList(stringInit.getS());
                            System.out.println(completo);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("NO");
                    }
                }) {
                };
                requestQueue.add(request);
                // initList(personalJSON);
            }
        });

        //BOTON PARA ELEGIR EL RANKING GLOBAL

        final Button button_global = (Button) findViewById(R.id.btn_ranking_global);
        assert button_global != null;

        button_global.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                StringRequest request = new StringRequest(Request.Method.GET, urlGlobal, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        System.out.println("Me devuelve JSON   iuyf");
                        System.out.println("RESPONSE " + response);
                        try {
                            String completo = "{\"country\":" + response + "}";
                            stringInit.setS(completo);
                            initList(stringInit.getS());
                            System.out.println(completo);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("NO");
                    }
                }) {
                };
                requestQueue.add(request);
                //initList(globalJSON);
            }
        });

        // BOTON PARA ELEGIR EL RANKING AMIGOS

        final Button button_amigos = (Button) findViewById(R.id.btn_ranking_amigos);
        assert button_amigos != null;

        button_amigos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                StringRequest request = new StringRequest(Request.Method.GET, urlAmigos + usuario, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        System.out.println("Me devuelve JSON   iuyf");
                        System.out.println("RESPONSE " + response);
                        try {
                            String completo = "{\"country\":" + response + "}";
                            stringInit.setS(completo);
                            initList(stringInit.getS());
                            System.out.println(completo);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("NO");
                    }
                }) {
                };
                requestQueue.add(request);
               // initList(amigosJSON);
            }
        });
    }

    List<Map<String,String>> countryList = new ArrayList<Map<String,String>>();

    private void initList(String s){

        System.out.println("ENTRA ESTO A INIT: " + s);

        try{
            JSONObject jsonResponse = new JSONObject(s);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("country");
            countryList.clear();

            for(int i = 0; i<jsonMainNode.length();i++){
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                String usuario = jsonChildNode.optString("Usuario");
                int posicion = jsonChildNode.optInt("Posicion");
                int puntuacion = jsonChildNode.optInt("Puntuacion");
                String outPut = posicion+"º" + "    " +usuario+"    "+puntuacion+" FitPoints";
                countryList.add(createEmployee("country", outPut));
            }
            SimpleAdapter simpleAdapter = new SimpleAdapter(this, countryList, android.R.layout.simple_list_item_1, new String[] {"country"}, new int[] {android.R.id.text1});
            simpleAdapter.notifyDataSetChanged();
            listView.setAdapter(simpleAdapter);
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