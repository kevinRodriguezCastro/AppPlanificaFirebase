package com.cifpceuta.appplanificafirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class RegistrarUsuario extends AppCompatActivity {
    private EditText nombre,correo,contraseña;
    private Spinner cursos,turnos;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        nombre = findViewById(R.id.editTextNombre);
        correo = findViewById(R.id.editTextCorreo);
        cursos = findViewById(R.id.spinnerCurso);
        turnos = findViewById(R.id.spinnerTurno);
        contraseña = findViewById(R.id.editTextContraseña);
        btnRegistrar = findViewById(R.id.botonRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });

        List<String> listaCursos = new ArrayList<>();
        listaCursos.add("1ºDAM");
        listaCursos.add("1ºDAW");
        listaCursos.add("1ºSMT");
        listaCursos.add("2ºDAM");
        listaCursos.add("2ºDAW");
        listaCursos.add("2ºSMT");

        ArrayAdapter<String> cursoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaCursos);
        cursoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        cursos.setAdapter(cursoAdapter);


        List<String> listaTurnos = new ArrayList<>();
        listaTurnos.add("Turno Mañana");
        listaTurnos.add("Turno Tarde");

        ArrayAdapter<String> turnoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaTurnos);
        turnoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        turnos.setAdapter(turnoAdapter);
    }

    private void registrar(){

        startActivity(new Intent(this,MainActivity.class));
    }
}