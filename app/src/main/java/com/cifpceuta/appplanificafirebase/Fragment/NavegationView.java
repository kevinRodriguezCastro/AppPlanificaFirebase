package com.cifpceuta.appplanificafirebase.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.Toast;

import com.cifpceuta.appplanificafirebase.Adapter.ItemAdapter;
import com.cifpceuta.appplanificafirebase.Clases.Practica;
import com.cifpceuta.appplanificafirebase.Clases.Usuario;
import com.cifpceuta.appplanificafirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavegationView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavegationView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Usuario usuario;
    private RecyclerView recycle;
    private ArrayList<Practica> practicas;
    public NavegationView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NavegationView.
     */
    // TODO: Rename and change types and number of parameters
    public static NavegationView newInstance(String param1, String param2) {
        NavegationView fragment = new NavegationView();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_navegation_view, container, false);
        TabLayout tabla = v.findViewById(R.id.tableLayout);
        recogerDatosTareas(v);
        tabla.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 1:

                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //ViewPager2 para cambiar haciendo deslizando
        return v;
    }
    private void recogerDatosTareas(View v) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
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
                            recycle = (RecyclerView) v.findViewById(R.id.nRecycler);
                            recycle.setAdapter(adapter);
                            recycle.setLayoutManager(new LinearLayoutManager(v.getContext()));
                        } else {
                            Toast.makeText(getContext(), "Error al leer datos " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private ArrayList<Practica> semanaDelMes(int semana){

        ArrayList<Practica> practicasPorSemanas = new ArrayList<>();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaPractica;

        int semanaPractica;
        for (Practica tmp:practicas){
            fechaPractica = LocalDate.parse(tmp.getFechaFin(),formato);
            if (fechaPractica.getYear() == LocalDate.now().getYear() &&  fechaPractica.getMonth() == LocalDate.now().getMonth()){
                semanaPractica = fechaPractica.get(WeekFields.of(DayOfWeek.MONDAY,1).weekOfMonth());
                if (semana == semanaPractica){
                    practicasPorSemanas.add(tmp);
                }
            }
        }
        return practicasPorSemanas;
    }
}