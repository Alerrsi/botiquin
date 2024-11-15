package com.example.botiquin;

public class Medicamento {

    private int id;
    private String nombre;
    private int cantidad;
    private String fechaVencimiento;
    private int miligramos;
    private String presentacion;
    private String descripcion;

    public Medicamento(int id, String nombre, int cantidad, String fechaVencimiento, int miligramos, String presentacion, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.fechaVencimiento = fechaVencimiento;
        this.miligramos = miligramos;
        this.presentacion = presentacion;
        this.descripcion = descripcion;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getMiligramos() {
        return miligramos;
    }

    public void setMiligramos(int miligramos) {
        this.miligramos = miligramos;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Medicamento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad +
                ", fechaVencimiento='" + fechaVencimiento + '\'' +
                ", miligramos=" + miligramos +
                ", presentacion='" + presentacion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
