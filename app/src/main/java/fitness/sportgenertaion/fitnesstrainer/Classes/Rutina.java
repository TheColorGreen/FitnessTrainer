package fitness.sportgenertaion.fitnesstrainer.Classes;

/**
 * Created by Carlos on 27/04/2018.
 */

public class Rutina {

    private boolean selected =false;
boolean echo;
    String ejercicio;


    public Rutina(boolean echo, String ejercicio) {
        this.echo = echo;

        this.ejercicio = ejercicio;
    }

    public boolean getEcho() {
            return echo;
    }
    public String getEjercicio() {
        return ejercicio;
    }
    public boolean isSelected(){
        return selected;
    }
    public void setSelected(boolean selected){
        this.selected=selected;
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
