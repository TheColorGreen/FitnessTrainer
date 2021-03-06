package fitness.sportgenertaion.fitnesstrainer;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fitness.sportgenertaion.fitnesstrainer.Classes.ActualizarHistorial;
import fitness.sportgenertaion.fitnesstrainer.Classes.DateAcciones;
import fitness.sportgenertaion.fitnesstrainer.Classes.EjercicioAdapter;
import fitness.sportgenertaion.fitnesstrainer.Classes.IdUsuario;
import fitness.sportgenertaion.fitnesstrainer.Classes.Idioma;
import fitness.sportgenertaion.fitnesstrainer.Classes.Rutina;
import fitness.sportgenertaion.fitnesstrainer.Classes.RutinaAcciones;

public class CrearRutinas extends AppCompatActivity implements View.OnClickListener {
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
Button bGuardar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_rutinas);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = findViewById(R.id.tabs);
        bGuardar=findViewById(R.id.bGuargar);
        bGuardar.setOnClickListener(this);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(mViewPager);
            }
        });
        Bundle parametros = this.getIntent().getExtras();
        idUsuario = IdUsuario.getIdUsuario();


    }

    //Anyadeix l'exercici al array del dia que toca. Es per quan l'usuari marca el check
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
//Borra l'exercici de la rutina temporal, es per quan l'usuari desmarca el checkbox
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
//Guarda la rutina creada a la teva rutina
    @Override
    public void onClick(View view) {
        RutinaAcciones.eliminarRutina();
        for (int x = 0; x < lunes.size(); x++) {
            RutinaAcciones.anyadir("Lunes", lunes.get(x));
        }
        for (int x = 0; x < martes.size(); x++) {
            RutinaAcciones.anyadir("Martes", martes.get(x));
        }
        for (int x = 0; x < miercoles.size(); x++) {
            RutinaAcciones.anyadir("Miercoles", miercoles.get(x));
        }
        for (int x = 0; x < jueves.size(); x++) {
            RutinaAcciones.anyadir("Jueves", jueves.get(x));
        }
        for (int x = 0; x < viernes.size(); x++) {
            RutinaAcciones.anyadir("Viernes", viernes.get(x));
        }
        for (int x = 0; x < sabado.size(); x++) {
            RutinaAcciones.anyadir("Sabado", sabado.get(x));
        }
        for (int x = 0; x < domingo.size(); x++) {
            RutinaAcciones.anyadir("Domingo", domingo.get(x));
        }
        lunes = new ArrayList<String>();
        martes = new ArrayList<String>();
        miercoles = new ArrayList<String>();
        jueves = new ArrayList<String>();
        viernes = new ArrayList<String>();
        sabado = new ArrayList<String>();
        domingo = new ArrayList<String>();

        //Actualitrza la fecha del ultim dia modificat de rutina
        DateAcciones.ActualizarFechaRutina();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }


    /**
     * A placeholder fragment containing a simple view.
     */


    public static class PlaceholderFragment extends Fragment implements ValueEventListener, AdapterView.OnItemSelectedListener {


        DatabaseReference dbPrediccio;
        String nivel = "5";
        String grupoMuscular = "5";
        Spinner spGrupoMuscular;
        Spinner spNivel;

        private RecyclerView rvEjercicios;
        private List<Rutina> llistaEjercicios = new ArrayList<Rutina>();
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
            spNivel = rootView.findViewById(R.id.spNivel);
            spGrupoMuscular = rootView.findViewById(R.id.spMusculo);

            rvEjercicios = rootView.findViewById(R.id.rvEjercicios);


            //Hago que en los spinners salgan los arrays de las opciones que tienes a elegir
            ArrayAdapter<CharSequence> adapterMusculos = ArrayAdapter.createFromResource(getContext(), R.array.musculos, android.R.layout.simple_spinner_item);
            adapterMusculos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spGrupoMuscular.setAdapter(adapterMusculos);


            ArrayAdapter<CharSequence> adapterNivel = ArrayAdapter.createFromResource(getContext(), R.array.dificultad, android.R.layout.simple_spinner_item);
            adapterMusculos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spNivel.setAdapter(adapterNivel);

            //Hago referencia a la base de datos
            dbPrediccio = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("Ejercicios-"+ Idioma.getIdioma());
            dbPrediccio.addValueEventListener(this);


            rvEjercicios.setLayoutManager(new LinearLayoutManager(getContext()));
            rvEjercicios.addItemDecoration(new DividerItemDecoration(getContext(),
                    LinearLayoutManager.VERTICAL));

            //Depende de en que fragment estes determina el dia en el qual estas, eso se hace para llamar a la base de datos
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
        public void onDataChange(DataSnapshot dataSnapshot) {
            llistaEjercicios.removeAll(llistaEjercicios);

            for (DataSnapshot element : dataSnapshot.getChildren()) {
                //Si coinciden la dificultad i el nivel entra
                if ((element.child("dificultad").getValue().toString().equals(this.nivel) && (element.child("musculos").getValue().toString().equals(this.grupoMuscular))) || (this.nivel.equals("5") && this.grupoMuscular.equals("5")) || (this.grupoMuscular.equals("5") && element.child("dificultad").getValue().toString().equals(this.nivel)) || this.nivel.equals("5") && element.child("musculos").getValue().toString().equals(this.grupoMuscular)) {

                    Rutina ejercicio = new Rutina(Boolean.parseBoolean(element.getValue().toString()), element.getKey().toString());
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

            //Mira la dificultad y nivel para despues pasarselo al ondatachange y que asi solo te salgan los ejercicios de ese nivel i dificultad
            Resources res = getResources();
            String[] musculos= res.getStringArray(R.array.musculos);
            String[] dificultad= res.getStringArray(R.array.dificultad);

            if (spNivel.getSelectedItem().toString().equals(dificultad[0])) {
                this.nivel = "5";
            } else if (spNivel.getSelectedItem().toString().equals(dificultad[1])) {
                this.nivel = "1";
            } else if (spNivel.getSelectedItem().toString().equals(dificultad[2])) {
                this.nivel = "2";
            } else {
                this.nivel = "3";
            }

            if (spGrupoMuscular.getSelectedItem().toString().equals(musculos[0])) {
                this.grupoMuscular = "5";
            } else if (spGrupoMuscular.getSelectedItem().toString().equals(musculos[1])) {
                this.grupoMuscular = "1";
            } else if (spGrupoMuscular.getSelectedItem().toString().equals(musculos[2])) {
                this.grupoMuscular = "2";
            } else if (spGrupoMuscular.getSelectedItem().toString().equals(musculos[3])) {
                this.grupoMuscular = "3";
            } else {
                this.grupoMuscular = "4";
            }

//LLama al firebase
            dbPrediccio.addValueEventListener(this);

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
            //  return 7;
            return 7;
        }
//
        @Override
        public CharSequence getPageTitle(int position) {
//Dice lo que te saldra arriba para saber en que dia estas
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
