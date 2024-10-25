/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.facturacion.controladores;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;
import sv.edu.facturacion.entidades.Cliente;
import sv.edu.facturacion.modelos.ClienteFacade;

/**
 *
 * @author salvador
 */
@ManagedBean(name = "clienteControl")
@ViewScoped
public class ClienteControl implements Serializable {

    @EJB
    private ClienteFacade clienteFacade;

    private List<Cliente> lstClientes;

    private Cliente clienteSelected;

    private String accion;

    @PostConstruct
    public void init() {
        lstClientes = clienteFacade.findAll();
        clienteSelected = new Cliente();
        accion = "Agregar";
    }

    public void doAccion() {
        if (accion.equals("Agregar")) {
            agregarCliente();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Cliente agregado correctamente"));
        } else {
            modificarCliente();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Cliente modificado correctamente"));
        }
    }

    public void agregarCliente() {
        try {
            clienteFacade.edit(clienteSelected);
            lstClientes=clienteFacade.findAll();
            limpiar();
        } catch (Exception e) {
            System.out.println("Error al agregar al cliente: " + e);
        }
    }

    public void modificarCliente() {
        try {
            clienteFacade.edit(clienteSelected);
            lstClientes=clienteFacade.findAll();
            limpiar();
        } catch (Exception e) {
            System.out.println("Error al modificar cliente: " + e);
        }
    }

    public void limpiar() {
            clienteSelected = new Cliente();
    }

    public List<Cliente> getLstClientes() {
        return lstClientes;
    }

    public void setLstClientes(List<Cliente> lstClientes) {
        this.lstClientes = lstClientes;
    }

    public Cliente getClienteSelected() {
        return clienteSelected;
    }

    public void setClienteSelected(Cliente clienteSelected) {
        this.clienteSelected = clienteSelected;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    
    
}
