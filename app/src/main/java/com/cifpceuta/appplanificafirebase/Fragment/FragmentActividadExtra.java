package com.cifpceuta.appplanificafirebase.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cifpceuta.appplanificafirebase.Adapter.ActividadExtraAdapter;
import com.cifpceuta.appplanificafirebase.Clases.ActividadExtra;
import com.cifpceuta.appplanificafirebase.Clases.Usuario;
import com.cifpceuta.appplanificafirebase.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentActividadExtra#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentActividadExtra extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ActividadExtraAdapter adapter;
    private RecyclerView recycler;
    private FloatingActionButton icon;
    private ArrayList<ActividadExtra> listaActividadesExtra;
    private Usuario usuario;
    public FragmentActividadExtra() {
        // Required empty public constructor
    }
    public FragmentActividadExtra(ArrayList<ActividadExtra> listaActividadesExtra, Usuario usuario) {
        this.listaActividadesExtra = listaActividadesExtra;
        this.usuario = usuario;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentActividadExtra.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentActividadExtra newInstance(String param1, String param2) {
        FragmentActividadExtra fragment = new FragmentActividadExtra();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_actividad_extra, container, false);


        //listaActividadesExtra = new ArrayList<>(); // Cuidado con esto

        adapter = new ActividadExtraAdapter(listaActividadesExtra);

        recycler = (RecyclerView) v.findViewById(R.id.recyclerActividad);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(v.getContext()));


        icon = v.findViewById(R.id.icon);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context c = requireContext();
                Toast.makeText(v.getContext(),"hola",Toast.LENGTH_LONG).show();
                Dialog dialog = new Dialog(c);
                dialog.show();
                dialog.setContentView(R.layout.layout_actividad_extra);
                EditText titulo = dialog.findViewById(R.id.tituloActividad);
                EditText fecha = dialog.findViewById(R.id.fechaActividad);

                Spinner grupos = dialog.findViewById(R.id.spinnerActicvidad);
                ArrayList<String> listaGrupos = new ArrayList<>();
                listaGrupos.add("DAM1");
                listaGrupos.add("DAM2");

                ArrayAdapter<String> grupoAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, listaGrupos);
                grupoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                grupos.setAdapter(grupoAdapter);


                Button  btnAñadir = dialog.findViewById(R.id.btnAñadirActividad);
                btnAñadir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Guardar datos en firestore
                        ActividadExtra actividad = new ActividadExtra(titulo.getText().toString(),fecha.getText().toString(),grupos.getSelectedItem().toString());
                        guardarDatos(actividad);
                        if (grupos.getSelectedItem().toString().equalsIgnoreCase(usuario.getCurso()))
                        listaActividadesExtra.add(actividad);
                        adapter.setList_item(listaActividadesExtra);
                        dialog.dismiss();
                    }
                });
            }
        });
        return v;
    }

    private void guardarDatos(ActividadExtra actividad){
        // Create a new user with a first and last name
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String idUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> actividadExtra = new HashMap<>();
        actividadExtra.put("Titulo", actividad.getTitulo());
        actividadExtra.put("Grupo", actividad.getGrupo());
        actividadExtra.put("FechaInicio", actividad.getFechaInicio());


        // Add a new document with a generated ID
        db.collection("actividadExtra")
                .add(actividadExtra)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(),"Actividad publicada",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"Error al subir practica",Toast.LENGTH_SHORT).show();
                    }
                });

    }
}