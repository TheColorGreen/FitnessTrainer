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
import android.widget.TextView;

import java.util.List;

import fitness.sportgenertaion.fitnesstrainer.R;
import fitness.sportgenertaion.fitnesstrainer.VerEjercicio;
import fitness.sportgenertaion.fitnesstrainer.VerRutinasPredeterminada;

public class VerRutinasPredeterminadasAdapter extends RecyclerView.Adapter<fitness.sportgenertaion.fitnesstrainer.Classes.VerRutinasPredeterminadasAdapter.ViewHolder>
        {
    public List<RutinaPredefinida> llistaRutina;
    Context context;


    public VerRutinasPredeterminadasAdapter(Context context, List<RutinaPredefinida> llistaRutina) {

        this.llistaRutina = llistaRutina;
        this.context = context;

    }



    public class ViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {
        public TextView tvNomEjercici;


        @SuppressLint("WrongViewCast")
        public ViewHolder(View itemView) {
            super(itemView);
            tvNomEjercici = itemView.findViewById(R.id.tv_nomEjercici);

            tvNomEjercici.setOnClickListener(this);
        }

//Truca a ver ejercicio per tal de que l'usuari sapigui quin exercici esta mirant
        @Override
        public void onClick(View view) {
            int posicio = getAdapterPosition();

            Intent intent = new Intent(context, VerEjercicio.class);
            intent.putExtra("ejercicio", llistaRutina.get(posicio).getNombre());
            context.startActivity(intent);


        }
    }

    // Mètode de la classe RecyclerView (que és abstracta)
    @Override
    public fitness.sportgenertaion.fitnesstrainer.Classes.VerRutinasPredeterminadasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rutinas_predeterminadas, parent, false);

        return new fitness.sportgenertaion.fitnesstrainer.Classes.VerRutinasPredeterminadasAdapter.ViewHolder(view);
    }


    // Mètode de la classe RecyclerView (que és abstracta)
    @Override
    public void onBindViewHolder(fitness.sportgenertaion.fitnesstrainer.Classes.VerRutinasPredeterminadasAdapter.ViewHolder holder, int position) {
        final RutinaPredefinida rutina = llistaRutina.get(position);
        RutinaPredefinida item = llistaRutina.get(position);

        //Fica el nom del exercici al textview
        holder.tvNomEjercici.setText(item.getNombre());


    }


    @Override
    public int getItemCount() {
        return llistaRutina.size();
    }

}
