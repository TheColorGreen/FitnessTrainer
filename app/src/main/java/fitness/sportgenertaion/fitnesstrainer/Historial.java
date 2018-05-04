package fitness.sportgenertaion.fitnesstrainer;

/**
 * Created by Carlos on 27/04/2018.
 */

public class Historial {

    boolean echo;

    String ejercicio;


    public Historial(boolean echo, String ejercicio) {
        this.echo = echo;

        this.ejercicio = ejercicio;
    }

    public boolean getEcho() {
        return echo;
    }
    public String getEjercicio() {
        return ejercicio;
    }


    @Override
    public String toString() {
        return "Historial{" +
                ", ejercicio='" + getEjercicio() + '\'' +
                "{" +
                "echo=" + getEcho() +
                "}}";
    }
}

