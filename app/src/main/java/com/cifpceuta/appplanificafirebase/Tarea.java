package com.cifpceuta.appplanificafirebase;

public class Tarea {
    private String curso;
    private String modulo;
    private String descripcion;
    private String fechaIn;
    private String fechaFin;

    public Tarea() {
    }

    public Tarea(String curso, String modulo, String descripcion, String fechaIn, String fechaFin) {
        this.curso = curso;
        this.modulo = modulo;
        this.descripcion = descripcion;
        this.fechaIn = fechaIn;
        this.fechaFin = fechaFin;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaIn() {
        return fechaIn;
    }

    public void setFechaIn(String fechaIn) {
        this.fechaIn = fechaIn;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }
}
