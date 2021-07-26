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
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 *
 * @author asm_1
 */
public class ManejadorUsuarios {
    private FileInputStream fis;
    private FileOutputStream fos;
    private DataInputStream dis;
    private DataOutputStream dos;

    private final String FILE_NAME = "usuarios.dat";
    private final String FOLDER_NAME = "C:/Agenda Personal";

    public ManejadorUsuarios() {
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
    }

    /**
     * Permite abrir los flujos de salida y entrada hacia el archivo que contiene los datos
     * @throws FileNotFoundException En caso de que no exista el archivo se lanzará una excepción
     */
    private void abrirFlujoEscritura() throws FileNotFoundException {
        this.fos = new FileOutputStream(this.FOLDER_NAME + "/" + this.FILE_NAME, true);
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
     * Permite hacer el login de usuario
     * @param usuario Nombre de usuario a buscar
     * @param password Contraseña del usuario
     * @return Path hacia el archivo que contiene los contactos del usuario
     */
    public String login(String usuario, String password) {
        boolean salir = false;
        String file_name = null;
        try {
            this.abrirFlujoLectura();

            do {
                try {
                    String user = "", pass = "";

                    user = this.dis.readUTF();
                    pass = this.dis.readUTF();

                    
                    if (user.equals(usuario) && pass.equals(password)){
                        file_name = "Agenda_" + usuario + ".dat";
                        salir = true;
                    }
                } catch (Exception e) {
                    salir = true;
                }
            } while (true && !salir);
        } catch (Exception e) {}
        finally {
            try {
                this.cerrarFlujos();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return file_name;
    }

    /**
     * Permite agregar un nuevo usuario para acceder a su agenda
     * @param usuario Nombre de usuario a guardar
     * @param password Contraseña del usuario
     * @return true si fue posible agregar el nuevo usuario, false si no
     */
    public boolean agregarUsuario(String usuario, String password) {
        boolean salir = false, existe = false;
        try {
            this.abrirFlujoLectura();

            do {
                try {
                    String user = "";

                    user = this.dis.readUTF();
                    this.dis.readUTF();

                    
                    if (user.equals(usuario)){
                        salir = true;
                        existe = true;
                    }
                } catch (Exception e) {
                    salir = true;
                }
            } while (true && !salir);

            if (!existe) {
                this.abrirFlujoEscritura();
                this.dos.writeUTF(usuario);
                this.dos.writeUTF(password);
            } else {
                
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

        return !existe;
    }
}
