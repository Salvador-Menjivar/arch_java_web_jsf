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
import sv.edu.facturacion.entidades.FormaPago;
import sv.edu.facturacion.modelos.FormaPagoFacade;

/**
 *
 * @author salvador
 */
@Named(value = "formaPagoControl")
@ViewScoped
public class FormaPagoControl implements Serializable {

    @Inject
    private FormaPagoFacade formaPagoFacade;

    private List<FormaPago> lstFormaPago;

    private FormaPago formaPagoSelected;

    private String accion;

    @PostConstruct
    public void init() {
        lstFormaPago = formaPagoFacade.findAll();
        formaPagoSelected = new FormaPago();
    }

    public void doAccion() {
        if (accion.equals("Agregar")) {
            agregar();
        } else {
            modificar();
        }
    }

    public void agregar() {
        try {
            formaPagoFacade.edit(formaPagoSelected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Forma de pago agregada correctamente"));
            lstFormaPago = formaPagoFacade.findAll();
            limpiar();
        } catch (Exception e) {
            System.out.println("Error al agregar forma de pago:" + e);
        }
    }

    public void modificar() {
        try {
            formaPagoFacade.edit(formaPagoSelected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Forma de pago modificada correctamente"));
            lstFormaPago = formaPagoFacade.findAll();
            limpiar();
        } catch (Exception e) {
            System.out.println("Error al modificar forma de pago " + e);
        }

    }

    public void limpiar() {
        formaPagoSelected = new FormaPago();
    }

    public List<FormaPago> getLstFormaPago() {
        return lstFormaPago;
    }

    public void setLstFormaPago(List<FormaPago> lstFormaPago) {
        this.lstFormaPago = lstFormaPago;
    }

    public FormaPago getFormaPagoSelected() {
        return formaPagoSelected;
    }

    public void setFormaPagoSelected(FormaPago formaPagoSelected) {
        this.formaPagoSelected = formaPagoSelected;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

}
