package fitness.sportgenertaion.fitnesstrainer.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fitness.sportgenertaion.fitnesstrainer.R;


@SuppressLint("ValidFragment")
public class RutinaAleatoria extends Fragment implements ValueEventListener, ChildEventListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    String idUsuario;
    DatabaseReference dbEjercicios;

    @SuppressLint("ValidFragment")
    public RutinaAleatoria(String idUsuario) {
        this.idUsuario=idUsuario;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rutina_aleatoria, container, false);

        //TextView textView = (TextView) rootView.findViewById(R.id.prueba);
        Toast.makeText(getContext(), "Entra", Toast.LENGTH_SHORT).show();
        dbEjercicios = FirebaseDatabase.getInstance()
                .getReference()
                .child("Ejercicios");
        dbEjercicios.addValueEventListener(this);
        dbEjercicios.addChildEventListener(this);
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
}