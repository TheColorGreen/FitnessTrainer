package fitness.sportgenertaion.fitnesstrainer.Classes;

/**
 * Created by Carlos on 15/05/2018.
 */
//Serveix per guardar el id de l'usuari i aixi accedir a la base de dades que li toca
public class IdUsuario {
    public static String idUsuario;

    public static void setIdUsuario(String idUsuarios) {
        idUsuario = idUsuarios;

    }

    public static String getIdUsuario() {
        return idUsuario;
    }


}
