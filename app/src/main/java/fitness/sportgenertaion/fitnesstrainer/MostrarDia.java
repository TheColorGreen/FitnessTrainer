package fitness.sportgenertaion.fitnesstrainer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fitness.sportgenertaion.fitnesstrainer.Classes.IdUsuario;
import fitness.sportgenertaion.fitnesstrainer.Classes.MostrarDiaAdapter;
import fitness.sportgenertaion.fitnesstrainer.Classes.Rutina;

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
//Hagafa la fecha que se li ha pasat desde el fragment historialRutina
        fechaRecuperada = getIntent().getExtras().getString("fecha");

        rvEjercicios = findViewById(R.id.rvDiaHistorial);

        rvEjercicios.setLayoutManager(new LinearLayoutManager(this));
        rvEjercicios.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));
        rutinaAdapter = new MostrarDiaAdapter(this, llistaRutina);


        dbDiaHistorial = FirebaseDatabase.getInstance()
                .getReference()
                .child("users/" + IdUsuario.getIdUsuario() + "/Historial/" + fechaRecuperada);
        dbDiaHistorial.addListenerForSingleValueEvent(new ValueEventListener() {
            //Comprova els ejercicis fets aquell dia i desprees en el adapter fica la imatje depenent de el que hagis fet
            @Override
            public void onDataChange(DataSnapshot snapshot) {


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
