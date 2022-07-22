/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Servicio;

import Modelo.Empleado;
import java.util.List;



/**
 *
 * @author Cesar
 */
public interface IEmpleadoServicio {
    public Empleado crearEmpleado(Empleado empleado);
    public List<Empleado> listarEmpleados();
    public Empleado getEmpleadoByCedula(String cedula);
    public void actualizarEmpleado(String cedula, Empleado empleadoNuevo);
    public Empleado eliminarEmpleado(String cedula);
    public int getPositionEmpleado(Empleado empleado);
    
}
