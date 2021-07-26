/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yael.proyectofinalcurso;

/**
 *
 * @author asm_1
 */
public class Contacto {
    private String nombre;
    private String numero_telefonico;
    private String correo;
    private String fecha_cumpleanos;

    public Contacto() {
    }

    public Contacto(String nombre, String numero_telefonico, String correo, String fecha_cumpleanos) {
        this.nombre = nombre;
        this.numero_telefonico = numero_telefonico;
        this.correo = correo;
        this.fecha_cumpleanos = fecha_cumpleanos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero_telefonico() {
        return numero_telefonico;
    }

    public void setNumero_telefonico(String numeros_telefonico) {
        this.numero_telefonico = numeros_telefonico;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFecha_cumpleanos() {
        return fecha_cumpleanos;
    }

    public void setFecha_cumpleanos(String fecha_cumpleanos) {
        this.fecha_cumpleanos = fecha_cumpleanos;
    }
}
