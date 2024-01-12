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


import com.google.android.material.navigation.NavigationView;

public class UsuarioLogueado extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    Toolbar toolbar;
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

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();


        if (itemId == R.id.perfil) {

            //getSupportFragmentManager().beginTransaction().replace(R.id.fragPerfilEst, new PerfilEstudiante_Fragment()).commit();
        } else if (itemId == R.id.planificarPractica) {

        } else if (itemId == R.id.planificarExamen) {

        } else if (itemId == R.id.preferencias) {

        } else if (itemId == R.id.salir) {
            startActivity(new Intent(this, MainActivity.class));
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}