package com.cifpceuta.appplanificafirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button btnIniciar,btnRegistar;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIniciar = (Button) findViewById(R.id.btnIniciar);
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), InicioSesion.class));
            }
        });

        btnRegistar = (Button) findViewById(R.id.btnRegistrar);
        btnRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), RegistrarUsuario.class));
            }
        });

        db = FirebaseFirestore.getInstance();
       // modulos();
    }
    private void modulos(){
        // Create a new user with a first and last name
        List<String> listaModulosDam2 = new ArrayList<>();
        listaModulosDam2.add("DI");
        listaModulosDam2.add("SGE");
        listaModulosDam2.add("PSP");
        listaModulosDam2.add("EIE");
        listaModulosDam2.add("AD");
        listaModulosDam2.add("PM");

        List<String> listaModulosDam1 = new ArrayList<>();
        listaModulosDam1.add("P");
        listaModulosDam1.add("FOL");
        listaModulosDam1.add("ED");
        listaModulosDam1.add("LM");
        listaModulosDam1.add("SMI");
        listaModulosDam1.add("BD");

        Map<String, Object> modulos = new HashMap<>();
        modulos.put("DAM2", listaModulosDam2);
        modulos.put("DAM1", listaModulosDam1);

        // Add a new document with a generated ID
        db.collection("modulos").document("modulos")
                .set(modulos)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void v) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
}