package com.cifpceuta.appplanificafirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class InicioSesion extends AppCompatActivity {
    private EditText correo, contraseña;
    private Button btnIniciar;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        correo = (EditText) findViewById(R.id.iCorreo);
        contraseña = (EditText) findViewById(R.id.iContraseña);
        btnIniciar = (Button) findViewById(R.id.btnIniciarSesion);
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               iniciarSesion();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    private void iniciarSesion(){
        mAuth.signInWithEmailAndPassword(correo.getText().toString(), contraseña.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //recogerDatos();
                            startActivity(new Intent(InicioSesion.this,UsuarioLogueado.class));
                            //Toast.makeText(InicioSesion.this, "Funciona inicio de sesion", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(InicioSesion.this, "Error inicio de sesion", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void recogerDatos(){
        DocumentReference docRef = db.collection("usuarios").document( FirebaseAuth.getInstance().getCurrentUser().getUid() );
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Usuario u = new Usuario();
                        u.setNombre(document.getData().get("Nombre").toString());
                        u.setCorreo(document.getData().get("Correo").toString());
                        u.setCurso(document.getData().get("Curso").toString());
                        u.setTurno(document.getData().get("Turno").toString());

                        Toast.makeText(InicioSesion.this,"Nombre: "+u.getNombre()+" "+u.getCurso(),Toast.LENGTH_LONG).show();
                        //Toast.makeText(InicioSesion.this,"Datos recogidos",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(InicioSesion.this,"Datos no encontrados   "+FirebaseAuth.getInstance().getCurrentUser().getUid(),Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(InicioSesion.this,"Error al leer datos "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}