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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

public class CrearRutina extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ValueEventListener, ChildEventListener {
    ///7777dfg
    Spinner spGrupoMuscular;
    Spinner spNivel;
    Spinner spDia;
    Button bguardar;
    private RecyclerView rvListaEjercicios;
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
        rvListaEjercicios = findViewById(R.id.rvListaEjercicios);

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
        DatabaseReference dbEjercicios = FirebaseDatabase.getInstance().getReference().child("Ejercicios");
        dbEjercicios.addChildEventListener(this);
        dbEjercicios.addValueEventListener(this);
        rvListaEjercicios.setHasFixedSize(true);
        rvListaEjercicios.setLayoutManager(new LinearLayoutManager(this)); // també es pot posar "getApplicationContext()"

        rvListaEjercicios.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        ejercicioAdapter = new EjercicioAdapter(this, llistaEjercicios);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        llistaEjercicios.removeAll(llistaEjercicios);
        // Recorrem tots els elements del DataSnapshot i els mostrem
        Toast.makeText(CrearRutina.this, "Hi ha " + dataSnapshot.getChildrenCount() + " dates a la llista", Toast.LENGTH_SHORT).show();
        for (DataSnapshot element : dataSnapshot.getChildren()) {
            Ejercicio ejercicio = new Ejercicio(
                    element.getKey().toString(),
                    element.child("descripcion").getValue().toString(),
                    element.child("foto").getValue().toString(),
                    Integer.valueOf(element.child("dificultad").getValue().toString()),
                    Integer.valueOf(element.child("musculos").getValue().toString()));
            llistaEjercicios.add(ejercicio);
        }
        // Si ho volguéssim fer amb subElements:
        //        for (DataSnapshot element : dataSnapshot.getChildren()) {
        //            for (DataSnapshot subElement : element.getChildren()) {
//        llistaPrediccio.add(new Prediccio("data", "cel", 22, 4.5));

        // Per si hi ha canvis, que es refresqui l'adaptador
        ejercicioAdapter.notifyDataSetChanged();
        rvListaEjercicios.scrollToPosition(llistaEjercicios.size() - 1);
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
}
