package fitness.sportgenertaion.fitnesstrainer;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import fitness.sportgenertaion.fitnesstrainer.Classes.IdUsuario;
import fitness.sportgenertaion.fitnesstrainer.Fragments.HistorialRutina;
import fitness.sportgenertaion.fitnesstrainer.Fragments.RutinaAleatoria;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authListener;

    Button btCrearRutina;
    Button btRutinaAleatoria;
    Button btMiRutina;
    Button btHistorial;
    Dias dias;
    Typeface typeface;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        // typeface = Typeface.createFromAsset(getAssets(),"fonts/Comfortaa-Regular.ttf");
        

        mAuth = FirebaseAuth.getInstance();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Task<AuthResult> resultTask = mAuth.signInAnonymously();
                    resultTask.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                        }
                    });
                }
            }
        };
IdUsuario.setIdUsuario(mAuth.getCurrentUser().getUid());
        btCrearRutina = findViewById(R.id.bCreaRutina);
        btHistorial = findViewById(R.id.bHistorialRutina);
        btMiRutina = findViewById(R.id.bRutina);
        btRutinaAleatoria = findViewById(R.id.bCrearRutinaAleatoria);

//Faic que cuan clickis a un botto vaigi a l'activity/classe
        btCrearRutina.setOnClickListener(this);
        btHistorial.setOnClickListener(this);
        btMiRutina.setOnClickListener(this);
        btRutinaAleatoria.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v == btHistorial) {
            HistorialRutina historialRutina = new HistorialRutina();

            getSupportFragmentManager().beginTransaction().replace(R.id.flFrame, historialRutina).commit();
        }
        else if (v == btCrearRutina) {
            intent = new Intent(this, CrearRutinas.class);
            startActivity(intent);
        }
        else if (v == btRutinaAleatoria) {

            RutinaAleatoria fragment = new RutinaAleatoria();

            getSupportFragmentManager().beginTransaction().replace(R.id.flFrame, fragment).commit();

        }
       else {
            intent = new Intent(this, Dias.class);
            startActivity(intent);

        }
    }

}