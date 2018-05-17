package fitness.sportgenertaion.fitnesstrainer.Classes;

import android.annotation.SuppressLint;
import android.content.Context;
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


public class MostrarDiaAdapter extends RecyclerView.Adapter<MostrarDiaAdapter.ViewHolder> implements CompoundButton.OnCheckedChangeListener {
    public List<Rutina> llistaRutina;
    Context context;
        Boolean marcado;
    String idUsuario;


    public MostrarDiaAdapter(Context context, List<Rutina> llistaRutina, Boolean marcado, String idUsuario) {

        this.llistaRutina = llistaRutina;
        this.context = context;
        this.marcado = marcado;
        this.idUsuario = idUsuario;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {
        public TextView tvEjercicio;
        public ImageView ivMarcador;


        @SuppressLint("WrongViewCast")
        public ViewHolder(View itemView) {
            super(itemView);
            tvEjercicio = itemView.findViewById(R.id.tv_nom);
            ivMarcador = itemView.findViewById(R.id.ivMarcador);
        }


        @Override
        public void onClick(View view) {


        }
    }

    // Mètode de la classe RecyclerView (que és abstracta)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ejercicios_marcados, parent, false);

        return new ViewHolder(view);
    }

    // Mètode de la classe RecyclerView (que és abstracta)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Rutina rutina = llistaRutina.get(position);
        Rutina item = llistaRutina.get(position);
        holder.tvEjercicio.setText(item.getEjercicio());

        // Elejir un icono o otro segun la variable
        holder.ivMarcador.setImageResource(R.drawable.pesas);// Este va por defecto
        holder.ivMarcador.setImageResource(R.drawable.noRealizado); //Este es el que cambia



    }




    @Override
    public int getItemCount() {
        return llistaRutina.size();
    }
}