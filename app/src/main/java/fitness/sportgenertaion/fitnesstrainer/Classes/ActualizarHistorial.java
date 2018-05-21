package fitness.sportgenertaion.fitnesstrainer.Classes;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Carlos on 21/05/2018.
 */

public class ActualizarHistorial {
    static Calendar cal = Calendar.getInstance();
    public static void anyadirHistorial() throws ParseException {
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
               DatabaseReference dbUltimaModificacion = FirebaseDatabase.getInstance()
                        .getReference()
                        .child("users/" + IdUsuario.getIdUsuario() + "/Rutina/" + diasSemana[dia]);


                dbUltimaModificacion.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        String diaHoy=cal.get(Calendar.DAY_OF_MONTH)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.YEAR);
                        HistorialAcciones.BorrarDia(diaHoy);
                        for (DataSnapshot element : snapshot.getChildren()) {

                            HistorialAcciones.anyadir(diaHoy, element.getKey().toString(),Boolean.parseBoolean(element.getValue().toString()));

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }




    }
}
