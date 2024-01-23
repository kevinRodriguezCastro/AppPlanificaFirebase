package com.cifpceuta.appplanificafirebase.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cifpceuta.appplanificafirebase.Adapter.ItemAdapter;
import com.cifpceuta.appplanificafirebase.Clases.Practica;
import com.cifpceuta.appplanificafirebase.Clases.Usuario;
import com.cifpceuta.appplanificafirebase.R;
import com.cifpceuta.appplanificafirebase.UsuarioLogueado;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTareas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTareas extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private ArrayList<Practica> practicas;
    private FirebaseFirestore db;

    private RecyclerView recyclerView;
    private Usuario usuario;
    public FragmentTareas() {
    }

    public FragmentTareas(Usuario u) {
        // Required empty public constructor
        usuario = u;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTareas.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTareas newInstance(String param1, String param2) {
        FragmentTareas fragment = new FragmentTareas();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_tareas, container, false);
        recogerDatosTareas(v);

        return v;
    }
    private void recogerDatosTareas(View v) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();

        db.collection("practicas")
                .whereEqualTo("Curso", usuario.getCurso())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            practicas = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Practica p = new Practica();
                                p.setCreadorID(document.getString("CreadorID"));
                                p.setTitulo(document.getString("Titulo"));
                                p.setDescripcion(document.getString("Descripcion"));
                                p.setFechaIn(document.getString("FechaInicio"));
                                p.setFechaFin(document.getString("FechaFin"));
                                p.setCurso(document.getString("Curso"));
                                p.setModulo(document.getString("Modulo"));
                                practicas.add(p);
                            }
                            ItemAdapter adapter = new ItemAdapter(practicas);
                            recyclerView = (RecyclerView) v.findViewById(R.id.tRecycler);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
                        } else {
                            Toast.makeText(getContext(), "Error al leer datos " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}