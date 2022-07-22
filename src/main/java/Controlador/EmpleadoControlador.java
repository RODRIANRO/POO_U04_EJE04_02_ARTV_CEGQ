/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Departamento;
import Modelo.Empleado;
import Servicio.DepartamentoServicio;
import Servicio.EmpleadoServicio;
import java.time.LocalDate;
import java.util.List;



/**
 *
 * @author Cesar
 */
public class EmpleadoControlador {
    private final EmpleadoServicio empleadoServicio = new EmpleadoServicio();
    public final DepartamentoServicio departamentoServicio = new DepartamentoServicio();
    
    public Empleado crearEmpleado(String cedula, String nombre, int anioNacimiento, int mesNacimiento, int diaNacimiento, 
            String nacionalidad, String direccion, String cargo, float salario, Departamento departamento){
        
          var fechaNacimiento = LocalDate.of(anioNacimiento, mesNacimiento, diaNacimiento);
        // fecha de entrada = fecha del sistema
        var fechaEntrada = LocalDate.now();
        
        if (cedula.equals("")) {
            throw new NullPointerException("Debe ingresar la cedula");
        }
        if (nombre.equals("")){
            throw new NullPointerException("Debe ingresar un nombre");
        }
        if (cargo.equals("")){
            throw new NullPointerException("Debe ingresar un cargo");
        }
        
        if (!isAdult(fechaNacimiento)) {
            throw new RuntimeException("Empleado debe ser mayor a 18 años");
        }
        var nuevoEmpleado =  new Empleado(cedula,nombre,fechaNacimiento,nacionalidad,direccion,cargo,salario,fechaEntrada,departamento);
        return empleadoServicio.crearEmpleado(nuevoEmpleado);  
        
        
        
                
    }
    
    public List<Empleado> listarEmpleados(){
        return empleadoServicio.listarEmpleados();
    }
    
    public Empleado getEmpleadoByCedula(String cedula){
        return empleadoServicio.getEmpleadoByCedula(cedula);
    }
    
     public void asignarDepartamento(String cedula, Departamento departamento){
        empleadoServicio.asignarDepartamento(cedula, departamento);
    }
     
    public void actualizarEmpleado(String cedula, Empleado empleadoNew){
        empleadoServicio.actualizarEmpleado(cedula, empleadoNew);
    } 
     
    public Empleado eliminarEmpleado(String cedula){
        return empleadoServicio.eliminarEmpleado(cedula);
    }
    public boolean isAdult(LocalDate date){
        var anioActual = LocalDate.now().getYear();
        var anioNacimiento = date.getYear();
        var age = anioActual-anioNacimiento;
        return age>=18;
    } 
    
    
    
    
   
      // Metodos validacion KeyEvent
    
    // valida que solo se ingresen numeros en el campo "cedula"
    public boolean validarSoloNumeros(char codigo){
        boolean flag = false;
        if(Character.isDigit(codigo)){
            flag = true;
        }
        return flag;
    }
    
    // valida que solo se ingrese texto en el campo "nombre"
    public boolean validarSoloTexto(char texto){
        boolean flag = false;
        if(Character.isLetter(texto) || Character.isSpaceChar(texto) || Character.isUpperCase(texto)){
            flag = true;
        }
        return flag;
                
    }
}
