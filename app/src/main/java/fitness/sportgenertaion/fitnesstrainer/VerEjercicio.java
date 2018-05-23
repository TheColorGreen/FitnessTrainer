package fitness.sportgenertaion.fitnesstrainer;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.net.URL;

import fitness.sportgenertaion.fitnesstrainer.Classes.RutinaAcciones;

public class VerEjercicio extends AppCompatActivity
        implements ValueEventListener, ChildEventListener,View.OnClickListener {

    String ejercicio = "Abdominales";
    private Bitmap loadedImage;
    TextView tvTitulo;
    TextView tvDescripcion;
    ImageView ivImagen;
    Button bCronometro;
    String dia;
    int repeticiones=0;
    int series=0;
    int tiempo=0;
    int ronda=1;
    Boolean cronometroIniciado=true;
    FloatingActionButton fabEliminar;

    TextView tvRepeticiones;
    TextView tvSeries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_ejercicio);

        fabEliminar = findViewById(R.id.fabEliminar);

        //Recogemos el ejercico que nos pasan por el intent
        Bundle parametros = this.getIntent().getExtras();
        if (parametros != null) {
            ejercicio = parametros.getString("ejercicio");
            dia = parametros.getString("dia");
        }
        // Si el parametro es null,
        if (dia == null) {
            // S'ha magara el boton fabEliminar
            fabEliminar.setVisibility(View.INVISIBLE);
        }




        // Eliminar el exercici secceionat
        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dia != null && !dia.equals("")) {
                    RutinaAcciones.BorrarEjercicios(dia, ejercicio);
                    Intent intent = new Intent(VerEjercicio.this, Dias.class);
                    startActivity(intent);

                }

            }
        });

        //Inicializamos variables
        tvSeries=findViewById(R.id.tvSeries);
        bCronometro=findViewById(R.id.bCronometro);
        tvTitulo = findViewById(R.id.tvTitulo);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        ivImagen = (ImageView) findViewById(R.id.ivImagen);
        tvRepeticiones=findViewById(R.id.tvRepeticiones);
bCronometro.setOnClickListener(this);
        //Firebase

        DatabaseReference dbPrediccio = FirebaseDatabase.getInstance()
                .getReference()
                .child("Ejercicios/" + ejercicio);
        dbPrediccio.addValueEventListener(this);
        dbPrediccio.addChildEventListener(this);


    }


    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        // Toast.makeText(VerEjercicio.this, "entra aqui.",
        // Toast.LENGTH_SHORT).show();

        repeticiones= Integer.valueOf(dataSnapshot.child("/repeticiones").getValue().toString());
        series= Integer.valueOf(dataSnapshot.child("/series").getValue().toString());
        tiempo= Integer.valueOf(dataSnapshot.child("/tiempo").getValue().toString());

        tvTitulo.setText(dataSnapshot.getKey());
        tvDescripcion.setText(dataSnapshot.child("/descripcion").getValue().toString());
        tvRepeticiones.setText(String.valueOf(repeticiones));
        tvSeries.setText(String.valueOf(series));


        //Carrear la imatge
         new DownLoadImageTask(ivImagen).execute(dataSnapshot.child("/foto").getValue().toString());


    }

    @Override
    public void onClick(View v) {
        int segundos=tiempo;
        if(cronometroIniciado==true && series>0) {
            new CountDownTimer(tiempo * 1000, 1000) {

                public void onTick(long millisUntilFinished) {
                    cronometroIniciado = false;
                    bCronometro.setText("00:" + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    cronometroIniciado = true;
                    ronda++;


                    series--;
                    if(series>0){

                        bCronometro.setText(getResources().getString(R.string.ronda) + ronda);
                    }
                    else{
                        bCronometro.setText(getResources().getString(R.string.ronda_acabada));
                    }
                    tvSeries.setText(String.valueOf(series));
                }
            }.start();
        }
    }

    //Aquest metode es per carregaar una imatge desde url
    private class DownLoadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */

        //Aquesta classe es
        protected Bitmap doInBackground(String... urls) {
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try {
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            } catch (Exception e) { // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }


    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }


    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}