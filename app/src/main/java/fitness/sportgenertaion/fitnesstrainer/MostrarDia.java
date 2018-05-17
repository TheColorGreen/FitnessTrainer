package fitness.sportgenertaion.fitnesstrainer;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MostrarDia extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_dia);


        FloatingActionButton fbExit= findViewById(R.id.fbExit);
        fbExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new  Intent(MostrarDia.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }
}
