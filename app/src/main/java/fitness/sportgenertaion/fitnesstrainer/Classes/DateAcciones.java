package fitness.sportgenertaion.fitnesstrainer.Classes;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Carlos on 16/05/2018.
 */

public class DateAcciones {
    int dia;
    int mes;
    int anyo;

    public DateAcciones(int dia, int mes, int anyo) {
        this.dia = dia;
        this.mes = mes;
        this.anyo = anyo;

    }

    public int CompararFechas() {
        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día
        java.util.Date hoy = new Date(); //Fecha de hoy
        Calendar calendar = new GregorianCalendar(anyo, mes - 1, dia);
        java.sql.Date fecha = new java.sql.Date(calendar.getTimeInMillis());

        long diferencia = (hoy.getTime() - fecha.getTime()) / MILLSECS_PER_DAY;

        int resultado= (int)diferencia;
       return resultado;
    }



    public String diaSemana() throws ParseException {
        String month = String.valueOf(mes);
        String day = String.valueOf(dia);
        String year = String.valueOf(anyo);
        String inputDateStr = String.format("%s/%s/%s", day, month, year);
        Date inputDate = new SimpleDateFormat("dd/MM/yyyy").parse(inputDateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inputDate);
        String dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US).toUpperCase();

        return dayOfWeek;

    }


}
