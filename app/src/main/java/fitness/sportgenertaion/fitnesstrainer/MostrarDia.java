package fitness.sportgenertaion.fitnesstrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MostrarDia extends AppCompatActivity {
    String fechaRecuperada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_dia);

        fechaRecuperada = getIntent().getExtras().getString("fecha");

        Toast.makeText(getApplicationContext(), fechaRecuperada, Toast.LENGTH_LONG).show();


        FloatingActionButton fbExit = findViewById(R.id.fbExit);
        fbExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MostrarDia.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }


}
