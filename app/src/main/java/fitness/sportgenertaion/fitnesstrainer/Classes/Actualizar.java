package fitness.sportgenertaion.fitnesstrainer.Classes;

/**
 * Created by Carlos on 18/05/2018.
 */

//Serveix per saber si s'ha actualitzat l'historial
public class Actualizar {
  private static  Boolean actualizado=false;

    public static void setActualizado(Boolean actualizados){
        actualizado=actualizados;

    }
    public static Boolean getActualizado(){
        return actualizado;
    }


}

