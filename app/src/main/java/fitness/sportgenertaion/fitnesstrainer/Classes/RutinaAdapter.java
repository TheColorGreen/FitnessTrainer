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

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import fitness.sportgenertaion.fitnesstrainer.R;
import fitness.sportgenertaion.fitnesstrainer.VerEjercicio;

/**
 * Created by Carlos on 11/05/2018.
 */
//Adapter de Rutian
public class RutinaAdapter extends RecyclerView.Adapter<RutinaAdapter.ViewHolder> implements CompoundButton.OnCheckedChangeListener {
    public List<Rutina> llistaRutina;
    Context context;
    String dia;
    String idUsuario;
    String[] diasSemanas = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};


    public RutinaAdapter(Context context, List<Rutina> llistaRutina, String dia, String idUsuario) {

        this.llistaRutina = llistaRutina;
        this.context = context;
        this.dia = dia;
        this.idUsuario = idUsuario;
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

            intent.putExtra("ejercicio", llistaRutina.get(posicio).getEjercicio());
            intent.putExtra("dia", dia);
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
        final Rutina rutina = llistaRutina.get(position);
        Rutina item = llistaRutina.get(position);

        //Fica el nom de l'exercici al textview
        holder.tvEjercicio.setText(item.getEjercicio());


       //Lisener al checkbox
        holder.cAnyadir.setOnCheckedChangeListener(null);


//Mira el dia d'abui
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DateAcciones fecha = new DateAcciones(day, month, year);
        String diaSemana = "Monday";
        try {
            diaSemana = fecha.diaSemana();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int diaDeLaSemana = 0;

        for (int a = 0; a < 7; a++) {
            if (diasSemanas[a].equals(dia)) {
                diaDeLaSemana = a;
            }
        }

        try {
//Si el dia d'abui es mes gran que el de la rutina no et deix modificar els checkbox
            if (fecha.diasHastaLunes() > diaDeLaSemana) {
                holder.cAnyadir.setEnabled(false);


            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Si aquell ejercici esta marcat com a fet el reflexa al checkbox
        if (rutina.getEcho() == true) {
            holder.cAnyadir.setChecked(true);
        }


        holder.cAnyadir.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set your object's last status
                try {
                    //Quan cambia el checbox actualitza el historial
                    ActualizarHistorial.anyadirHistorial();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                rutina.setSelected(isChecked);
//Si esta seleccionat fica true a el ejercici al firebase sino fica false
                if (rutina.isSelected()) {

                    RutinaAcciones.PonerCheck(dia, rutina.getEjercicio());

                } else {
                    RutinaAcciones.anyadir(dia, rutina.getEjercicio());

                }
            }
        });


    }

    // Mètode de la classe RecyclerView (que és abstracta)


    @Override
    public int getItemCount() {
        return llistaRutina.size();
    }
}