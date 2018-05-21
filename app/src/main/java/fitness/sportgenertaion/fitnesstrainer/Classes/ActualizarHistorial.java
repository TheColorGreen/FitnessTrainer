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

    public void anyadirHistorial() throws ParseException {
        final String[] diasSemana = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};

        int month;
        int day;
        int year;
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        final DateAcciones fecha = new DateAcciones(day, month, year);

        for (int dia = 0; dia < 7; dia++) {
            if( dia==fecha.diasHastaLunes()){
               DatabaseReference dbUltimaModificacion = FirebaseDatabase.getInstance()
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
}
