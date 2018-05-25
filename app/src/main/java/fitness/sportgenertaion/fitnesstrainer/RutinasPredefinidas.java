package fitness.sportgenertaion.fitnesstrainer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fitness.sportgenertaion.fitnesstrainer.Classes.Idioma;
import fitness.sportgenertaion.fitnesstrainer.Classes.ModificarAdapter;
import fitness.sportgenertaion.fitnesstrainer.Classes.Rutina;
import fitness.sportgenertaion.fitnesstrainer.Classes.RutinaOpcionalAdapter;
import fitness.sportgenertaion.fitnesstrainer.Classes.RutinaPredefinida;

//Et surten les rutines predefinides del firebase
public class RutinasPredefinidas extends AppCompatActivity implements ValueEventListener {



    private RecyclerView rvEjercicios;
    private List<RutinaPredefinida> llistaEjercicios = new ArrayList<RutinaPredefinida>();
    private RutinaOpcionalAdapter modificarAdapter;
    DatabaseReference dbRutinas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutinas_predefinidas);

//Inicialitzo i creo els listeners de la base de dades
        dbRutinas = FirebaseDatabase.getInstance()
                .getReference()
                .child("Ejercicios Predeterminados-"+ Idioma.getIdioma());
        dbRutinas.addValueEventListener(this);


        rvEjercicios=findViewById(R.id.rvMostraRutinasPredeterminadas);
        rvEjercicios.setLayoutManager(new LinearLayoutManager(this));
        //rvPrediccions.setLayoutManager(new GridLayoutManager(this, 2));
        rvEjercicios.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));

        //rvPrediccions.setAdapter(prediccioAdapter);
        modificarAdapter = new RutinaOpcionalAdapter(this, llistaEjercicios);
    }



    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        //Guardo les rutines predefinides y les envio al lisener
        llistaEjercicios.removeAll(llistaEjercicios);
        for (DataSnapshot element : dataSnapshot.getChildren()) {

            RutinaPredefinida rutinaPredefinida=new RutinaPredefinida(element.getKey().toString());

            llistaEjercicios.add(rutinaPredefinida);
        }
        rvEjercicios.setAdapter(modificarAdapter);
        rvEjercicios.scrollToPosition(llistaEjercicios.size() - 1);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
