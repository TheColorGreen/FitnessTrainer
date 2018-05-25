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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import fitness.sportgenertaion.fitnesstrainer.Classes.IdUsuario;
import fitness.sportgenertaion.fitnesstrainer.Classes.Rutina;
import fitness.sportgenertaion.fitnesstrainer.Classes.RutinaAdapter;
//Es la activity de la teva rutina
public class Dias extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    static String idUsuario;
    public static String dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dias);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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

        Bundle parametros = this.getIntent().getExtras();
        idUsuario = IdUsuario.getIdUsuario();

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        RecyclerView rvEjercicios;
        List<Rutina> llistaRutina = new ArrayList<Rutina>();
        RutinaAdapter rutinaAdapter;
        ArrayList<String> llistaEjerciciosPuestos = new ArrayList<String>();

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
            View rootView = inflater.inflate(R.layout.fragment_dias, container, false);


            rvEjercicios = rootView.findViewById(R.id.rvRutina);
            //


            String dia2 = "Lunes";

            //Miro hen quin fragment esta per saber quin dia tinc que mostrar de la sebarutina

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
            //Floating button per anyadir nous exercicis
            FloatingActionButton fab = rootView.findViewById(R.id.fab);
            final String finalDia = dia2;
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), ModificarRutina.class);
                    intent.putExtra("idUsuario", idUsuario);
                    intent.putExtra("dia", finalDia);
                    intent.putExtra("miLista", llistaEjerciciosPuestos);
                    startActivity(intent);

                }
            });

            rvEjercicios.setLayoutManager(new LinearLayoutManager(getContext()));
            rvEjercicios.addItemDecoration(new DividerItemDecoration(getContext(),
                    LinearLayoutManager.VERTICAL));
            //Iniciaitzo variables
            rutinaAdapter = new RutinaAdapter(getContext(), llistaRutina, dia, idUsuario);

            DatabaseReference rutina = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("users" + "/" + idUsuario + "/Rutina/" + dia);

            //Faic un forsingle value listener perque nomes ehem carregui una vegada. Aixo ho faic per reduir carrega
            rutina.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    llistaRutina.removeAll(llistaRutina);
                    for (DataSnapshot element : snapshot.getChildren()) {

                        Rutina rutina = new Rutina(Boolean.parseBoolean(element.getValue().toString()), element.getKey().toString());
                        llistaEjerciciosPuestos.add(element.getKey().toString());
                        llistaRutina.add(rutina);

                    }
                    rvEjercicios.setAdapter(rutinaAdapter);
                    rvEjercicios.scrollToPosition(llistaRutina.size() - 1);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            return rootView;
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
//El que surt adal que t'indica en quin dia estas
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
