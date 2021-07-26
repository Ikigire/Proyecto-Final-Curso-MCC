/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yael.proyectofinalcurso;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 *
 * @author asm_1
 */
public class ManejadorContactos {
    private FileInputStream fis;
    private FileOutputStream fos;
    private DataInputStream dis;
    private DataOutputStream dos;
    private ArrayList<Contacto> contactos;

    private final String FOLDER_NAME = "C:/Agenda Personal";
    private final String FILE_NAME;

    public ManejadorContactos(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
        this.contactos = new ArrayList<Contacto>();
        File f = new File(this.FOLDER_NAME);
        if (!f.exists()) {
            f.mkdirs();
        }
        
        f = new File(this.FOLDER_NAME + "/" + this.FILE_NAME);
        if (!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        obtenerListaContactos();
    }

    /**
     * Permite abrir los flujos de salida y entrada hacia el archivo que contiene los datos
     * @throws FileNotFoundException En caso de que no exista el archivo se lanzará una excepción
     */
    private void abrirFlujoEscritura() throws FileNotFoundException {
        this.fos = new FileOutputStream(this.FOLDER_NAME + "/" + this.FILE_NAME);
        this.dos = new DataOutputStream(fos);
    }

    private void abrirFlujoLectura() throws FileNotFoundException {
        this.fis = new FileInputStream(this.FOLDER_NAME + "/" + this.FILE_NAME);
        this.dis = new DataInputStream(fis);
    }

    /**
     * Permite cerrar los fujos de entrada y salida hacia el archvio
     * @throws IOException
     */
    private void cerrarFlujos() throws IOException {
        if (this.fos != null){
            this.fos.close();
            this.dos.close();
        }
        if (this.fis != null){
            this.fis.close();
            this.dis.close();
        }
    }
    
    /**
     * Abre el archivo y lee los contactos guardados
     */
    private void obtenerListaContactos() {
        boolean salir = false;
        try {
            this.abrirFlujoLectura();

            do {
                try {
                    Contacto c = new Contacto();

                    c.setNombre(this.dis.readUTF());
                    c.setNumero_telefonico(this.dis.readUTF());
                    c.setCorreo(this.dis.readUTF());
                    c.setFecha_cumpleanos(this.dis.readUTF());

                    this.contactos.add(c);
                } catch (Exception e) {
                    salir = true;
                }
            } while (true && !salir);

            this.ordenarLista();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                this.cerrarFlujos();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Permite ordenar la lista de acuerdo al nombre de los contactos
     */
    private void ordenarLista(){
        this.contactos.sort((o1, o2) -> o1.getNombre().toLowerCase().compareTo(o2.getNombre().toLowerCase()));
    }

    /**
     * Almacena la lista de contactos dentro del archivo agenda
     */
    public void guardarListaContactos() {
        try {
            this.abrirFlujoEscritura();

            int indice = 0;
            while (indice < this.contactos.size()) {
                Contacto c = this.contactos.get(indice);

                this.dos.writeUTF(c.getNombre());
                this.dos.writeUTF(c.getNumero_telefonico());
                this.dos.writeUTF(c.getCorreo());
                this.dos.writeUTF(c.getFecha_cumpleanos());
                indice++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                this.cerrarFlujos();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public DefaultListModel<String> obtenerListaDeNombres() {
        DefaultListModel<String> nombres = new DefaultListModel<String>();
        int index = 0;
        while (index < this.contactos.size()){
            nombres.addElement(this.contactos.get(index).getNombre());
            index++;
        }

        return nombres;
    }

    /**
     * Obtiene el Contacto en una posición específica
     * @param posicion Índice en donde se encuentra el Contacto
     * @return Contacto en la posición solicitada
     */
    public Contacto getContacto(int posicion) {
        return this.contactos.get(posicion);
    }

    /**
     * Permite agregar un nuevo contacto a la lista de contactos
     * @param c
     */
    public void agregarContacto(Contacto c) {
        this.contactos.add(c);
        this.ordenarLista();
        this.guardarListaContactos();
    }

    /**
     * Permite editar un contacto previamente guardado
     * @param posicion Índice en donde se encuentra el contacto a editar
     * @param c Contacto con los nuevos datos
     */
    public void editarContacto(int posicion, Contacto c) {
        this.contactos.set(posicion, c);
        this.ordenarLista();
        this.guardarListaContactos();
    }

    /**
     * Permite eliminar un contacto ya guardado
     * @param posicion Índice en donde se encuentra el contacto a eliminar
     */
    public void eliminarContacto(int posicion) {
        this.contactos.remove(posicion);
        this.ordenarLista();
        this.guardarListaContactos();
    }
}
