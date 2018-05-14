package fitness.sportgenertaion.fitnesstrainer;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
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
import fitness.sportgenertaion.fitnesstrainer.Classes.RutinaAcciones;

public class CrearRutinas extends AppCompatActivity {
    static String idUsuario;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    static ArrayList<String> lunes = new ArrayList<String>();
    static ArrayList<String> martes = new ArrayList<String>();
    static ArrayList<String> miercoles = new ArrayList<String>();
    static ArrayList<String> jueves = new ArrayList<String>();
    static ArrayList<String> viernes = new ArrayList<String>();
    static ArrayList<String> sabado = new ArrayList<String>();
    static ArrayList<String> domingo = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_rutinas);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = findViewById(R.id.tabs);

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(mViewPager);
            }
        });
        Bundle parametros = this.getIntent().getExtras();
        if (parametros != null) {
            idUsuario = parametros.getString("idUsuario");

        }



    }

    public static void RutinaTemporal(String dia, String ejercicio) {
        if (dia.equals("Lunes")) {
            lunes.add(ejercicio);
        } else if (dia.equals("Martes")) {
            martes.add(ejercicio);
        } else if (dia.equals("Miercoles")) {
            miercoles.add(ejercicio);
        } else if (dia.equals("Jueves")) {
            jueves.add(ejercicio);
        } else if (dia.equals("Viernes")) {
            viernes.add(ejercicio);
        } else if (dia.equals("Sabado")) {
            sabado.add(ejercicio);
        } else if (dia.equals("Domingo")) {
            domingo.add(ejercicio);
        }
    }

    public static void BorrarRutinaTemporal(String dia, String ejercicio) {
        if (dia.equals("Lunes")) {
            for (int x = 0; x < lunes.size(); x++) {
                if (lunes.get(x) == ejercicio) {
                    lunes.remove(x);

                }
            }
        } else if (dia.equals("Martes")) {
            for (int x = 0; x < jueves.size(); x++) {
                if (martes.get(x) == ejercicio) {
                    martes.remove(x);

                }
            }
        } else if (dia.equals("Miercoles")) {
            for (int x = 0; x < miercoles.size(); x++) {
                if (miercoles.get(x) == ejercicio) {
                    miercoles.remove(x);

                }
            }
        } else if (dia.equals("Jueves")) {
            for (int x = 0; x < jueves.size(); x++) {
                if (jueves.get(x) == ejercicio) {
                    jueves.remove(x);

                }
            }
        } else if (dia.equals("Viernes")) {
            for (int x = 0; x < viernes.size(); x++) {
                if (viernes.get(x) == ejercicio) {
                    viernes.remove(x);

                }
            }

        } else if (dia.equals("Sabado")) {
            for (int x = 0; x < sabado.size(); x++) {
                if (sabado.get(x) == ejercicio) {
                    sabado.remove(x);

                }
            }

        } else if (dia.equals("Domingo")) {
            for (int x = 0; x < domingo.size(); x++) {
                if (domingo.get(x) == ejercicio) {
                    domingo.remove(x);

                }
            }

        }

    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements ValueEventListener, ChildEventListener, AdapterView.OnItemSelectedListener, View.OnClickListener {


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
        private static final String ARG_SECTION_NUMBER = "0";

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
            String dias = "Lunes";
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                dias = "Lunes";
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                dias = "Martes";
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
                dias = "Miercoles";
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 4) {
                dias = "Jueves";
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 5) {
                dias = "Viernes";
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 6) {
                dias = "Sabado";
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 7) {
                dias = "Domingo";
            }
            //rvPrediccions.setAdapter(prediccioAdapter);
            ejercicioAdapter = new EjercicioAdapter(getContext(), llistaEjercicios, dias);


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
            llistaEjercicios.removeAll(llistaEjercicios);
            for (DataSnapshot element : dataSnapshot.getChildren()) {
                if ((element.child("dificultad").getValue().toString().equals(this.nivel) && (element.child("musculos").getValue().toString().equals(this.grupoMuscular))) || (this.nivel.equals("5") && this.grupoMuscular.equals("5")) || (this.grupoMuscular.equals("5") && element.child("dificultad").getValue().toString().equals(this.nivel)) || this.nivel.equals("5") && element.child("musculos").getValue().toString().equals(this.grupoMuscular)) {

                    Ejercicio ejercicio = new Ejercicio(element.getKey().toString(), element.child("descripcion").getValue().toString(), element.child("foto").getValue().toString(), Integer.valueOf(element.child("dificultad").getValue().toString()), Integer.valueOf(element.child("musculos").getValue().toString()));

                    llistaEjercicios.add(ejercicio);
                }
            }
            rvEjercicios.setAdapter(ejercicioAdapter);
            rvEjercicios.scrollToPosition(llistaEjercicios.size() - 1);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (spNivel.getSelectedItem().toString().equals("Todos")) {
                this.nivel = "5";
            } else if (spNivel.getSelectedItem().toString().equals("Principiante")) {
                this.nivel = "1";
            } else if (spNivel.getSelectedItem().toString().equals("Entusiasta")) {
                this.nivel = "2";
            } else {
                this.nivel = "3";
            }

            if (spGrupoMuscular.getSelectedItem().toString().equals("Todos")) {
                this.grupoMuscular = "5";
            } else if (spGrupoMuscular.getSelectedItem().toString().equals("Espalda y Biceps")) {
                this.grupoMuscular = "1";
            } else if (spGrupoMuscular.getSelectedItem().toString().equals("Pecho y Triceps")) {
                this.grupoMuscular = "2";
            } else if (spGrupoMuscular.getSelectedItem().toString().equals("Abdominales")) {
                this.grupoMuscular = "3";
            } else {
                this.grupoMuscular = "4";
            }

            bguardar.setOnClickListener(this);
            dbPrediccio.addValueEventListener(this);
            dbPrediccio.addChildEventListener(this);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

        @Override
        public void onClick(View view) {
            RutinaAcciones.eliminarRutina(idUsuario);
            for (int x = 0; x < lunes.size(); x++) {
                RutinaAcciones.anyadir("Lunes", lunes.get(x), idUsuario);
            }
            for (int x = 0; x < martes.size(); x++) {
                RutinaAcciones.anyadir("Martes", martes.get(x), idUsuario);
            }
            for (int x = 0; x < miercoles.size(); x++) {
                RutinaAcciones.anyadir("Miercoles", miercoles.get(x), idUsuario);
            }
            for (int x = 0; x < jueves.size(); x++) {
                RutinaAcciones.anyadir("Jueves", jueves.get(x), idUsuario);
            }
            for (int x = 0; x < viernes.size(); x++) {
                RutinaAcciones.anyadir("Viernes", viernes.get(x), idUsuario);
            }
            for (int x = 0; x < sabado.size(); x++) {
                RutinaAcciones.anyadir("Sabado", sabado.get(x), idUsuario);
            }
            for (int x = 0; x < domingo.size(); x++) {
                RutinaAcciones.anyadir("Domingo", domingo.get(x), idUsuario);
            }
            lunes = new ArrayList<String>();
            martes = new ArrayList<String>();
            miercoles = new ArrayList<String>();
            jueves = new ArrayList<String>();
            viernes = new ArrayList<String>();
            sabado = new ArrayList<String>();
            domingo = new ArrayList<String>();
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
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

            switch (position) {
                case 0:
                    return "Lu";
                case 1:
                    return "Ma";
                case 2:
                    return "Mi";
                case 3:
                    return "Ju";
                case 4:
                    return "Vi";
                case 5:
                    return "Sá";
                case 6:
                    return "Do";
            }
            return null;
        }

    }
}
