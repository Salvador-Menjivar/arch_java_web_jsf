/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.facturacion.controladores;


import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sv.edu.facturacion.entidades.Cliente;
import sv.edu.facturacion.entidades.DetalleFactura;
import sv.edu.facturacion.entidades.Empleado;
import sv.edu.facturacion.entidades.Factura;
import sv.edu.facturacion.entidades.FormaPago;
import sv.edu.facturacion.entidades.Producto;
import sv.edu.facturacion.modelos.ClienteFacade;
import sv.edu.facturacion.modelos.DetalleFacturaFacade;
import sv.edu.facturacion.modelos.EmpleadoFacade;
import sv.edu.facturacion.modelos.FacturaFacade;
import sv.edu.facturacion.modelos.FormaPagoFacade;
import sv.edu.facturacion.modelos.ProductoFacade;

/**
 *
 * @author JAVA
 */
@ManagedBean
@SessionScoped
public class FacturaControl implements Serializable {

    @EJB
    ClienteFacade clienteFacade;
    @EJB
    EmpleadoFacade empleadoFacade;
    @EJB
    FormaPagoFacade formaPagoFacade;
    @EJB
    ProductoFacade productoFacade;
    @EJB
    FacturaFacade facturaFacade;
    @EJB
    DetalleFacturaFacade detalleFacturaFacade;

    private List<Cliente> lstClientes;
    private List<Empleado> lstEmpleados;
    private List<FormaPago> lstFormaPago;
    private List<Producto> lstProductos;
    private List<Factura> lstFacturas;

    private Factura facturaSelected;
    private DetalleFactura detalleFacturaSelected;

    @PostConstruct
    public void init() {
        facturaSelected = new Factura();
        facturaSelected.setIdCliente(new Cliente());
        facturaSelected.setIdEmpleado(new Empleado());
        facturaSelected.setIdFormaPago(new FormaPago());
        facturaSelected.setFecha(new Date());
        detalleFacturaSelected = new DetalleFactura();
        detalleFacturaSelected.setIdProducto(new Producto());
        facturaSelected.setDetalleFacturaList(new ArrayList<>());
        listarClientes();
        listarEmpleados();
        listarFormaPago();
        listarProductos();
        listarFacturas();
    }

    public BigDecimal getSumarTotales() {
        BigDecimal totalFactura = BigDecimal.ZERO;
        for (DetalleFactura detalle : facturaSelected.getDetalleFacturaList()) {
            totalFactura=totalFactura.add(detalle.getTotal());
        }
        totalFactura=totalFactura.setScale(2,RoundingMode.HALF_UP);
        return totalFactura;
    }

    public String limpiar() {
        init();
        return "frmFactura.xhtml?faces-redirect=true";
    }

    public List<Factura> listarFacturas() {
        lstFacturas = facturaFacade.findAll();
        return lstFacturas;
    }

    public void borrarDetalle() {
        try {
            facturaSelected.getDetalleFacturaList().remove(detalleFacturaSelected);
            facturaSelected = facturaFacade.edit(facturaSelected);
            //detalleFacturaFacade.remove(detalleFacturaSelected);
        } catch (Exception e) {
            System.out.println("Error al borrar detalle " + e);
        }
    }

    public void guardarDetalle() {
        try {
            detalleFacturaSelected.setPrecio(
                    detalleFacturaSelected.getIdProducto().getPrecio());
            detalleFacturaSelected.setIdFactura(facturaSelected);
            facturaSelected.getDetalleFacturaList().add(detalleFacturaSelected);
            facturaSelected = facturaFacade.edit(facturaSelected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "REGISTRO GUARDADO", "PrimeFaces Rocks."));
            detalleFacturaSelected = new DetalleFactura();
            listarFacturas();
        } catch (Exception e) {
            System.out.println("Error al guardar la factura " + e);
        }
    }

    public void calcularMontos() {
        try {
            if (detalleFacturaSelected.getIdProducto().getPrecio() != null
                    && detalleFacturaSelected.getCantidad() != null) {
                BigDecimal iva = detalleFacturaSelected.getIdProducto().getPrecio().multiply(
                        new BigDecimal(0.10));
                detalleFacturaSelected.setIva(iva);
                detalleFacturaSelected.setTotal(
                        detalleFacturaSelected.getIdProducto().getPrecio().multiply(
                                new BigDecimal(detalleFacturaSelected.getCantidad()))
                        .subtract(iva));
                detalleFacturaSelected.setIva(detalleFacturaSelected.getIva().setScale(2, RoundingMode.HALF_UP));
                detalleFacturaSelected.setTotal(detalleFacturaSelected.getTotal().setScale(2, RoundingMode.HALF_UP));
            }
        } catch (Exception e) {
            System.out.println("Error al calcular iva y total " + e);
        }
    }

    public List<Producto> listarProductos() {
        lstProductos = productoFacade.findAll();
        return lstProductos;
    }

    public List<Empleado> listarEmpleados() {
        lstEmpleados = empleadoFacade.findAll();
        return lstEmpleados;
    }

    public List<FormaPago> listarFormaPago() {
        lstFormaPago = formaPagoFacade.findAll();
        return lstFormaPago;
    }

    public List<Cliente> listarClientes() {
        lstClientes = clienteFacade.findAll();
        return lstClientes;

    }

    public List<Cliente> getLstClientes() {
        return lstClientes;
    }

    public void setLstClientes(List<Cliente> lstClientes) {
        this.lstClientes = lstClientes;
    }

    public Factura getFacturaSelected() {
        return facturaSelected;
    }

    public void setFacturaSelected(Factura facturaSelected) {
        this.facturaSelected = facturaSelected;
    }

    public List<Empleado> getLstEmpleados() {
        return lstEmpleados;
    }

    public void setLstEmpleados(List<Empleado> lstEmpleados) {
        this.lstEmpleados = lstEmpleados;
    }

    public List<FormaPago> getLstFormaPago() {
        return lstFormaPago;
    }

    public void setLstFormaPago(List<FormaPago> lstFormaPago) {
        this.lstFormaPago = lstFormaPago;
    }

    public DetalleFactura getDetalleFacturaSelected() {
        return detalleFacturaSelected;
    }

    public void setDetalleFacturaSelected(DetalleFactura detalleFacturaSelected) {
        this.detalleFacturaSelected = detalleFacturaSelected;
    }

    public List<Producto> getLstProductos() {
        return lstProductos;
    }

    public void setLstProductos(List<Producto> lstProductos) {
        this.lstProductos = lstProductos;
    }

    public List<Factura> getLstFacturas() {
        return lstFacturas;
    }

    public void setLstFacturas(List<Factura> lstFacturas) {
        this.lstFacturas = lstFacturas;
    }

}
