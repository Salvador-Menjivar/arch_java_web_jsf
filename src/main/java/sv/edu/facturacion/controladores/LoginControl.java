/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.facturacion.controladores;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.codec.digest.DigestUtils;
import sv.edu.facturacion.entidades.Usuario;
import sv.edu.facturacion.modelos.UsuarioFacade;

/**
 *
 * @author JAVA
 */
@ManagedBean
@SessionScoped
public class LoginControl implements Serializable {

    @EJB
    UsuarioFacade usuarioFacade;

    private String usuario;
    private String clave;

    private Usuario usuarioValidado;

    @PostConstruct
    public void init() {

    }

    public String cerrarSesion() {
// FacesContext.getCurrentInstance().getExternalContext().
//               getSessionMap().remove("usuario");
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/faces/index.xhtml";

    }

    public String validarUsuario() {
        clave = DigestUtils.sha512Hex(clave);
        usuarioValidado = new Usuario();
        usuarioValidado = usuarioFacade.buscarUsuario(usuario, clave);
        if (usuarioValidado != null) {
            FacesContext.getCurrentInstance().getExternalContext().
                    getSessionMap().put("usuario", usuarioValidado);
            return "procesos/Facturacion/tblFacturas.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Combinación usuario y contraseña incorrectos", "PrimeFaces Rocks."));
            return null;
        }
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Usuario getUsuarioValidado() {
        return usuarioValidado;
    }

    public void setUsuarioValidado(Usuario usuarioValidado) {
        this.usuarioValidado = usuarioValidado;
    }

}
