/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.trabajo04_02;

import Servicio.DataManager;
import Vista.VentanaPrincipal;

/**
 *
 * @author Cesar
 */
public class Trabajo04_02 {
public static void main(String[] args) {
        System.out.println("Ruta del proyecto: "+DataManager.getProjectPath());
        System.out.println("Ruta para guardar los objetos : "+DataManager.getDataPath());    
        var ventanaPrincipal = new VentanaPrincipal();
        ventanaPrincipal.setVisible(true);
    }
}

