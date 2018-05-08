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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fitness.sportgenertaion.fitnesstrainer.R;
import fitness.sportgenertaion.fitnesstrainer.VerEjercicio;

/**
 * Created by Carlos on 03/05/2018.
 */

public class EjercicioAdapter extends RecyclerView.Adapter<EjercicioAdapter.ViewHolder> implements CompoundButton.OnCheckedChangeListener {
    public List<Ejercicio> llistaEjercicios;
    Context context;

    public EjercicioAdapter(Context context, List<Ejercicio> llistaEjercicios) {

        this.llistaEjercicios = llistaEjercicios;
        this.context = context;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {
        public TextView tvEjercicio;
        public CheckBox cAnyadir;


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

        //in some cases, it will prevent unwanted situations
        holder.cAnyadir.setOnCheckedChangeListener(null);

        //if true, your checkbox will be selected, else unselected
        holder.cAnyadir.setChecked(objIncome.isSelected());

        holder.cAnyadir.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set your object's last status
                objIncome.setSelected(isChecked);
            }
        });
    }

    // Mètode de la classe RecyclerView (que és abstracta)
    @Override
    public int getItemCount() {
        return llistaEjercicios.size();
    }





}