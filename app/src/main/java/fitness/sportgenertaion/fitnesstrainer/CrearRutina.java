package fitness.sportgenertaion.fitnesstrainer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fitness.sportgenertaion.fitnesstrainer.Classes.Ejercicio;
import fitness.sportgenertaion.fitnesstrainer.Classes.EjercicioAdapter;

public class CrearRutina extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener, ValueEventListener, ChildEventListener {
    ///7777dfg
    DatabaseReference dbPrediccio;
    String nivel = "5";
    String grupoMuscular = "5";
    Spinner spGrupoMuscular;
    Spinner spNivel;
    Spinner spDia;
    Button bguardar;
    private RecyclerView rvEjercicios;
    private List<Ejercicio> llistaEjercicios = new ArrayList<Ejercicio>();
    private EjercicioAdapter ejercicioAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_rutina);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Inicialitzem les variables


        spDia = findViewById(R.id.spDia);
        spNivel = findViewById(R.id.spNivel);
        spGrupoMuscular = findViewById(R.id.spMusculo);
        bguardar = findViewById(R.id.bGuargar);
        rvEjercicios = findViewById(R.id.rvEjercicios);

        //Hago que en los spinners salgan los arrays
        ArrayAdapter<CharSequence> adapterMusculos = ArrayAdapter.createFromResource(this, R.array.musculos, android.R.layout.simple_spinner_item);
        adapterMusculos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGrupoMuscular.setAdapter(adapterMusculos);

        ArrayAdapter<CharSequence> adapterDias = ArrayAdapter.createFromResource(this, R.array.dia, android.R.layout.simple_spinner_item);
        adapterMusculos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDia.setAdapter(adapterDias);

        ArrayAdapter<CharSequence> adapterNivel = ArrayAdapter.createFromResource(this, R.array.dificultad, android.R.layout.simple_spinner_item);
        adapterMusculos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spNivel.setAdapter(adapterNivel);

        //Hago referencia a la base de datos
        dbPrediccio = FirebaseDatabase.getInstance()
                .getReference()
                .child("Ejercicios");
        dbPrediccio.addValueEventListener(this);
        dbPrediccio.addChildEventListener(this);


        rvEjercicios = findViewById(R.id.rvEjercicios);

        rvEjercicios.setLayoutManager(new LinearLayoutManager(this));
        //rvPrediccions.setLayoutManager(new GridLayoutManager(this, 2));
        rvEjercicios.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));
        //rvPrediccions.setAdapter(prediccioAdapter);
        ejercicioAdapter = new EjercicioAdapter(this, llistaEjercicios, spDia.getSelectedItem().toString());


        //hago el listenner del spinner
        spGrupoMuscular.setOnItemSelectedListener(this);
        spNivel.setOnItemSelectedListener(this);
        spDia.setOnItemSelectedListener(this);

        //Hago una conversion


    }


    //Metodes de la interficie ValueEvetListener
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

       /* Prediccion pred= dataSnapshot.getValue(Prediccion.class);
        tvCel.setText(pred.getCel());
        tvTemperatura.setText(pred.getTemperatura() + "ºC");
       tvHumitat.setText(pred.getHumitat() + "%");
       */
        llistaEjercicios.removeAll(llistaEjercicios);
        for (DataSnapshot element : dataSnapshot.getChildren()) {
            if ((element.child("dificultad").getValue().toString().equals(this.nivel) && (element.child("musculos").getValue().toString().equals(this.grupoMuscular))) || (this.nivel.equals("5") && this.grupoMuscular.equals("5")) || (this.grupoMuscular.equals("5") && element.child("dificultad").getValue().toString().equals(this.nivel)) || this.nivel.equals("5") && element.child("musculos").getValue().toString().equals(this.grupoMuscular)) {

                Ejercicio ejercicio = new Ejercicio(element.getKey().toString(), element.child("descripcion").getValue().toString(), element.child("foto").getValue().toString(), Integer.valueOf(element.child("dificultad").getValue().toString()), Integer.valueOf(element.child("musculos").getValue().toString()));

                llistaEjercicios.add(ejercicio);
            }
        }
        rvEjercicios.setAdapter(ejercicioAdapter);
        rvEjercicios.scrollToPosition(llistaEjercicios.size() - 1);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.crear_rutina, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }


    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



            if (spNivel.getSelectedItem().toString().equals("Todos")) {
                this.nivel = "5";
            } else if (spNivel.getSelectedItem().toString().equals("Principiante")) {
                this.nivel = "1";
            } else if (spNivel.getSelectedItem().toString().equals("Entusiasta")) {
                this.nivel = "2";
            } else {
                this.nivel = "3";
            }

            if (spGrupoMuscular.getSelectedItem().toString().equals("Todos")) {
                this.grupoMuscular = "5";
            } else if (spGrupoMuscular.getSelectedItem().toString().equals("Espalda y Biceps")) {
                this.grupoMuscular = "1";
            } else if (spGrupoMuscular.getSelectedItem().toString().equals("Pecho y Triceps")) {
                this.grupoMuscular = "2";
            } else if (spGrupoMuscular.getSelectedItem().toString().equals("Abdominales")) {
                this.grupoMuscular = "3";
            } else {
                this.grupoMuscular = "4";
            }




            ejercicioAdapter = new EjercicioAdapter(this, llistaEjercicios, spDia.getSelectedItem().toString());

        dbPrediccio.addValueEventListener(this);
        dbPrediccio.addChildEventListener(this);



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
