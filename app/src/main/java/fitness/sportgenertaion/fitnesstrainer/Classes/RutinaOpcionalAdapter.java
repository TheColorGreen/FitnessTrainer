package fitness.sportgenertaion.fitnesstrainer.Classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.net.URL;
import java.security.Key;
import java.util.List;

import fitness.sportgenertaion.fitnesstrainer.MainActivity;
import fitness.sportgenertaion.fitnesstrainer.R;
import fitness.sportgenertaion.fitnesstrainer.VerEjercicio;
import fitness.sportgenertaion.fitnesstrainer.VerRutinasPredeterminada;

//Adapter de Rutina predetefinida
public class RutinaOpcionalAdapter extends RecyclerView.Adapter<fitness.sportgenertaion.fitnesstrainer.Classes.RutinaOpcionalAdapter.ViewHolder>
            implements CompoundButton.OnCheckedChangeListener {
        public List<RutinaPredefinida> llistaRutina;
        Context context;
static String url=" ";


        public RutinaOpcionalAdapter(Context context, List<RutinaPredefinida> llistaRutina) {

            this.llistaRutina = llistaRutina;
            this.context = context;

        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        }

        public class ViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {
            public Button bMostraLesRutinesOpcionals;


            @SuppressLint("WrongViewCast")
            public ViewHolder(View itemView) {
                super(itemView);
                bMostraLesRutinesOpcionals = itemView.findViewById(R.id.bMostraLesRutinesOpcionals);

                bMostraLesRutinesOpcionals.setOnClickListener(this);
            }

//Truca a la rutina per tal de veure com es
            @Override
            public void onClick(View view) {
                int posicio = getAdapterPosition();
                Intent intent = new Intent(context, VerRutinasPredeterminada.class);

                intent.putExtra("rutina", llistaRutina.get(posicio).getNombre());
                context.startActivity(intent);

            }
        }

        // Mètode de la classe RecyclerView (que és abstracta)
        @Override
        public fitness.sportgenertaion.fitnesstrainer.Classes.RutinaOpcionalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_rutinas_opcionales, parent, false);

            return new fitness.sportgenertaion.fitnesstrainer.Classes.RutinaOpcionalAdapter.ViewHolder(view);
        }



        // Mètode de la classe RecyclerView (que és abstracta)
        @Override
        public void onBindViewHolder(final fitness.sportgenertaion.fitnesstrainer.Classes.RutinaOpcionalAdapter.ViewHolder holder, int position) {
            final RutinaPredefinida rutina = llistaRutina.get(position);
            RutinaPredefinida item = llistaRutina.get(position);

            //Fica el nom de la rutina a el Boto
            holder.bMostraLesRutinesOpcionals.setText(item.getNombre());

            DatabaseReference dbFoto= FirebaseDatabase.getInstance()
                    .getReference()
                    .child("Ejercicios Predeterminados-"+ Idioma.getIdioma()+"/"+rutina.getNombre());
            dbFoto.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot element : snapshot.getChildren()) {

                        url= snapshot.child("/foto").getValue().toString();

                    }

                    new DownLoadImageTask(holder.bMostraLesRutinesOpcionals).execute(url);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });


        }

        // Mètode de la classe RecyclerView (que és abstracta)


        @Override
        public int getItemCount () {
            return llistaRutina.size();
        }

    private class DownLoadImageTask extends AsyncTask<String, Void, Bitmap> {
        Button button;

        public DownLoadImageTask(Button button) {
            this.button = button;
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
            BitmapDrawable ob = new BitmapDrawable(Resources.getSystem(), result);
            button.setBackground(ob);
        }
    }

    }
