/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;

import Modelo.Empresa;
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
public class EmpresaServicio implements IEmpresaServicio{
    
    //definir el nombre del archivo
    private final String PATH = DataManager.getDataPath()+"EmpresaData.dat";
    
    private static  List<Empresa> listaEmpresas = new ArrayList<>();
   
    @Override
    public Empresa crearEmpresa(Empresa empresa) {
        
        if (codeExists(empresa.getCodigo())) {
            throw new RuntimeException("Codigo de la empresa ya existe!");
        }
        listaEmpresas.add(empresa);
        
        // guarda toda la lista de empresas en el archivo .dat
        try {
            saveToFile();
        } catch (IOException ex) {
            Logger.getLogger(EmpresaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return empresa;
    }

    @Override
    public List<Empresa> listarEmpresas() {
        
        try {
            
            listaEmpresas = readFromFile();
            
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(EmpresaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (listaEmpresas.isEmpty()) {
           throw new RuntimeException("Lista de empresas vacia!");
        }
        return listaEmpresas;
        
    }

    @Override
    public Empresa getEmpresaByCode(int codigo) {
        for (var emp: listaEmpresas){
            if (emp.getCodigo() == codigo){
                return emp;
            }
        }
        return null;
    }

    @Override
    public void actualizarEmpresa(int codigo, Empresa empresaNueva) {
        int posicion  = getPositionEmpresa(getEmpresaByCode(codigo));
        
        if (empresaNueva.getNombre().equals("")) {
           throw new NullPointerException("Empresa debe tener un nombre");
        }
        
        if (empresaNueva.getNombreFundador().equals("")) {
           throw new NullPointerException("Empresa debe tener un fundador");
        }
        
        listaEmpresas.get(posicion).setNombre(empresaNueva.getNombre());
        listaEmpresas.get(posicion).setNombreFundador(empresaNueva.getNombreFundador());
        listaEmpresas.get(posicion).setPais(empresaNueva.getPais());
        listaEmpresas.get(posicion).setDireccion(empresaNueva.getDireccion());
        listaEmpresas.get(posicion).setFechaFundacion(empresaNueva.getFechaFundacion());
        
        
        try {
           saveToFile();
        } catch (IOException e) {
            
        }
    }

    @Override
    public Empresa eliminarEmpresa(int codigo) {
        var posicion = getPositionEmpresa(getEmpresaByCode(codigo));
        var emp = listaEmpresas.remove(posicion);
        
        
        try {
           saveToFile();
        } catch (IOException e) {
            
        }
        return emp;
    }

    @Override
    public int getPositionEmpresa(Empresa empresa) {
         for (int i = 0; i < listaEmpresas.size(); i++) {
            if (empresa.getCodigo() == listaEmpresas.get(i).getCodigo()) {
                return i;
            }
        }
        return -1;
    }
    
     private boolean codeExists(int code){
        
        for (var dep: listaEmpresas) {
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
            
            oos.writeObject(listaEmpresas);
            System.out.println("Datos de Empresas Guardados !");
            oos.close();
     
        } catch (IOException e) {
            oos.close();
            throw new IOException("Error al escribir el archivo :"+e.getMessage());
        }
    }
    
    private List<Empresa> readFromFile() throws ClassNotFoundException, IOException{
        
        List<Empresa> listEmpresa = new ArrayList<>();
        
        ObjectInputStream ois = null;
        FileInputStream fis = null;
        try {
           fis = new FileInputStream(new File(PATH));
           
           while(fis.available() > 0){
              
               ois = new ObjectInputStream(fis);
               listEmpresa = (List<Empresa>)ois.readObject();
              
           }
           
           ois.close();
           fis.close();
           
        } catch (IOException e) {
            ois.close();
            fis.close();
            throw new IOException("Error al leer el archivo :"+e.getMessage());
        }
        
        
        return listEmpresa;
        
        
    }
    
    
    
}
