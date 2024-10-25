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
import sv.edu.facturacion.entidades.Producto;
import sv.edu.facturacion.modelos.ProductoFacade;

/**
 *
 * @author salvador
 */
@Named(value = "productoControl")
@ViewScoped

public class ProductoControl implements Serializable {

    @Inject
    private ProductoFacade productoFacade;

    private Producto productoSelected;

    private String accion;

    private List<Producto> lstProducto;

    @PostConstruct
    public void init() {
        lstProducto = productoFacade.findAll();
    }

    public void doAccion() {
        if (accion.equals("Agregar")) {
            agregar();
        } else {
            modificar();
        }
    }

    public void limpiar() {
        productoSelected = new Producto();
    }

    public void agregar() {
        try {
            productoFacade.edit(productoSelected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Producto agregado correctamente"));
            limpiar();
            lstProducto = productoFacade.findAll();
        } catch (Exception e) {
            System.out.println("Error al agregar producto " + e);
        }
    }

    public void modificar() {
        try {
            productoFacade.edit(productoSelected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Producto modificado correctamente"));
            limpiar();
            lstProducto = productoFacade.findAll();
        } catch (Exception e) {
            System.out.println("Error al agregar producto");
        }
    }

    public List<Producto> getLstProducto() {
        return lstProducto;
    }

    public void setLstProducto(List<Producto> lstProducto) {
        this.lstProducto = lstProducto;
    }

    public Producto getProductoSelected() {
        return productoSelected;
    }

    public void setProductoSelected(Producto productoSelected) {
        this.productoSelected = productoSelected;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

}
