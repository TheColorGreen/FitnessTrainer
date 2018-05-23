package fitness.sportgenertaion.fitnesstrainer.Classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fitness.sportgenertaion.fitnesstrainer.R;
import fitness.sportgenertaion.fitnesstrainer.VerEjercicio;

//Adapter de mostrar dia
public class MostrarDiaAdapter extends RecyclerView.Adapter<MostrarDiaAdapter.ViewHolder> implements CompoundButton.OnCheckedChangeListener {
    public List<Rutina> llistaRutina;
    Context context;


    public MostrarDiaAdapter(Context context, List<Rutina> llistaRutina) {

        this.llistaRutina = llistaRutina;
        this.context = context;


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {
        public TextView tvEjercicio;
        public ImageView ivMarcado;


        @SuppressLint("WrongViewCast")
        public ViewHolder(View itemView) {
            super(itemView);
            tvEjercicio = itemView.findViewById(R.id.tv_nom);
            ivMarcado = itemView.findViewById(R.id.ivMarcador);
//Fica el listener al checkbox
            itemView.setOnClickListener(this);
        }

        //Truca a ver ejercicio per tal de que l'usuari sapigui quin exercici esta mirant
        @Override
        public void onClick(View view) {
            int posicio = getAdapterPosition();
            Intent intent = new Intent(context, VerEjercicio.class);

            intent.putExtra("ejercicio", llistaRutina.get(posicio).getEjercicio());

            context.startActivity(intent);

        }
    }

    // Mètode de la classe RecyclerView (que és abstracta)
    @Override
    public MostrarDiaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ejercicios_marcados, parent, false);

        return new MostrarDiaAdapter.ViewHolder(view);
    }

    // Mètode de la classe RecyclerView (que és abstracta)
    @Override
    public void onBindViewHolder(MostrarDiaAdapter.ViewHolder holder, int position) {
        final Rutina rutina = llistaRutina.get(position);
        Rutina item = llistaRutina.get(position);
        holder.tvEjercicio.setText(item.getEjercicio());
//Si ha fet l'exercici se li fica una pesa y sino una x
        if (rutina.getEcho() == true) {
            holder.ivMarcado.setImageResource(R.drawable.pesas);
        } else {
            holder.ivMarcado.setImageResource(R.drawable.norealizado);

        }

    }

    // Mètode de la classe RecyclerView (que és abstracta)


    @Override
    public int getItemCount() {
        return llistaRutina.size();
    }
}

