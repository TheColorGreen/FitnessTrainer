package fitness.sportgenertaion.fitnesstrainer.Classes;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Carlos Zambudio on 10/05/2018.
 */

public  class RutinaAcciones {
    public static void anyadir(String dia, String ejercicio) {
        DatabaseReference dbRutina = FirebaseDatabase.getInstance()
                .getReference()
                .child("users/" + IdUsuario.getIdUsuario() + "/Rutina"+"/" + dia+"/" + ejercicio );
        dbRutina.setValue(false);
    }

    public static void eliminarRutina() {
        DatabaseReference dbRutina = FirebaseDatabase.getInstance()
                .getReference()
                .child("users/" + IdUsuario.getIdUsuario() + "/Rutina");
        dbRutina.removeValue();
    }

    public static void anyadirFecha(int dia,int mes,int anyo) {
        DatabaseReference dbRutina = FirebaseDatabase.getInstance()
                .getReference()
                .child("users/" + IdUsuario.getIdUsuario() + "/FechaRutina");
        dbRutina.child("/dia" ).setValue(dia);
        dbRutina.child("/mes" ).setValue(mes);
        dbRutina.child("/anyo" ).setValue(anyo);
    }

    public static void BorrarEjercicios(String dia, String ejercicio) {
        DatabaseReference dbRutina = FirebaseDatabase.getInstance()
                .getReference()
                .child("users/" + IdUsuario.getIdUsuario() + "/Rutina" + "/" + dia + "/" + ejercicio);
        dbRutina.removeValue();
    }
    public static void PonerCheck(String dia, String ejercicio){
        DatabaseReference dbRutina = FirebaseDatabase.getInstance()
                .getReference()
                .child("users/" + IdUsuario.getIdUsuario() + "/Rutina");
        dbRutina.child("/" + dia + "/" + ejercicio).setValue(true);
    }

}
