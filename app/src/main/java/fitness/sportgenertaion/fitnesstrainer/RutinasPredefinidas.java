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

import fitness.sportgenertaion.fitnesstrainer.Classes.RutinaOpcionalAdapter;
import fitness.sportgenertaion.fitnesstrainer.Classes.RutinaPredefinida;

public class RutinasPredefinidas extends AppCompatActivity {


    String fechaRecuperada;
    DatabaseReference dbDiaHistorial;
    RecyclerView rvMostraRutinasPredeterminadas;
    List<RutinaPredefinida> llistaRutina = new ArrayList<RutinaPredefinida>();
    RutinaOpcionalAdapter rutinaAdapter;//rutinaopcionalAdapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutinas_predefinidas);


        rvMostraRutinasPredeterminadas = findViewById(R.id.rvMostraRutinasPredeterminadas);

        rvMostraRutinasPredeterminadas.setLayoutManager(new LinearLayoutManager(this));
        //rvPrediccions.setLayoutManager(new GridLayoutManager(this, 2));
        rvMostraRutinasPredeterminadas.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));
        rutinaAdapter = new RutinaOpcionalAdapter(this, llistaRutina);


        dbDiaHistorial = FirebaseDatabase.getInstance()
                .getReference()
                .child("Ejercicios-predefinidos");

        dbDiaHistorial.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {


                llistaRutina.removeAll(llistaRutina);
                for (DataSnapshot element : snapshot.getChildren()) {
                    Toast.makeText(getApplicationContext(), snapshot.getChildrenCount() + "", Toast.LENGTH_SHORT).show();
                    RutinaPredefinida rutina = new RutinaPredefinida(element.getKey().toString());
                    llistaRutina.add(rutina);

                }
                rvMostraRutinasPredeterminadas.setAdapter(rutinaAdapter);
                rvMostraRutinasPredeterminadas.scrollToPosition(llistaRutina.size() - 1);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
