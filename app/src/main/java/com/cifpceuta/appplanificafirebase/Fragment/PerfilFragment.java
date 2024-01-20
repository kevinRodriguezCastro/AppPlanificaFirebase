package com.cifpceuta.appplanificafirebase.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cifpceuta.appplanificafirebase.Clases.Usuario;
import com.cifpceuta.appplanificafirebase.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "usuario";


    // TODO: Rename and change types of parameters
    private Usuario usuario;


    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(Usuario u) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, u);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            usuario =(Usuario)getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);
        TextView nombre,correo,curso,turno;

        nombre = (TextView) v.findViewById(R.id.pNombre);
        correo = (TextView) v.findViewById(R.id.pCorreo);
        curso = (TextView) v.findViewById(R.id.pCurso);
        turno = (TextView) v.findViewById(R.id.pTurno);

        nombre.setText("Nombre: "+usuario.getNombre());
        correo.setText("Correo: "+usuario.getCorreo());
        curso.setText("Curso: "+usuario.getCurso());
        turno.setText("Turno: "+usuario.getTurno());

        return v;
    }
}