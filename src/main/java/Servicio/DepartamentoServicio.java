/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;

import Modelo.Departamento;
import Modelo.Empleado;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;




/**
 *
 * @author Cesar
 */
public class DepartamentoServicio implements IDepartamentoServicio{
private final String PATH = DataManager.getDataPath()+"DepartamentoData.dat";
    private static  List<Departamento> listaDepartamentos = new ArrayList<>();

    @Override
    public Departamento crearDepartamento(Departamento departamento) {
        if (codeExists(departamento.getCodigo())) {
           throw new RuntimeException("El codigo de departamento ("+departamento.getCodigo()+") ya existe");
        }
        listaDepartamentos.add(departamento);
        
        
        try {
           saveToFile();
        } catch (IOException e) {
             Logger.getLogger(EmpresaServicio.class.getName()).log(Level.SEVERE, null, e);
        }
        return departamento; 
        
    }

    @Override
    public List<Departamento> listarDepartamentos() {
       
        try {
            
            listaDepartamentos = readFromFile();
           
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Lista de departamentos vacia!");
        }
        
        
        if (listaDepartamentos.isEmpty()) {
            throw new RuntimeException("Lista de departamentos vacia!");
        }
        
        return listaDepartamentos;
    }

    @Override
    public Departamento getDepartamentoByCode(int codigo) {
        for(Departamento dep : listaDepartamentos){
            if(dep.getCodigo() == codigo){
                return dep;
            }
        }
        return null;
    }
    
    public void asignarGerente(int codigo, Empleado empleado){
        getDepartamentoByCode(codigo).asignarGerente(empleado);
        
    }

    @Override
    public void actualizarDepartamento(int codigo, Departamento departamentoNew) {
       int posicion = getPositionDepartamento(getDepartamentoByCode(codigo));
       
        if (departamentoNew.getNombre().equals("")) {
            throw new NullPointerException("Debe ingresar el nombre de departamento");
        }
        
        if (departamentoNew.getUbicacion().equals("")) {
            throw new NullPointerException("Debe ingresar la ubicacion de departamento");
        }
       
       listaDepartamentos.get(posicion).setNombre(departamentoNew.getNombre());
       listaDepartamentos.get(posicion).asignarGerente(departamentoNew.getGerente());
       listaDepartamentos.get(posicion).setEmpresa(departamentoNew.getEmpresa());
       listaDepartamentos.get(posicion).setUbicacion(departamentoNew.getUbicacion());
       
       try {
           saveToFile();
        } catch (IOException e) {
            
        }
    
    }

    @Override
    public Departamento eliminarDepartamento(int codigo) {
       var posicion = getPositionDepartamento(getDepartamentoByCode(codigo));
       var dep = listaDepartamentos.remove(posicion);
       
        try {
           saveToFile();
        } catch (IOException e) {
            
        }
       
       return dep;
    }

    @Override
    public int getPositionDepartamento(Departamento departamento) {
        for (int i = 0; i < listaDepartamentos.size(); i++) {
            if (departamento.getCodigo() == listaDepartamentos.get(i).getCodigo()) {
                return i;
            }
        }
        return -1;
    }
    
    private boolean codeExists(int code){
        
        for (var dep: listaDepartamentos) {
            if (dep.getCodigo() == code) {
                return true;
            }
        }
        return false;
    }
      
    private void saveToFile() throws IOException{
        
        ObjectOutputStream oos = null;
       
        try {
           
            oos = new ObjectOutputStream(new FileOutputStream(new File(PATH)));
            
            oos.writeObject(listaDepartamentos);
            System.out.println("Datos de Departamentos Guardados !");
            oos.close();
     
        } catch (IOException e) {
            oos.close();
            throw new IOException("Error al escribir el archivo :"+e.getMessage());
        }
    }
    
    
    
    
    private List<Departamento> readFromFile() throws ClassNotFoundException, IOException{
        
        List<Departamento> listDeps = new ArrayList<>();
        
        ObjectInputStream ois = null;
        FileInputStream fis = null;
        try {
           fis = new FileInputStream(new File(PATH));
           
           while(fis.available() > 0){
              
               ois = new ObjectInputStream(fis);
               listDeps = (List<Departamento>)ois.readObject();
              
           }
           ois.close();
           fis.close();
           
           
        } catch (IOException e) {
           
            ois.close();
            fis.close();
            
            throw new IOException("Error al leer el archivo :"+e.getMessage());
           
            
        }
        
        
        return listDeps;
        
        
    }
    
}
