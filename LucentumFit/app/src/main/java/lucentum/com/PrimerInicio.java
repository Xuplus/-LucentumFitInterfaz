package lucentum.com;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class PrimerInicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primer_inicio);
        EditText nombre = (EditText)findViewById(R.id.et_inNombre);

    }

    public void CambioDashboard(View v){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
        finish();
    }

    public void ModificaPerfil(View v){

    }
}
