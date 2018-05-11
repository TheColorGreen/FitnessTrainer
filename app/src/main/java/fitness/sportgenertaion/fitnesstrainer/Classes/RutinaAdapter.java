package fitness.sportgenertaion.fitnesstrainer.Classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import fitness.sportgenertaion.fitnesstrainer.CrearRutina;
import fitness.sportgenertaion.fitnesstrainer.R;
import fitness.sportgenertaion.fitnesstrainer.VerEjercicio;

/**
 * Created by Carlos on 11/05/2018.
 */

public class RutinaAdapter extends RecyclerView.Adapter<RutinaAdapter.ViewHolder> implements CompoundButton.OnCheckedChangeListener {
    public List<Rutina> llistaRutina;
    Context context;
    String dia;
    String idUsuario;


    public RutinaAdapter(Context context, List<Rutina> llistaRutina, String dia,String idUsuario) {

        this.llistaRutina = llistaRutina;
        this.context = context;
        this.dia = dia;
        this.idUsuario=idUsuario;
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
        holder.tvEjercicio.setText(item.getEjercicio());



        //in some cases, it will prevent unwanted situations
         holder.cAnyadir.setOnCheckedChangeListener(null);

        //if true, your checkbox will be selected, else unselected
       if(rutina.getEcho()==true) {
           holder.cAnyadir.setChecked(true);
       }



        holder.cAnyadir.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set your object's last status

                rutina.setSelected(isChecked);

                if (rutina.isSelected()) {

                    RutinaAcciones.PonerCheck(dia,rutina.getEjercicio(),idUsuario);
                } else {

                   RutinaAcciones.anyadir(dia,rutina.getEjercicio(),idUsuario);
                }
            }
        });


    }

    // Mètode de la classe RecyclerView (que és abstracta)


    @Override
    public int getItemCount () {
        return llistaRutina.size();
    }
}