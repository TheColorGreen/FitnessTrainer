package fitness.sportgenertaion.fitnesstrainer.Classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;

import java.util.List;

import fitness.sportgenertaion.fitnesstrainer.R;
import fitness.sportgenertaion.fitnesstrainer.VerEjercicio;
import fitness.sportgenertaion.fitnesstrainer.VerRutinasPredeterminada;

//Adapter de Rutina predetefinida
public class RutinaOpcionalAdapter extends RecyclerView.Adapter<fitness.sportgenertaion.fitnesstrainer.Classes.RutinaOpcionalAdapter.ViewHolder>
            implements CompoundButton.OnCheckedChangeListener {
        public List<RutinaPredefinida> llistaRutina;
        Context context;



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
        public void onBindViewHolder(fitness.sportgenertaion.fitnesstrainer.Classes.RutinaOpcionalAdapter.ViewHolder holder, int position) {
            final RutinaPredefinida rutina = llistaRutina.get(position);
            RutinaPredefinida item = llistaRutina.get(position);

            //Fica el nom de la rutina a el Boto
            holder.bMostraLesRutinesOpcionals.setText(item.getNombre());

        }

        // Mètode de la classe RecyclerView (que és abstracta)


        @Override
        public int getItemCount () {
            return llistaRutina.size();
        }

    }
