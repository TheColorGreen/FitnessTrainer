package fitness.sportgenertaion.fitnesstrainer;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

import fitness.sportgenertaion.fitnesstrainer.Classes.Actualizar;
import fitness.sportgenertaion.fitnesstrainer.Classes.IdUsuario;
import fitness.sportgenertaion.fitnesstrainer.Classes.Idioma;
import fitness.sportgenertaion.fitnesstrainer.Fragments.ComprobarRutinaHistorial;
import fitness.sportgenertaion.fitnesstrainer.Fragments.HistorialRutina;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authListener;

    Button btCrearRutina;
    Button btRutinaAleatoria;
    Button btMiRutina;
    Button btHistorial;
    Dias dias;
    Typeface typeface;
    int presiones = 0;
    HistorialRutina historialRutina;
    ComprobarRutinaHistorial fragment;


    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(authListener);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//Define el idioma
        Idioma.setIdioma(Locale.getDefault().getDisplayLanguage());

        mAuth = FirebaseAuth.getInstance();
        authListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null) {
                    Task<AuthResult> resultTask = mAuth.signInAnonymously();
                    resultTask.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            IdUsuario.setIdUsuario(mAuth.getCurrentUser().getUid());
                            Actualizar.setActualizado(true);
                            fragment = new ComprobarRutinaHistorial();
                            getSupportFragmentManager().beginTransaction().replace(R.id.flFrame, fragment).commit();
                        }
                    });

                }

            }
        };
//        Toast.makeText(getApplicationContext(),FirebaseAuth.getInstance().getCurrentUser().toString(),Toast.LENGTH_LONG).show();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//Si user no es null guardo l'id de l'usuari i llanço faic el fragment de comprobar rutinahistorial
        if (user != null) {
            IdUsuario.setIdUsuario(mAuth.getCurrentUser().getUid());
            if (Actualizar.getActualizado() == false) {
                Actualizar.setActualizado(true);
                fragment = new ComprobarRutinaHistorial();
                getSupportFragmentManager().beginTransaction().replace(R.id.flFrame, fragment).commit();
            }
        }


        btCrearRutina = findViewById(R.id.bCreaRutina);
        btHistorial = findViewById(R.id.bHistorialRutina);
        btMiRutina = findViewById(R.id.bRutina);
        btRutinaAleatoria = findViewById(R.id.bCrearRutinaAleatoria);

//Faic que quan clickis a un botto vaigi a l'activity/classe
        btCrearRutina.setOnClickListener(this);
        btHistorial.setOnClickListener(this);
        btMiRutina.setOnClickListener(this);
        btRutinaAleatoria.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        Intent intent;
        //A quina activity o fragment anira
        if (v == btHistorial) {
            historialRutina = new HistorialRutina();
            getSupportFragmentManager().beginTransaction().replace(R.id.flFrame, historialRutina).commit();


        } else if (v == btCrearRutina) {
            intent = new Intent(this, CrearRutinas.class);
            startActivity(intent);
        } else if (v == btRutinaAleatoria) {
            intent = new Intent(this, RutinasPredefinidas.class);
            startActivity(intent);
            //rutinaAleatoria = new RutinaAleatoria();

            //     getSupportFragmentManager().beginTransaction().replace(R.id.flFrame, rutinaAleatoria).commit();

        } else {
            intent = new Intent(this, Dias.class);
            startActivity(intent);

        }
    }
//Al donar-li enrere tanca els fragments o la aplicació
    @Override
    public void onBackPressed() {
        if ( historialRutina != null) {
            getSupportFragmentManager().beginTransaction().
                    remove(getSupportFragmentManager().findFragmentById(R.id.flFrame)).commit();

            historialRutina = null;


        } else {
            System.exit(0);


        }
    }
}