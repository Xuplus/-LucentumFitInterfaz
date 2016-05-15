package lucentum.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class Dashboard extends AppCompatActivity/*
        implements NavigationView.OnNavigationItemSelectedListener */{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("ERROR2");
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        System.out.println("ERROR3");
        setContentView(R.layout.fragment_dashboard);
        System.out.println("ERROR4");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        System.out.println("ERROR5");
        setSupportActionBar(toolbar);
        System.out.println("ERROR6");

=======
        setContentView(R.layout.activity_dashboard);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
>>>>>>> refs/remotes/origin/master

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        // Botones

        findViewById(R.id.bStart).setOnClickListener(onClickListener);
        findViewById(R.id.bLoad).setOnClickListener(onClickListener);
        findViewById(R.id.iv1).setOnClickListener(onClickListener);
        findViewById(R.id.iv2).setOnClickListener(onClickListener);
        findViewById(R.id.iv3).setOnClickListener(onClickListener);
        findViewById(R.id.iv4).setOnClickListener(onClickListener);
        findViewById(R.id.iv5).setOnClickListener(onClickListener);

        // !Botones

        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();*/

        /*NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bStart:
                    startRuta();
                    break;
                case R.id.bLoad:
                    loadRuta();
                    break;
                case R.id.iv1:
                    rutas();
                    break;
                case R.id.iv2:
                    perfil();
                    break;
                case R.id.iv3:
                    ranking();
                    break;
                case R.id.iv4:
                    contactos();
                    break;
                case R.id.iv5:
                    ajustes();
                    break;
            }
        }
    };

    private void loadRuta() {
<<<<<<< HEAD
        Intent in = new Intent(this,Rutas.class);
=======
        /*Intent in = new Intent(this,rutadetallada.class);
        startActivity(in);*/
        rutas();
>>>>>>> refs/remotes/origin/master
    }

    private void startRuta() {
        Intent in = new Intent(this,CrearRuta.class);
        startActivity(in);
    }

    private void rutas(){
        Intent in= new Intent(this,Rutas.class);
        startActivity(in);
    }

    private void perfil(){
        Intent in= new Intent(this,Perfil.class);
        startActivity(in);
    }

    private void ranking(){
        Intent in= new Intent(this,Ranking.class);
        startActivity(in);
    }

    private void contactos(){
        Intent in= new Intent(this,Contactos.class);
        startActivity(in);
    }

    private void ajustes(){
        Intent in= new Intent(this,Configuracion.class);
        startActivity(in);
    }

    @Override
    public void onBackPressed() {
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*@SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent in;

        if (id == R.id.nav_dashboard) {
            in= new Intent(this,Dashboard.class);
            startActivity(in);

        } else if (id == R.id.nav_rutas) {
            in= new Intent(this,Rutas.class);
            startActivity(in);

        } else if (id == R.id.nav_perfil) {
            in= new Intent(this,Perfil.class);
            startActivity(in);

        } else if (id == R.id.nav_ranking) {
            in= new Intent(this,Ranking.class);
            startActivity(in);

        } else if (id == R.id.nav_contactos) {
            in= new Intent(this,Contactos.class);
            startActivity(in);

        } else if (id == R.id.nav_ajustes) {
            in= new Intent(this,Configuracion.class);
            startActivity(in);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/
}
