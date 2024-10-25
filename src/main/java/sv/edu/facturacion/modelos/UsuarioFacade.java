/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.facturacion.modelos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.edu.facturacion.entidades.Usuario;

/**
 *
 * @author JAVA
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "APPFACTU_PU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    public Usuario buscarUsuario(String usuario, String clave) {
        Query q = em.createQuery("SELECT U FROM Usuario U "
                + "WHERE U.nombre=:usuario AND U.password=:clave AND U.idEstado.idEstado=1");
        q.setParameter("usuario", usuario);        
        q.setParameter("clave", clave);
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            //return (Usuario) q.getSingleResult();
            return (Usuario) q.getResultList().get(0);
        }        
    }
    
}
