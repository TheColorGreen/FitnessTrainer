package fitness.sportgenertaion.fitnesstrainer;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import fitness.sportgenertaion.fitnesstrainer.Classes.RutinaAcciones;

public class CrearRutina extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener, ValueEventListener, ChildEventListener {
    ///7777dfg
    TextView tvDia;
    static ArrayList<String> lunes = new ArrayList<String>();
    static ArrayList<String> martes = new ArrayList<String>();
    static ArrayList<String> miercoles = new ArrayList<String>();
    static ArrayList<String> jueves = new ArrayList<String>();
    static ArrayList<String> viernes = new ArrayList<String>();
    static ArrayList<String> sabado = new ArrayList<String>();
    static ArrayList<String> domingo = new ArrayList<String>();
    int numeroDia = 0;
    String[] dia;
    DatabaseReference dbPrediccio;
    String nivel = "5";
    String grupoMuscular = "5";
    Spinner spGrupoMuscular;
    Spinner spNivel;
    Button bguardar;
    private RecyclerView rvEjercicios;
    private List<Ejercicio> llistaEjercicios = new ArrayList<Ejercicio>();
    private EjercicioAdapter ejercicioAdapter;

    String idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_rutina);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Inicialitzem les variables
        tvDia = findViewById(R.id.tvDia);
        dia = getResources().getStringArray(R.array.dia);
        bguardar = findViewById(R.id.bGuargar);
        spNivel = findViewById(R.id.spNivel);
        spGrupoMuscular = findViewById(R.id.spMusculo);
        bguardar = findViewById(R.id.bGuargar);
        rvEjercicios = findViewById(R.id.rvEjercicios);
        tvDia.setText(dia[numeroDia]);
        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            idUsuario=parametros.getString("idUsuario");

        }
        //Hago que en los spinners salgan los arrays
        ArrayAdapter<CharSequence> adapterMusculos = ArrayAdapter.createFromResource(this, R.array.musculos, android.R.layout.simple_spinner_item);
        adapterMusculos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGrupoMuscular.setAdapter(adapterMusculos);


        ArrayAdapter<CharSequence> adapterNivel = ArrayAdapter.createFromResource(this, R.array.dificultad, android.R.layout.simple_spinner_item);
        adapterMusculos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spNivel.setAdapter(adapterNivel);

        //Hago referencia a la base de datos
        dbPrediccio = FirebaseDatabase.getInstance()
                .getReference()
                .child("Ejercicios");
        dbPrediccio.addValueEventListener(this);
        dbPrediccio.addChildEventListener(this);

        rvEjercicios.setLayoutManager(new LinearLayoutManager(this));
        //rvPrediccions.setLayoutManager(new GridLayoutManager(this, 2));
        rvEjercicios.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));
        //rvPrediccions.setAdapter(prediccioAdapter);
        ejercicioAdapter = new EjercicioAdapter(this, llistaEjercicios, dia[numeroDia]);


        //hago el listenner del spinner
        spGrupoMuscular.setOnItemSelectedListener(this);
        spNivel.setOnItemSelectedListener(this);


        //Hago una conversion
        //4


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


        dbPrediccio.addValueEventListener(this);
        dbPrediccio.addChildEventListener(this);


    }

    public void Siguiente(View view) {
        if (numeroDia < 6) {
            numeroDia++;
            ejercicioAdapter = new EjercicioAdapter(this, llistaEjercicios, dia[numeroDia]);
            dbPrediccio.addValueEventListener(this);
            dbPrediccio.addChildEventListener(this);
            tvDia.setText(dia[numeroDia]);
        } else if (numeroDia == 6) {
            bguardar.setText(getResources().getString(R.string.b_guardar3));
            numeroDia++;

        } else {
            Toast.makeText(this,"hola",Toast.LENGTH_LONG);
            Toast.makeText(this,idUsuario,Toast.LENGTH_LONG);
            for (int x = 0; x < lunes.size(); x++) {
               RutinaAcciones.anyadir(dia[0],lunes.get(x),idUsuario);
            }
            for (int x = 0; x < martes.size(); x++) {
                RutinaAcciones.anyadir(dia[1],martes.get(x),idUsuario);
            }
            for (int x = 0; x < miercoles.size(); x++) {
                RutinaAcciones.anyadir(dia[2],miercoles.get(x),idUsuario);
            }
            for (int x = 0; x < jueves.size(); x++) {
                RutinaAcciones.anyadir(dia[3],jueves.get(x),idUsuario);
            }
            for (int x = 0; x < viernes.size(); x++) {
                RutinaAcciones.anyadir(dia[4],viernes.get(x),idUsuario);
            }
            for (int x = 0; x < sabado.size(); x++) {
                RutinaAcciones.anyadir(dia[5],sabado.get(x),idUsuario);
            }
            for (int x = 0; x < domingo.size(); x++) {
                RutinaAcciones.anyadir(dia[6],domingo.get(x),idUsuario);
            }

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static void RutinaTemporal(String dia, String ejercicio) {
        if (dia.equals("Lunes")) {
            lunes.add(ejercicio);
        } else if (dia.equals("Martes")) {
            martes.add(ejercicio);
        } else if (dia.equals("Miercoles")) {
            miercoles.add(ejercicio);
        } else if (dia.equals("Jueves")) {
            jueves.add(ejercicio);
        } else if (dia.equals("Viernes")) {
            viernes.add(ejercicio);
        } else if (dia.equals("Sabado")) {
            sabado.add(ejercicio);
        } else if (dia.equals("Domingo")) {
            domingo.add(ejercicio);
        }
    }

    public static void BorrarRutinaTemporal(String dia, String ejercicio) {
        if (dia.equals("Lunes")) {
            for (int x = 0; x < lunes.size(); x++) {
                if (lunes.get(x) == ejercicio) {
                    lunes.remove(x);

                }
            }
        } else if (dia.equals("Martes")) {
            for (int x = 0; x < jueves.size(); x++) {
                if (martes.get(x) == ejercicio) {
                    martes.remove(x);

                }
            }
        } else if (dia.equals("Miercoles")) {
            for (int x = 0; x < miercoles.size(); x++) {
                if (miercoles.get(x) == ejercicio) {
                    miercoles.remove(x);

                }
            }
        } else if (dia.equals("Jueves")) {
            for (int x = 0; x < jueves.size(); x++) {
                if (jueves.get(x) == ejercicio) {
                    jueves.remove(x);

                }
            }
        } else if (dia.equals("Viernes")) {
                for (int x = 0; x < viernes.size(); x++) {
                    if (viernes.get(x) == ejercicio) {
                        viernes.remove(x);

                    }
                }

            }
        else if (dia.equals("Sabado")) {
            for (int x = 0; x < sabado.size(); x++) {
                if (sabado.get(x) == ejercicio) {
                    sabado.remove(x);

                }
            }

        }
        else if (dia.equals("Domingo")) {
            for (int x = 0; x < domingo.size(); x++) {
                if (domingo.get(x) == ejercicio) {
                    domingo.remove(x);

                }
            }

        }

        }
    }
