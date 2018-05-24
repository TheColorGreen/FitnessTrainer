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
//Serveix per mirar el dia de la semana, comprarar feches...
public class DateAcciones {
    int dia;
    int mes;
    int anyo;
//Constructor
    public DateAcciones(int dia, int mes, int anyo) {
        this.dia = dia;
        this.mes = mes;
        this.anyo = anyo;

    }
//Mira els dies de diferencia que existeixen de la echa que haiguis ficat a la fecha en la que estem actualment
    public int CompararFechas() {
        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día
        java.util.Date hoy = new Date(); //Fecha de hoy
        Calendar calendar = new GregorianCalendar(anyo, mes - 1, dia);
        java.sql.Date fecha = new java.sql.Date(calendar.getTimeInMillis());

        long diferencia = (hoy.getTime() - fecha.getTime()) / MILLSECS_PER_DAY;

        int resultado = (int) diferencia;
        return resultado;
    }

//Et diu el dia de la semana de la fecha
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

    //Te dice los dias que han pasado desde el ultimo dia
    public int diasHastaLunes() throws ParseException {

        String diaSemana= diaSemana();
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

    public static void ActualizarFechaRutina(){
        Date fechas = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechas);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

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
        try {
            ActualizarHistorial.anyadirHistorial();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
