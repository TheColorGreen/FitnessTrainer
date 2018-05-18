package fitness.sportgenertaion.fitnesstrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fitness.sportgenertaion.fitnesstrainer.Classes.HistorialAcciones;
import fitness.sportgenertaion.fitnesstrainer.Classes.IdUsuario;
import fitness.sportgenertaion.fitnesstrainer.Classes.MostrarDiaAdapter;
import fitness.sportgenertaion.fitnesstrainer.Classes.Rutina;
import fitness.sportgenertaion.fitnesstrainer.Classes.RutinaAdapter;

public class MostrarDia extends AppCompatActivity {
    String fechaRecuperada;
    DatabaseReference dbDiaHistorial;
    RecyclerView rvEjercicios;
    List<Rutina> llistaRutina = new ArrayList<Rutina>();
    MostrarDiaAdapter rutinaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_dia);

        fechaRecuperada = getIntent().getExtras().getString("fecha");

        rvEjercicios = findViewById(R.id.rvDiaHistorial);

        rvEjercicios.setLayoutManager(new LinearLayoutManager(this));
        //rvPrediccions.setLayoutManager(new GridLayoutManager(this, 2));
        rvEjercicios.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));
        rutinaAdapter = new MostrarDiaAdapter(this, llistaRutina );





        dbDiaHistorial = FirebaseDatabase.getInstance()
                .getReference()
                .child("users/" + IdUsuario.getIdUsuario() + "/Historial/" + fechaRecuperada);
        dbDiaHistorial.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Toast.makeText(getApplicationContext(), fechaRecuperada, Toast.LENGTH_LONG).show();

                llistaRutina.removeAll(llistaRutina);
                for (DataSnapshot element : snapshot.getChildren()) {

                    Rutina rutina = new Rutina(Boolean.parseBoolean(element.getValue().toString()), element.getKey().toString());
                    llistaRutina.add(rutina);

                }
                rvEjercicios.setAdapter(rutinaAdapter);
                rvEjercicios.scrollToPosition(llistaRutina.size() - 1);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
