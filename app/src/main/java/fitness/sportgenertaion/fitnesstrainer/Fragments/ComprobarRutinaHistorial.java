package fitness.sportgenertaion.fitnesstrainer.Fragments;

import android.os.Bundle;
import android.os.Handler;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import fitness.sportgenertaion.fitnesstrainer.Classes.DateAcciones;
import fitness.sportgenertaion.fitnesstrainer.Classes.HistorialAcciones;
import fitness.sportgenertaion.fitnesstrainer.Classes.IdUsuario;
import fitness.sportgenertaion.fitnesstrainer.Classes.RutinaAcciones;
import fitness.sportgenertaion.fitnesstrainer.R;


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
        View rootView = inflater.inflate(R.layout.fragment_comprobar_rutina_historial, container, false);
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
        if (fechaRutina.CompararFechas() - diferenciaActual != 0 ) {
            resetearRutina();


        } else {
            Handler handler = new Handler();

//Llamamos al método postDelayed
            handler.postDelayed(new Runnable() {
                public void run() {
                  cerrarFragment();
                }
            }, 3000);


        }


    }

    public void cerrarFragment(){
        getFragmentManager().beginTransaction().remove(this).commit();
    }
    public void anyadirHistorial() throws ParseException {
        final String[] diasSemana = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};

        int month;
        int day;
        int year;
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        final DateAcciones fecha = new DateAcciones(day, month, year);

        for (int dia = 0; dia < 7; dia++) {
            if( dia==fecha.diasHastaLunes()){
                dbUltimaModificacion = FirebaseDatabase.getInstance()
                        .getReference()
                        .child("users/" + IdUsuario.getIdUsuario() + "/Rutina/" + diasSemana[dia]);

                String dia2 = anyoRutina + "-" + mesRutina + "-" + diaRutina;


                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                final Date fechaHistorial = sdf.parse(dia2);

                final Calendar calendar = Calendar.getInstance();


                calendar.setTime(fechaHistorial);
                calendar.add(Calendar.DAY_OF_YEAR, dia);
                dia2 = calendar.get(Calendar.DAY_OF_MONTH) +"-"+ calendar.get(Calendar.MONTH)+"-" + calendar.get(Calendar.YEAR) ;
                final String finalDia = dia2;
                dbUltimaModificacion.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot element : snapshot.getChildren()) {
                            HistorialAcciones.anyadir(finalDia, element.getKey().toString(),Boolean.parseBoolean(element.getValue().toString()));

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }


        compararRutina();


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
        Handler handler = new Handler();

//Llamamos al método postDelayed
        handler.postDelayed(new Runnable() {
            public void run() {
                cerrarFragment();
            }
        }, 3000);

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }


}
