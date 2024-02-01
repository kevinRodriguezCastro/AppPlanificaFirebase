package com.cifpceuta.appplanificafirebase.Clases;

public class ActividadExtra {
    String titulo;
    String fechaInicio;
    String grupo;

    public ActividadExtra(String titulo, String fechaInicio, String grupo) {
        this.titulo = titulo;
        this.fechaInicio = fechaInicio;
        this.grupo = grupo;
    }
    public ActividadExtra() {
        this.titulo = "";
        this.fechaInicio = "";
        this.grupo = "";
    }
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }
}
