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
import android.widget.TextView;

import java.util.List;

import fitness.sportgenertaion.fitnesstrainer.R;
import fitness.sportgenertaion.fitnesstrainer.VerEjercicio;

/**
 * Created by Carlos on 03/05/2018.
 */

public class EjercicioAdapter extends RecyclerView.Adapter<EjercicioAdapter.ViewHolder> {
    public List<Ejercicio> llistaEjercicios;
    Context context;

    public EjercicioAdapter(Context context, List<Ejercicio> llistaEjercicios) {

        this.llistaEjercicios = llistaEjercicios;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {
        public TextView tvEjercicio;
        public Button btAnyadir;

        @SuppressLint("WrongViewCast")
        public ViewHolder(View itemView) {
            super(itemView);
            tvEjercicio = itemView.findViewById(R.id.tv_nom);


            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int posicio = getAdapterPosition();
            Intent intent = new Intent(context, VerEjercicio.class);

            intent.putExtra("ejercicio",llistaEjercicios.get(posicio).getNombre());
            context.startActivity(intent);

        }
    }

    // Mètode de la classe RecyclerView (que és abstracta)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ejercicio, parent, false);

        return new ViewHolder(view);
    }

    // Mètode de la classe RecyclerView (que és abstracta)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ejercicio item = llistaEjercicios.get(position);
        holder.tvEjercicio.setText(item.getNombre());

    }

    // Mètode de la classe RecyclerView (que és abstracta)
    @Override
    public int getItemCount() {
        return llistaEjercicios.size();
    }





}