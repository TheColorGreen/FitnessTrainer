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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import fitness.sportgenertaion.fitnesstrainer.Classes.DateAcciones;
import fitness.sportgenertaion.fitnesstrainer.Classes.Historial;
import fitness.sportgenertaion.fitnesstrainer.Classes.HistorialAcciones;
import fitness.sportgenertaion.fitnesstrainer.Classes.IdUsuario;
import fitness.sportgenertaion.fitnesstrainer.Classes.Rutina;
import fitness.sportgenertaion.fitnesstrainer.Classes.RutinaAcciones;
import fitness.sportgenertaion.fitnesstrainer.Dias;
import fitness.sportgenertaion.fitnesstrainer.MainActivity;
import fitness.sportgenertaion.fitnesstrainer.R;

import static java.util.logging.Logger.global;


public class ComprobarRutinaHistorial extends Fragment implements ValueEventListener, ChildEventListener {
    // TODO: Rename parameter arguments, choose names that match
    String idUsuario;
    DatabaseReference dbUltimaModificacion;
    // TODO: Rename and change types of parameters
    static int year;
    static int month;
    static int day;
    static boolean nulo ;
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
        nulo=false;
        idUsuario = IdUsuario.getIdUsuario();

        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);


        dbUltimaModificacion = FirebaseDatabase.getInstance()
                .getReference()
                .child("users" + "/" + IdUsuario.getIdUsuario() + "/FechaRutina");

        dbUltimaModificacion.addListenerForSingleValueEvent(new ValueEventListener() {
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
                    anyadirHistorial();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        dbUltimaModificacion.onDisconnect().cancel();

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
            resetearRutina();


        } else {
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);

        }


    }

    public void anyadirHistorial() throws ParseException {
        final String[] diasSemana = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};
        int contador = 0;
        for (int dia = 0; dia < 7; dia++) {

            final int dias = dia;
            dbUltimaModificacion = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("users/" + IdUsuario.getIdUsuario() + "/Rutina/" + diasSemana[dia]);

            final int finalContador = contador;

            final String dia2 = anyoRutina + "-" + mesRutina + "-" + diaRutina;


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            final Calendar calendar = Calendar.getInstance();

            try {
                Date fecha = sdf.parse(dia2);


                calendar.setTime(fecha);
                calendar.add(Calendar.DAY_OF_YEAR, finalContador);

            } catch (ParseException e) {
                e.printStackTrace();
            }


            dbUltimaModificacion.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    String dia3 = calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.YEAR);
                    int month;
                    int day;
                    int year;
                    year = cal.get(Calendar.YEAR);
                    month = cal.get(Calendar.MONTH) + 1;
                    day = cal.get(Calendar.DAY_OF_MONTH);
                    DateAcciones fecha = new DateAcciones(day, month, year);

                    try {
                        if (finalContador >= fecha.diasHastaLunes() || comprovarNulo(dia3)) {
                            HistorialAcciones.BorrarDia(dia3);
                            for (DataSnapshot element : snapshot.getChildren()) {


                                HistorialAcciones.anyadir(dia3, element.getKey().toString(), Boolean.parseBoolean(element.getValue().toString()));

                            }

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            contador++;

        }

        compararRutina();


    }

    public boolean comprovarNulo(String fecha) {
        DatabaseReference dbUltimaModificacion2;
        dbUltimaModificacion2 = FirebaseDatabase.getInstance()
                .getReference()
                .child("users/" + IdUsuario.getIdUsuario() + "/Historial/" + fecha);


        dbUltimaModificacion2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() == 0) {
                    nulo = true;
                    Toast.makeText(getContext(),nulo+"",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(),nulo+"",Toast.LENGTH_LONG).show();
            }
        });
        Toast.makeText(getContext(),nulo+"",Toast.LENGTH_LONG).show();

        return nulo;


    }


    public void resetearRutina() {
        final String[] diasSemana = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};

        for (int dia = 0; dia < 7; dia++) {
            final int dias = dia;
            dbUltimaModificacion = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("users" + "/" + IdUsuario.getIdUsuario() + "/Rutina/" + diasSemana[dia]);

            dbUltimaModificacion.addListenerForSingleValueEvent(new ValueEventListener() {
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

        Date fechas = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechas);
        int year;
        int month;
        int day;
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);

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
        getFragmentManager().beginTransaction().remove(this).commit();

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }


}
