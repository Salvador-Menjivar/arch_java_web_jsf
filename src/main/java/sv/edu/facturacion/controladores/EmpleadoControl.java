/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.facturacion.controladores;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import sv.edu.facturacion.entidades.Empleado;
import sv.edu.facturacion.modelos.EmpleadoFacade;
import sv.edu.facturacion.modelos.EstadoFacade;

/**
 *
 * @author salvador
 */
@Named(value = "empleadoControl")
@ViewScoped
public class EmpleadoControl implements Serializable {

    @Inject
    private EmpleadoFacade empleadoFacade;
    @Inject 
    private EstadoFacade estadoFacade;

    private List<Empleado> lstEmpleado;

    private Empleado empleadoSelected;

    private String accion;

    @PostConstruct
    public void init() {
        lstEmpleado = empleadoFacade.findAll();
        empleadoSelected = new Empleado();
    }

    public void doAccion() {
        if (accion.equals("Agregar")) {
            agregarEmpleado();
        } else {
            modificarEmpleado();
        }
    }

    public void agregarEmpleado() {
        try {
            empleadoSelected.setIdEstado(estadoFacade.find(1));
            empleadoFacade.edit(empleadoSelected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Empleado agregado correctamente"));
            lstEmpleado = empleadoFacade.findAll();
            limpiar();
        } catch (Exception e) {
            System.out.println("Error al agregar empleado " + e);
        }
    }

    public void modificarEmpleado() {
        try {
            empleadoFacade.edit(empleadoSelected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Empleado modificado correctamente"));
            lstEmpleado = empleadoFacade.findAll();
            limpiar();
        } catch (Exception e) {
            System.out.println("Error al modificar empleador " + e);
        }
    }

    public void limpiar() {
        empleadoSelected = new Empleado();
    }

    public List<Empleado> getLstEmpleado() {
        return lstEmpleado;
    }

    public void setLstEmpleado(List<Empleado> lstEmpleado) {
        this.lstEmpleado = lstEmpleado;
    }

    public Empleado getEmpleadoSelected() {
        return empleadoSelected;
    }

    public void setEmpleadoSelected(Empleado empleadoSelected) {
        this.empleadoSelected = empleadoSelected;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }
    
    

}
