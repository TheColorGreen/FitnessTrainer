package fitness.sportgenertaion.fitnesstrainer.Classes;

/**
 * Created by Carlos on 25/04/2018.
 */

//Reopresenta el Ejercicio de firebase, esta feta per accedir a nom, dificultad...
public class Ejercicio {
    // Variables
    private String nombre;
    private String descripcion;
    private String foto;
    private int dificultad;
private boolean selected=false;
    private int musculos;

    public Ejercicio(String nombre, String descripcion, String foto, int dificultad, int musculos) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.foto = foto;
        this.dificultad = dificultad;
        this.musculos = musculos;


    }

    // Constructor per desar les dades (sense la data)

public boolean isSelected(){
       return selected;
}
public void setSelected(boolean selected){
    this.selected=selected;
}
    public String getDescripcion() {
        return this.descripcion;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getFoto() {
        return this.foto;
    }

    public int getDificultad() {
        return this.dificultad;
    }

    public int getMusculos() {
        return this.musculos;
    }

    @Override
    public String toString() {
        return "Ejercicios{" +
                "descripcion='" + getDescripcion() + '\'' +
                ", foto='" + getFoto() + '\'' +
                ", musuculos=" + getMusculos() +
                ", dificultad=" + getDificultad() +
                '}';
    }

}