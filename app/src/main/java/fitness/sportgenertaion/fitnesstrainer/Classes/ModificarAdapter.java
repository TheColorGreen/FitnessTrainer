package fitness.sportgenertaion.fitnesstrainer.Classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import fitness.sportgenertaion.fitnesstrainer.ModificarRutina;
import fitness.sportgenertaion.fitnesstrainer.R;
import fitness.sportgenertaion.fitnesstrainer.VerEjercicio;

/**
 * Created by Carlos on 15/05/2018.
 */
//Adapter de modificar Rutina
public class ModificarAdapter extends RecyclerView.Adapter<ModificarAdapter.ViewHolder> implements CompoundButton.OnCheckedChangeListener {
    public List<Rutina> llistaEjercicios;
    Context context;


    public ModificarAdapter(Context context, List<Rutina> llistaEjercicios) {

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
            cAnyadir = itemView.findViewById(R.id.cPoner);

            itemView.setOnClickListener(this);
        }

//Truca a ver ejercicio per tal de que l'usuari sapigui quin exercici esta mirant
        @Override
        public void onClick(View view) {
            int posicio = getAdapterPosition();
            Intent intent = new Intent(context, VerEjercicio.class);

            intent.putExtra("ejercicio", llistaEjercicios.get(posicio).getEjercicio());
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
        final Rutina ejercicio = llistaEjercicios.get(position);
        Rutina item = llistaEjercicios.get(position);
        holder.tvEjercicio.setText(item.getEjercicio());



        holder.cAnyadir.setChecked(ejercicio.isSelected());


        holder.cAnyadir.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set your object's last status

                ejercicio.setSelected(isChecked);

                if (ejercicio.isSelected()) {

                    ModificarRutina.RutinaTemporal(ejercicio.getEjercicio());
                } else {

                    ModificarRutina.BorrarRutinaTemporal(ejercicio.getEjercicio());
                }
            }
        });


    }

    // Mètode de la classe RecyclerView (que és abstracta)


    @Override
    public int getItemCount() {
        return llistaEjercicios.size();
    }
}

