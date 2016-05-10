package lucentum.com;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Ranking extends AppCompatActivity {

    private ListView list;
    private String[] sistemas= {"juan","carla","pedro", "paco","ana","carlos","esteban","jose","sara","chema","sandra", "carol", "cesar", "fernando"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        list = (ListView) findViewById(R.id.user_list);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sistemas);
        list.setAdapter(adaptador);

    }
}
