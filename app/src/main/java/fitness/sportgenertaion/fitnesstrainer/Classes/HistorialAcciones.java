package fitness.sportgenertaion.fitnesstrainer.Classes;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Carlos on 17/05/2018.
 */
//Serveix per anyadir y eliminar exercicis y dies de l'historial
public class HistorialAcciones {
    public static void anyadir(String dia, String ejercicio, boolean echo) {
        DatabaseReference dbRutina = FirebaseDatabase.getInstance()
                .getReference()
                .child("users/" + IdUsuario.getIdUsuario() + "/Historial" + "/" + dia + "/" + ejercicio);
        dbRutina.setValue(echo);
    }

    public static void BorrarDia(String dia) {
        DatabaseReference dbRutina = FirebaseDatabase.getInstance()
                .getReference()
                .child("users/" + IdUsuario.getIdUsuario() + "/Historial" + "/" + dia);
        dbRutina.removeValue();
    }
}
