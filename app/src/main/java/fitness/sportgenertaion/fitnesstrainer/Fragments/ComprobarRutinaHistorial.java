package fitness.sportgenertaion.fitnesstrainer.Fragments;

import android.content.Intent;
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
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import fitness.sportgenertaion.fitnesstrainer.Classes.DateAcciones;
import fitness.sportgenertaion.fitnesstrainer.Classes.IdUsuario;
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
                .child("users/" + IdUsuario.getIdUsuario() + "/FechaRutina");
        dbUltimaModificacion.addValueEventListener(this);
        dbUltimaModificacion.addChildEventListener(this);


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

        for (DataSnapshot element : dataSnapshot.getChildren()) {
            diaRutina = Integer.parseInt(element.child("dia").getValue().toString());
            mesRutina = Integer.parseInt(element.child("mes").getValue().toString());
            anyoRutina = Integer.parseInt(element.child("anyo").getValue().toString());

        }
        compararRutina();


    }

    public int diasHastaLunes(String diaSemana) {
        int diferencia = 0;

        if (diaSemana.equals("TUESDAY")) {
            diferencia = 1;
        } else if (diaSemana.equals("WEDNESDAY")) {
            diferencia = 2;
        } else if (diaSemana.equals("THURSDAY")) {
            diferencia = 3;
        } else if (diaSemana.equals("FRIDAY")) {
            diferencia = 4;
        } else if (diaSemana.equals("SATURDAY")) {
            diferencia = 5;
        } else if (diaSemana.equals("SUNDAY")) {
            diferencia = 6;
        }
        return diferencia;
    }

    public void compararRutina(){

        DateAcciones fechaRutina = new DateAcciones(diaRutina, mesRutina, anyoRutina);

        String diaSemana="MONDAY";
        int diferencia = 0;
        try {
            diaSemana = fechaRutina.diaSemana();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.add(Calendar.DAY_OF_YEAR, -2);

        Toast.makeText(getContext(),cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.DAY_OF_MONTH),Toast.LENGTH_LONG).show();

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
