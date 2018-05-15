package fitness.sportgenertaion.fitnesstrainer.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import fitness.sportgenertaion.fitnesstrainer.R;


public class HistorialRutina extends Fragment {

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
            }
        });

        ///calendarView.getDate();
        ///



        return rootView;


    }
}
