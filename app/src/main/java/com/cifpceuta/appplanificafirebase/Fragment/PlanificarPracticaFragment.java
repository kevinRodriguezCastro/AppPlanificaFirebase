package com.cifpceuta.appplanificafirebase.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cifpceuta.appplanificafirebase.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlanificarPracticaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanificarPracticaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FirebaseFirestore db;

    private EditText titulo,fechaIni,fechaFin,descripcion;
    private Spinner cursos,modulos;
    private Button btnSubir;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlanificarPracticaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlanificarPracticaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlanificarPracticaFragment newInstance(String param1, String param2) {
        PlanificarPracticaFragment fragment = new PlanificarPracticaFragment();
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
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v = inflater.inflate(R.layout.fragment_planificar_practica, container, false);



        titulo = v.findViewById(R.id.ppTitulo);
        fechaIni = v.findViewById(R.id.ppFechaIni);
        fechaIni.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mostrarSelectorFecha(fechaIni);
                    //fechaIni = dateString;
                }
                return false;
            }
        });


        fechaFin = v.findViewById(R.id.ppFechaFin);
        fechaFin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mostrarSelectorFecha(fechaFin);
                    //fechaIni = dateString;
                }
                return false;
            }
        });
        descripcion = v.findViewById(R.id.ppDescripcion);

        cursos = v.findViewById(R.id.ppCurso);
        modulos = v.findViewById(R.id.ppModulo);

        btnSubir = v.findViewById(R.id.ppBtnSubir);
        btnSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatos();
            }
        });

        List<String> listaCursos = new ArrayList<>();
        listaCursos.add("1ºDAM");
        listaCursos.add("1ºDAW");
        listaCursos.add("1ºSMT");
        listaCursos.add("2ºDAM");
        listaCursos.add("2ºDAW");
        listaCursos.add("2ºSMT");
        ArrayAdapter<String> cursoAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, listaCursos);
        cursoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cursos.setAdapter(cursoAdapter);

        List<String> listaTurnos = new ArrayList<>();
        listaTurnos.add("Mañana");
        listaTurnos.add("Tarde");
        ArrayAdapter<String> turnoAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, listaTurnos);
        turnoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modulos.setAdapter(turnoAdapter);


       return v;
    }
    private void mostrarSelectorFecha(EditText fecha){
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().build();

        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                // Formatear la fecha seleccionada
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dateString = dateFormat.format(new Date(selection));

                // Mostrar la fecha en el TextView
                fecha.setText(dateString);

            }
        });

        if (getActivity() != null) {
            datePicker.show(getActivity().getSupportFragmentManager(), datePicker.toString());
        }
    }
    private void guardarDatos(){
        // Create a new user with a first and last name
        String idUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> practica = new HashMap<>();
        practica.put("CreadorID", idUsuario);
        practica.put("Titulo", titulo.getText().toString());
        practica.put("FechaInicio", fechaIni.getText().toString());
        practica.put("FechaFin", fechaFin.getText().toString());
        practica.put("Curso", cursos.getSelectedItem().toString());
        practica.put("Modulo", modulos.getSelectedItem().toString());
        practica.put("Descripcion", descripcion.toString());

        // Add a new document with a generated ID
        db.collection("practicas").document(idUsuario)
                .set(practica)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void v) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       // Toast.makeText(PlanificarPracticaFragment.this,"Error guardar informacion extra",Toast.LENGTH_SHORT).show();
                    }
                });

    }

}