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
public class EmpleadoServicio implements IEmpleadoServicio{
private final String PATH = DataManager.getDataPath()+"EmpleadosData.dat";
    
    private static List<Empleado> listaEmpleados = new ArrayList<>();

   

    @Override
    public Empleado crearEmpleado(Empleado empleado) {
        if(codeExists(empleado.getCedula())){
            throw new RuntimeException("Cedula de empleado existente");
        
        }
        listaEmpleados.add(empleado);
        
        try {
            saveToFile();
        } catch (IOException ex) {
            Logger.getLogger(EmpleadoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return empleado;
    }

    @Override
    public List<Empleado> listarEmpleados() {
        
        try {
            listaEmpleados = readFromFile();
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(EmpleadoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(listaEmpleados.isEmpty()){
            throw new NullPointerException("Lista de empleados vacia");
        }
        
        return listaEmpleados;
    }

    @Override
    public Empleado getEmpleadoByCedula(String cedula) {
        for (Empleado emp : listaEmpleados) {
            if(emp.getCedula().equals(cedula)){
                return emp;
            }
        }
        return null;
    }
    
    public void asignarDepartamento(String cedula, Departamento departamento){
        getEmpleadoByCedula(cedula).asignarDepartamento(departamento);
    }

    @Override
    public void actualizarEmpleado(String cedula, Empleado empleadoNuevo) {
        int posicion = getPositionEmpleado(getEmpleadoByCedula(cedula));
        
       
        if (empleadoNuevo.getNombre().equals("")){
            throw new NullPointerException("Debe ingresar un nombre");
        }
        if (empleadoNuevo.getCargo().equals("")){
            throw new NullPointerException("Debe ingresar un cargo");
        }
        listaEmpleados.get(posicion).setNombre(empleadoNuevo.getNombre());
        listaEmpleados.get(posicion).setNacionalidad(empleadoNuevo.getNacionalidad());
        listaEmpleados.get(posicion).setCargo(empleadoNuevo.getCargo());
        listaEmpleados.get(posicion).setSalario(empleadoNuevo.getSalario());
        listaEmpleados.get(posicion).setFechaNacimiento(empleadoNuevo.getFechaNacimiento());
        listaEmpleados.get(posicion).setDireccion(empleadoNuevo.getDireccion());
        listaEmpleados.get(posicion).asignarDepartamento(empleadoNuevo.getDepartamento());
        
        try {
            saveToFile();
        } catch (IOException ex) {
            Logger.getLogger(EmpleadoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Empleado eliminarEmpleado(String cedula) {
       var posicion = getPositionEmpleado(getEmpleadoByCedula(cedula));
       var empl = listaEmpleados.remove(posicion);
      try {
            saveToFile();
        } catch (IOException ex) {
            Logger.getLogger(EmpleadoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
       return empl;
    }

    @Override
    public int getPositionEmpleado(Empleado empleado) {
        for (int i = 0; i < listaEmpleados.size(); i++) {
            if (empleado.getCedula().equals(listaEmpleados.get(i).getCedula())) {
                return i;
            }
        }
        return -1;
    }
     private boolean codeExists(String cedula){
        
        for (var emp: listaEmpleados) {
            if (emp.getCedula().equals(cedula)) {
                return true;
            }
        }
        return false;
    }
     
    private void saveToFile() throws IOException{
        
        ObjectOutputStream oos = null;
       
        try {
           
            oos = new ObjectOutputStream(new FileOutputStream(new File(PATH)));
            
            oos.writeObject(listaEmpleados);
            System.out.println("Datos de Empresas Guardados !");
            oos.close();
     
        } catch (IOException e) {
            oos.close();
            throw new IOException("Error al escribir el archivo :"+e.getMessage());
        }
    }
    
    private List<Empleado> readFromFile() throws ClassNotFoundException, IOException{
        
        List<Empleado> listEmpleados = new ArrayList<>();
        
        ObjectInputStream ois = null;
        FileInputStream fis = null;
        try {
           fis = new FileInputStream(new File(PATH));
           
           while(fis.available() > 0){
              
               ois = new ObjectInputStream(fis);
               listEmpleados = (List<Empleado>)ois.readObject();
              
           }
           
           ois.close();
           fis.close();
           
        } catch (IOException e) {
            ois.close();
            fis.close();
            throw new IOException("Error al leer el archivo :"+e.getMessage());
        }
        
        
        return listEmpleados;
        
        
    }
    
}
