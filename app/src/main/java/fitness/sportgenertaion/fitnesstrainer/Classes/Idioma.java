package fitness.sportgenertaion.fitnesstrainer.Classes;

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
