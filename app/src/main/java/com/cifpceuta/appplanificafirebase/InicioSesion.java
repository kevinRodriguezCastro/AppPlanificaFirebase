package com.cifpceuta.appplanificafirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InicioSesion extends AppCompatActivity {
    private EditText correo, contraseña;
    private Button btnIniciar;
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
    }

    private void iniciarSesion(){

    }
}