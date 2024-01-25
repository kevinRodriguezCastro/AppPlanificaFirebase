package com.cifpceuta.appplanificafirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrarUsuario extends AppCompatActivity {
    private EditText nombre,correo,contraseña;
    private Spinner cursos,turnos;
    private Button btnRegistrar;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        toolbar = (Toolbar) findViewById(R.id.rToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        listaCursos.add("DAM1");
        listaCursos.add("DAM2");
        /*
        listaCursos.add("DAW");
        listaCursos.add("2ºSMT");
        listaCursos.add("1ºDAW");
        listaCursos.add("1ºSMT");
         */

        ArrayAdapter<String> cursoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaCursos);
        cursoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        cursos.setAdapter(cursoAdapter);


        List<String> listaTurnos = new ArrayList<>();
        listaTurnos.add("Mañana");
        listaTurnos.add("Tarde");

        ArrayAdapter<String> turnoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaTurnos);
        turnoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        turnos.setAdapter(turnoAdapter);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    private void registrar(){
        mAuth.createUserWithEmailAndPassword(correo.getText().toString(), contraseña.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            guardarDatos();
                            startActivity(new Intent(RegistrarUsuario.this,MainActivity.class));
                        } else {
                            Toast.makeText(RegistrarUsuario.this, " Fallo, registro "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void guardarDatos(){
        // Create a new user with a first and last name
        String idUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> usuario = new HashMap<>();
        usuario.put("Nombre", nombre.getText().toString());
        usuario.put("Correo", correo.getText().toString());
        usuario.put("Curso", cursos.getSelectedItem().toString());
        usuario.put("Turno", turnos.getSelectedItem().toString());

// Add a new document with a generated ID
        db.collection("usuarios").document(idUsuario)
                .set(usuario)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void v) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegistrarUsuario.this,"Error guardar informacion extra",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}