package fitness.sportgenertaion.fitnesstrainer.Classes;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Carlos Zambudio on 10/05/2018.
 */

public  class RutinaAcciones {
    public static void anyadir(String dia, String ejercicio, String idUsuario) {
        DatabaseReference dbRutina = FirebaseDatabase.getInstance()
                .getReference()
                .child("users/" + idUsuario + "/Rutina"+"/" + dia+"/" + ejercicio );
        dbRutina.setValue(false);
    }

    public static void eliminarRutina(String idUsuario) {
        DatabaseReference dbRutina = FirebaseDatabase.getInstance()
                .getReference()
                .child("users/" + idUsuario + "/Rutina");
        dbRutina.removeValue();
    }

    public static void BorrarEjercicios(String dia, String ejercicio, String idUsuario) {
        DatabaseReference dbRutina = FirebaseDatabase.getInstance()
                .getReference()
                .child("users/" + idUsuario + "/Rutina" + "/" + dia + "/" + ejercicio);
        dbRutina.removeValue();
    }
    public static void PonerCheck(String dia, String ejercicio, String idUsuario){
        DatabaseReference dbRutina = FirebaseDatabase.getInstance()
                .getReference()
                .child("users/" + idUsuario + "/Rutina");
        dbRutina.child("/" + dia + "/" + ejercicio).setValue(true);
    }
}
