package fitness.sportgenertaion.fitnesstrainer.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Random;

import fitness.sportgenertaion.fitnesstrainer.Classes.Ejercicio;
import fitness.sportgenertaion.fitnesstrainer.Classes.Rutina;
import fitness.sportgenertaion.fitnesstrainer.Classes.RutinaAcciones;
import fitness.sportgenertaion.fitnesstrainer.CrearRutinas;
import fitness.sportgenertaion.fitnesstrainer.Dias;
import fitness.sportgenertaion.fitnesstrainer.MainActivity;
import fitness.sportgenertaion.fitnesstrainer.R;


@SuppressLint("ValidFragment")
public class RutinaAleatoria extends Fragment implements ValueEventListener, ChildEventListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    String idUsuario;
    DatabaseReference dbEjercicios;
    public List<String> llistaEjercicios;
    public String[] dias = new String[]{"Lunes", "Martes", "Miercoles", "Jueves","Viernes", "Sabado", "Domingo"};

    @SuppressLint("ValidFragment")
    public RutinaAleatoria(String idUsuario) {
        this.idUsuario = idUsuario;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rutina_aleatoria, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fbExit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), MainActivity.class);
                startActivity(in);
            }
        });
        llistaEjercicios = new ArrayList<String>();

        dbEjercicios = FirebaseDatabase.getInstance()
                .getReference()
                .child("Ejercicios");
        dbEjercicios.addValueEventListener(this);
        dbEjercicios.addChildEventListener(this);
        return rootView;
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
        int numeroDeEjercicios = -1;
        int ejerciciosDia = 0;
        int ejercicioRandom = 0;
        Random r = new Random();
        for (DataSnapshot element : dataSnapshot.getChildren()) {
            llistaEjercicios.add(element.getKey().toString());
            numeroDeEjercicios++;
        }
        RutinaAcciones.eliminarRutina(idUsuario);
        for (int a = 0; a < 7; a++) {
            ejerciciosDia = r.nextInt(5 - 1 + 1) + 1;
            for (int b = 0; b < ejerciciosDia; b++) {
                ejercicioRandom = r.nextInt(numeroDeEjercicios - 0 + 1) + 0;
                RutinaAcciones.anyadir(dias[a],llistaEjercicios.get(ejercicioRandom),idUsuario);

            }
        }
       Intent intent = new Intent(getContext(), Dias.class);
        intent.putExtra( "idUsuario",idUsuario);
        startActivity(intent);
    }


    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}