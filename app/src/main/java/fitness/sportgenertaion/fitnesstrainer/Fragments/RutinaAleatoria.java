package fitness.sportgenertaion.fitnesstrainer.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import fitness.sportgenertaion.fitnesstrainer.Classes.ActualizarHistorial;
import fitness.sportgenertaion.fitnesstrainer.Classes.DateAcciones;
import fitness.sportgenertaion.fitnesstrainer.Classes.IdUsuario;
import fitness.sportgenertaion.fitnesstrainer.Classes.RutinaAcciones;
import fitness.sportgenertaion.fitnesstrainer.Dias;
import fitness.sportgenertaion.fitnesstrainer.R;


@SuppressLint("ValidFragment")
public class RutinaAleatoria extends Fragment implements ValueEventListener, ChildEventListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    String idUsuario;
    DatabaseReference dbEjercicios;
    public List<String> llistaEjercicios;
    public String[] dias = new String[]{"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};


    public RutinaAleatoria() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rutina_aleatoria, container, false);
        idUsuario = IdUsuario.getIdUsuario();

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
        RutinaAcciones.eliminarRutina();
        for (int a = 0; a < 7; a++) {
            ejerciciosDia = r.nextInt(5 - 1 + 1) + 1;
            for (int b = 0; b < ejerciciosDia; b++) {
                ejercicioRandom = r.nextInt(numeroDeEjercicios - 0 + 1) + 0;
                RutinaAcciones.anyadir(dias[a], llistaEjercicios.get(ejercicioRandom));

            }
        }


        Date fechas = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechas);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DateAcciones fecha = new DateAcciones(day, month, year);


        try {
            cal.add(Calendar.DAY_OF_YEAR, -fecha.diasHastaLunes());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        RutinaAcciones.anyadirFecha(day, month + 1, year);
        try {
            ActualizarHistorial.anyadirHistorial();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(getContext(), Dias.class);
        startActivity(intent);
    }


    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}