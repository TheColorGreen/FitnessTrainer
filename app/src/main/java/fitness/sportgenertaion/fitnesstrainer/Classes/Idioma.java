package fitness.sportgenertaion.fitnesstrainer.Classes;
//Serveix per saber quin idioma te l'aplicació i aixi accedir a la base de dades convenient
public class Idioma {
    static String idioma = "es";

    public static String getIdioma() {
        return idioma;
    }

    public static void setIdioma(String idiomas) {
        if(idiomas.equals("español")){
            idiomas="es";
        }
        else{
            idiomas="en";
        }
        idioma = idiomas;
    }
}
