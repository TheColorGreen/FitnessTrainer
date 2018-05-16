package fitness.sportgenertaion.fitnesstrainer;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import fitness.sportgenertaion.fitnesstrainer.Classes.IdUsuario;
import fitness.sportgenertaion.fitnesstrainer.Classes.ModificarAdapter;
import fitness.sportgenertaion.fitnesstrainer.Classes.Rutina;
import fitness.sportgenertaion.fitnesstrainer.Classes.RutinaAcciones;

public class ModificarRutina extends AppCompatActivity implements ValueEventListener, ChildEventListener, AdapterView.OnItemSelectedListener, View.OnClickListener {
    static String idUsuario;
    static String dia = "Lunes";
    static ArrayList<String> ejercicios = new ArrayList<String>();
    DatabaseReference dbPrediccio;
    String nivel = "5";
    String grupoMuscular = "5";
    Spinner spGrupoMuscular;
    Spinner spNivel;
    Button bguardar;
    private RecyclerView rvEjercicios;
    private List<Ejercicio> llistaEjercicios = new ArrayList<Ejercicio>();
    private ModificarAdapter modificarAdapter;
    private List<String> llistaEjerciciosPuestos = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_rutina);
        dia = new String();
        Bundle parametros = this.getIntent().getExtras();
        idUsuario= IdUsuario.getIdUsuario();
        if (parametros != null) {

            dia = parametros.getString("dia");
            llistaEjerciciosPuestos = (ArrayList<String>) getIntent().getSerializableExtra("miLista");
        }

        bguardar = findViewById(R.id.bGuargar);
        spNivel = findViewById(R.id.spNivel);
        spGrupoMuscular = findViewById(R.id.spMusculo);
        bguardar = findViewById(R.id.bGuargar);
        rvEjercicios = findViewById(R.id.rvEjercicios);


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
        modificarAdapter = new ModificarAdapter(this, llistaEjercicios);


        //hago el listenner del spinner
        spGrupoMuscular.setOnItemSelectedListener(this);
        spNivel.setOnItemSelectedListener(this);

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
    public void onDataChange(DataSnapshot dataSnapshot) {
        llistaEjercicios.removeAll(llistaEjercicios);
        for (DataSnapshot element : dataSnapshot.getChildren()) {
            if ((element.child("dificultad").getValue().toString().equals(this.nivel) && (element.child("musculos").getValue().toString().equals(this.grupoMuscular))) || (this.nivel.equals("5") && this.grupoMuscular.equals("5")) || (this.grupoMuscular.equals("5") && element.child("dificultad").getValue().toString().equals(this.nivel)) || this.nivel.equals("5") && element.child("musculos").getValue().toString().equals(this.grupoMuscular)) {
                Boolean comprovacion = true;
                for (int x = 0; x < llistaEjerciciosPuestos.size(); x++) {
                    if (llistaEjerciciosPuestos.get(x).equals(element.getKey().toString())) {
                        comprovacion = false;
                    }
                }
                if (comprovacion) {
                    Ejercicio ejercicio = new Ejercicio(element.getKey().toString(), element.child("descripcion").getValue().toString(), element.child("foto").getValue().toString(), Integer.valueOf(element.child("dificultad").getValue().toString()), Integer.valueOf(element.child("musculos").getValue().toString()));

                    llistaEjercicios.add(ejercicio);
                }
            }
        }
        rvEjercicios.setAdapter(modificarAdapter);
        rvEjercicios.scrollToPosition(llistaEjercicios.size() - 1);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
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

        bguardar.setOnClickListener(this);
        dbPrediccio.addValueEventListener(this);
        dbPrediccio.addChildEventListener(this);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        for (int x = 0; x < ejercicios.size(); x++) {
            RutinaAcciones.anyadir(dia, ejercicios.get(x));
        }

        ejercicios = new ArrayList<String>();

        Intent intent = new Intent(this, Dias.class);
        intent.putExtra("idUsuario", idUsuario);
        startActivity(intent);
    }

    public static void RutinaTemporal(String ejercicio) {
        ejercicios.add(ejercicio);
    }

    public static void BorrarRutinaTemporal(String ejercicio) {

        for (int x = 0; x < ejercicios.size(); x++) {
            if (ejercicios.get(x) == ejercicio) {
                ejercicios.remove(x);

            }
        }


    }
}

