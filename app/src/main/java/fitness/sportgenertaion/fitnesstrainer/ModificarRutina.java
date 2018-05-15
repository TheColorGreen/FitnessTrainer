package fitness.sportgenertaion.fitnesstrainer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ModificarRutina extends AppCompatActivity {
String idUsuario;
String dia="Lunes";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_rutina);

        Bundle parametros = this.getIntent().getExtras();
        if (parametros != null) {
            idUsuario = parametros.getString("idUsuario");
            dia = parametros.getString("dia");
        }
        
    }
}
