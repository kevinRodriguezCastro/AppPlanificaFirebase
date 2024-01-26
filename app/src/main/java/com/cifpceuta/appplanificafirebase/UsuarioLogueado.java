package com.cifpceuta.appplanificafirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


import com.cifpceuta.appplanificafirebase.Clases.Practica;
import com.cifpceuta.appplanificafirebase.Clases.Usuario;
import com.cifpceuta.appplanificafirebase.Fragment.BlankFragment;
import com.cifpceuta.appplanificafirebase.Fragment.FragmentTareas;
import com.cifpceuta.appplanificafirebase.Fragment.NavegationView;
import com.cifpceuta.appplanificafirebase.Fragment.PerfilFragment;
import com.cifpceuta.appplanificafirebase.Fragment.PlanificarPracticaFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class UsuarioLogueado extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Usuario usuario;
    private ArrayList<Practica> practicas;
    private FirebaseFirestore db;
    private HashMap<String,ArrayList<String>> listaModulos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_logueado);

        toolbar = (findViewById(R.id.toolbar));
        setSupportActionBar(findViewById(R.id.toolbar));

        drawerLayout = findViewById(R.id.drawerLayout);

        toggle = //new ActionBarDrawerToggle(this, drawerLayout,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                 new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        db = FirebaseFirestore.getInstance();
        recogerDatos();
        //recogerDatosTareas();
        recogerDatosModulos();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.perfil) {
            //Esto cambia el fragmen y va en el oncreate para que salte por defecto
            PerfilFragment p = PerfilFragment.newInstance(usuario);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, p).commit();

        } else if (itemId == R.id.consultarTarea) {
           // recogerDatosTareas();
            FragmentTareas f = new FragmentTareas(usuario);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, f).commit();

        } else if (itemId == R.id.consultarTareaSemana) {
            NavegationView n = new NavegationView(usuario);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmento,n).commit();

        } else if (itemId == R.id.planificarPractica) {
            PlanificarPracticaFragment p = new PlanificarPracticaFragment(listaModulos);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, p).commit();
        } else if (itemId == R.id.planificarExamen) {

        } else if (itemId == R.id.preferencias) {

        } else if (itemId == R.id.salir) {
            startActivity(new Intent(this, MainActivity.class));
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void recogerDatos(){
        DocumentReference docRef = db.collection("usuarios").document( FirebaseAuth.getInstance().getCurrentUser().getUid() );
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        usuario = new Usuario();
                        usuario.setNombre(document.getData().get("Nombre").toString());
                        usuario.setCorreo(document.getData().get("Correo").toString());
                        usuario.setCurso(document.getData().get("Curso").toString());
                        usuario.setTurno(document.getData().get("Turno").toString());

                        BlankFragment fragmentoDefecto = BlankFragment.newInstance(usuario);
                        //if (savedInstanceState == null){
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, fragmentoDefecto).commit();
                        //}

                        //Toast.makeText(UsuarioLogueado.this,"Bienvenido "+usuario.getNombre()+" de "+usuario.getCurso(),Toast.LENGTH_LONG).show();
                        //Toast.makeText(InicioSesion.this,"Datos recogidos",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UsuarioLogueado.this,"Datos no encontrados   "+FirebaseAuth.getInstance().getCurrentUser().getUid(),Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UsuarioLogueado.this,"Error al leer datos "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void recogerDatosModulos(){
        DocumentReference docRef = db.collection("modulos").document("modulos");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        listaModulos = new HashMap<>();
                        ArrayList<String> modulosDam1 = new ArrayList<>();
                        ArrayList<String> modulosDam2 = new ArrayList<>();
                        modulosDam1 = (ArrayList<String>) document.getData().get("DAM1");
                        modulosDam2 = (ArrayList<String>) document.getData().get("DAM2");

                        listaModulos.put("DAM1",modulosDam1);
                        listaModulos.put("DAM2",modulosDam2);

                    } else {
                        Toast.makeText(UsuarioLogueado.this,"Datos no encontrados   "+FirebaseAuth.getInstance().getCurrentUser().getUid(),Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UsuarioLogueado.this,"Error al leer datos "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}