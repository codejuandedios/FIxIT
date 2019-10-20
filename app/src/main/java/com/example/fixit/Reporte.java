package com.example.fixit;

import java.time.LocalDateTime;

public class Reporte {

    private int idReporte;
    private int carne;
    private String TipoProblema;
    private String Descripcion;
    private String Imagen;
    private String Modulo;
    private String Salon;
    private String fecha;
    private int estado;

    public Reporte(int idReporte, int carne, String tipoProblema, String descripcion, String imagen, String modulo, String salon, String fecha, int estado) {
        this.idReporte = idReporte;
        this.carne = carne;
        this.TipoProblema = tipoProblema;
        Descripcion = descripcion;
        Imagen = imagen;
        Modulo = modulo;
        Salon = salon;
        this.fecha = fecha;
        this.estado = estado;
    }

    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public int getCarne() {
        return carne;
    }

    public void setCarne(int carne) {
        this.carne = carne;
    }

    public String getTipoProblema() {
        return TipoProblema;
    }

    public void setTipoProblema(String tipoProblema) {
        TipoProblema = tipoProblema;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public String getModulo() {
        return Modulo;
    }

    public void setModulo(String modulo) {
        Modulo = modulo;
    }

    public String getSalon() {
        return Salon;
    }

    public void setSalon(String salon) {
        Salon = salon;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
