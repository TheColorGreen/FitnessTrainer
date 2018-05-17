package fitness.sportgenertaion.fitnesstrainer.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import fitness.sportgenertaion.fitnesstrainer.Classes.DateAcciones;
import fitness.sportgenertaion.fitnesstrainer.Classes.IdUsuario;
import fitness.sportgenertaion.fitnesstrainer.Classes.Rutina;
import fitness.sportgenertaion.fitnesstrainer.Classes.RutinaAcciones;
import fitness.sportgenertaion.fitnesstrainer.Dias;
import fitness.sportgenertaion.fitnesstrainer.MainActivity;
import fitness.sportgenertaion.fitnesstrainer.R;


public class ComprobarRutinaHistorial extends Fragment implements ValueEventListener, ChildEventListener {
    // TODO: Rename parameter arguments, choose names that match
    String idUsuario;
    DatabaseReference dbUltimaModificacion;
    // TODO: Rename and change types of parameters
    static int year;
    static int month;
    static int day;

    static int anyoRutina;
    static int mesRutina;
    static int diaRutina;
    static Date fechas;
    static Calendar cal;

    public ComprobarRutinaHistorial() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rutina_aleatoria, container, false);
        idUsuario = IdUsuario.getIdUsuario();
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fbExit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), MainActivity.class);
                startActivity(in);
            }
        });
        fechas = new Date();
        cal = Calendar.getInstance();
        cal.setTime(fechas);
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);


        dbUltimaModificacion = FirebaseDatabase.getInstance()
                .getReference()
                .child("users" + "/" + IdUsuario.getIdUsuario() + "/FechaRutina");

        dbUltimaModificacion.addListenerForSingleValueEvent(this);
        dbUltimaModificacion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot element : snapshot.getChildren()) {

                    if (element.getKey().toString().equals("dia")) {
                        diaRutina = Integer.valueOf(element.getValue().toString());
                    } else if (element.getKey().toString().equals("mes")) {
                        mesRutina = Integer.valueOf(element.getValue().toString());
                    } else {
                        anyoRutina = Integer.valueOf(element.getValue().toString());
                    }

                }


                try {
                    compararRutina();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
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


    }


    public void compararRutina() throws ParseException {

        DateAcciones fechaRutina = new DateAcciones(diaRutina, mesRutina, anyoRutina);
        DateAcciones fechaActual = new DateAcciones(day, month + 1, year);

        String diaSemanaActual = "MONDAY";

        int diferenciaActual;

        diferenciaActual = fechaActual.diasHastaLunes();

        if (fechaRutina.CompararFechas() - diferenciaActual != 0) {
           // anyadirHistorial();

        }
        anyadirHistorial();

        //Toast.makeText(getContext(), fechaRutina.CompararFechas() - diferenciaActual + "", Toast.LENGTH_LONG).show();

    }

    public void anyadirHistorial() {


    }

    public void resetearRutina() {
        final String[] diasSemana = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};

        for (int dia = 0; dia < 7; dia++) {
            final int dias = dia;
            dbUltimaModificacion = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("users" + "/" + IdUsuario.getIdUsuario() + "/Rutina/" + diasSemana[dia]);

            dbUltimaModificacion.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot element : snapshot.getChildren()) {

                        RutinaAcciones.anyadir(diasSemana[dias], element.getKey().toString());

                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
