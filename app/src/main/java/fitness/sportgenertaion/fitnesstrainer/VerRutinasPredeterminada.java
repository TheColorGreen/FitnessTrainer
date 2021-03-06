package fitness.sportgenertaion.fitnesstrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fitness.sportgenertaion.fitnesstrainer.Classes.ActualizarHistorial;
import fitness.sportgenertaion.fitnesstrainer.Classes.DateAcciones;
import fitness.sportgenertaion.fitnesstrainer.Classes.Ejercicio;
import fitness.sportgenertaion.fitnesstrainer.Classes.EjercicioAdapter;
import fitness.sportgenertaion.fitnesstrainer.Classes.IdUsuario;
import fitness.sportgenertaion.fitnesstrainer.Classes.Idioma;
import fitness.sportgenertaion.fitnesstrainer.Classes.Rutina;
import fitness.sportgenertaion.fitnesstrainer.Classes.RutinaAcciones;
import fitness.sportgenertaion.fitnesstrainer.Classes.RutinaAdapter;
import fitness.sportgenertaion.fitnesstrainer.Classes.RutinaPredefinida;
import fitness.sportgenertaion.fitnesstrainer.Classes.VerRutinasPredeterminadasAdapter;

public class VerRutinasPredeterminada extends AppCompatActivity implements View.OnClickListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    static String idUsuario;
    public static String dia;
    static String rutinas = " ";
    static ArrayList<String> lunes = new ArrayList<String>();
    static ArrayList<String> martes = new ArrayList<String>();
    static ArrayList<String> miercoles = new ArrayList<String>();
    static ArrayList<String> jueves = new ArrayList<String>();
    static ArrayList<String> viernes = new ArrayList<String>();
    static ArrayList<String> sabado = new ArrayList<String>();
    static ArrayList<String> domingo = new ArrayList<String>();
    String[] diasDeLaSemana = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};
    Button bGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_rutinas_predeterminada);


        Bundle parametros = this.getIntent().getExtras();
        if (parametros != null) {
            rutinas = parametros.getString("rutina");
        }
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = findViewById(R.id.tabs);

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(mViewPager);
            }
        });

        idUsuario = IdUsuario.getIdUsuario();

        bGuardar = findViewById(R.id.bGuargarRutina);
        bGuardar.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dias, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @Override
    //Guardo la rutina predefinida com a rutina personal
    public void onClick(View v) {
        RutinaAcciones.eliminarRutina();
        for (int dia = 0; dia < 7; dia++) {
            DatabaseReference dbEjerciciosPredeterminados = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("Ejercicios Predeterminados-" + Idioma.getIdioma() + "/" + rutinas + "/" + diasDeLaSemana[dia]);
            final int finalDia = dia;
            dbEjerciciosPredeterminados.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot element : snapshot.getChildren()) {
                        RutinaAcciones.anyadir(diasDeLaSemana[finalDia], element.getKey().toString());

                    }
                  DateAcciones.ActualizarFechaRutina();
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements ValueEventListener, ChildEventListener {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        RecyclerView rvEjercicios;
        List<RutinaPredefinida> llistaRutina = new ArrayList<RutinaPredefinida>();
        VerRutinasPredeterminadasAdapter rutinaAdapter;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, String idUsuario) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);


            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_ver_rutina_predeteminada, container, false);


            rvEjercicios = rootView.findViewById(R.id.rvRutina);
            //


            String dia2 = "Lunes";
            ///Depenent el fragment en el que estugis dira el dia

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                dia = "Lunes";


            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                dia = "Martes";

            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
                dia = "Miercoles";


            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 4) {
                dia = "Jueves";


            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 5) {
                dia = "Viernes";


            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 6) {
                dia = "Sabado";

            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 7) {
                dia = "Domingo";


            }

            dia2 = dia;

            final String finalDia = dia2;


            rvEjercicios.setLayoutManager(new LinearLayoutManager(getContext()));
            rvEjercicios.addItemDecoration(new DividerItemDecoration(getContext(),
                    LinearLayoutManager.VERTICAL));
            rutinaAdapter = new VerRutinasPredeterminadasAdapter(getContext(), llistaRutina);
            DatabaseReference dbEjerciciosPredeterminados = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("Ejercicios Predeterminados-" + Idioma.getIdioma() + "/" + rutinas + "/" + dia);
            dbEjerciciosPredeterminados.addValueEventListener(this);
            dbEjerciciosPredeterminados.addChildEventListener(this);
            return rootView;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            llistaRutina.removeAll(llistaRutina);
            for (DataSnapshot element : dataSnapshot.getChildren()) {

                RutinaPredefinida rutina = new RutinaPredefinida(element.getKey().toString());

                llistaRutina.add(rutina);

            }
            rvEjercicios.setAdapter(rutinaAdapter);
            rvEjercicios.scrollToPosition(llistaRutina.size() - 1);
        }

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }


        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            return PlaceholderFragment.newInstance(position + 1, idUsuario);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 7;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return getString(R.string.lunes);
                case 1:
                    return getString(R.string.martes);
                case 2:
                    return getString(R.string.miercoles);
                case 3:
                    return getString(R.string.jueves);
                case 4:
                    return getString(R.string.viernes);
                case 5:
                    return getString(R.string.sabado);
                case 6:
                    return getString(R.string.domingo);
            }
            return null;
        }


    }

}
