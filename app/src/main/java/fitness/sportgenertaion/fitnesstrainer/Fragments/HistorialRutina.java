package fitness.sportgenertaion.fitnesstrainer.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import fitness.sportgenertaion.fitnesstrainer.MainActivity;
import fitness.sportgenertaion.fitnesstrainer.MostrarDia;
import fitness.sportgenertaion.fitnesstrainer.R;


public class HistorialRutina extends Fragment {
    String fecha;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_historial_rutina, container, false);
        CalendarView calendarView = rootView.findViewById(R.id.calendarView);
        //calendarView.getDate();
        //calendarView.setMinDate(calendarView.getDate());



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override public void onSelectedDayChange(
                    CalendarView view,
                    int          year,
                    int          month,
                    int          dayOfMonth ) {
                Toast.makeText(getContext(),
                        ""+dayOfMonth+ " / " + month + " / " + year, Toast.LENGTH_SHORT).show();
                // Guarda la fecha seleccionada en un String
                fecha = dayOfMonth+ "/" + month + "/" + year;

                Intent intent = new Intent(getActivity(), MostrarDia.class);
                // Envia la fecha
                intent.putExtra("fecha", fecha);
                startActivity(intent);

            }
        });

        // Boton para tirar para atras al menu principal
        FloatingActionButton fab =  rootView.findViewById(R.id.fbExit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // LLama a otra activity
                Intent in = new Intent(getActivity(), MainActivity.class);
                startActivity(in);
            }
        });






        return rootView;


    }
}
