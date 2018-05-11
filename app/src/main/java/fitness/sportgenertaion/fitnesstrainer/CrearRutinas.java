package fitness.sportgenertaion.fitnesstrainer;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fitness.sportgenertaion.fitnesstrainer.Classes.Ejercicio;
import fitness.sportgenertaion.fitnesstrainer.Classes.EjercicioAdapter;

public class CrearRutinas extends AppCompatActivity {
    static String idUsuario;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_rutinas);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            idUsuario=parametros.getString("idUsuario");

        }



    }




    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements ValueEventListener, ChildEventListener,AdapterView.OnItemSelectedListener {

        static ArrayList<String> lunes = new ArrayList<String>();
        static ArrayList<String> martes = new ArrayList<String>();
        static ArrayList<String> miercoles = new ArrayList<String>();
        static ArrayList<String> jueves = new ArrayList<String>();
        static ArrayList<String> viernes = new ArrayList<String>();
        static ArrayList<String> sabado = new ArrayList<String>();
        static ArrayList<String> domingo = new ArrayList<String>();
        int numeroDia = 0;
        String[] dia;
        DatabaseReference dbPrediccio;
        String nivel = "5";
        String grupoMuscular = "5";
        Spinner spGrupoMuscular;
        Spinner spNivel;
        Button bguardar;
        private RecyclerView rvEjercicios;
        private List<Ejercicio> llistaEjercicios = new ArrayList<Ejercicio>();
        private EjercicioAdapter ejercicioAdapter;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_crear_rutinas, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            dia = getResources().getStringArray(R.array.dia);
            bguardar = rootView.findViewById(R.id.bGuargar);
            spNivel = rootView.findViewById(R.id.spNivel);
            spGrupoMuscular = rootView.findViewById(R.id.spMusculo);
            bguardar = rootView.findViewById(R.id.bGuargar);
            rvEjercicios = rootView.findViewById(R.id.rvEjercicios);



            //Hago que en los spinners salgan los arrays
            ArrayAdapter<CharSequence> adapterMusculos = ArrayAdapter.createFromResource(getContext(), R.array.musculos, android.R.layout.simple_spinner_item);
            adapterMusculos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spGrupoMuscular.setAdapter(adapterMusculos);


            ArrayAdapter<CharSequence> adapterNivel = ArrayAdapter.createFromResource(getContext(), R.array.dificultad, android.R.layout.simple_spinner_item);
            adapterMusculos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spNivel.setAdapter(adapterNivel);

            //Hago referencia a la base de datos
            dbPrediccio = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("Ejercicios");
            dbPrediccio.addValueEventListener(this);
            dbPrediccio.addChildEventListener(this);

            rvEjercicios.setLayoutManager(new LinearLayoutManager(getContext()));
            //rvPrediccions.setLayoutManager(new GridLayoutManager(this, 2));
            rvEjercicios.addItemDecoration(new DividerItemDecoration(getContext(),
                    LinearLayoutManager.VERTICAL));
            //rvPrediccions.setAdapter(prediccioAdapter);
            ejercicioAdapter = new EjercicioAdapter(getContext(), llistaEjercicios, dia[numeroDia]);


            //hago el listenner del spinner
            spGrupoMuscular.setOnItemSelectedListener(this);
            spNivel.setOnItemSelectedListener(this);

            return rootView;
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
        public void onDataChange(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

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
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 7;
        }
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Lu";
                case 1 :
                    return "Ma";
                case 2 :
                    return "Mi";
                case 3 :
                    return "Ju";
                case 4 :
                    return "Vi";
                case 5 :
                    return "Sá";
                case 6 :
                    return "Do";
            }
            return null;
        }

    }
}
