/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.facturacion.modelos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sv.edu.facturacion.entidades.DetalleFactura;

/**
 *
 * @author JAVA
 */
@Stateless
public class DetalleFacturaFacade extends AbstractFacade<DetalleFactura> {
    @PersistenceContext(unitName = "APPFACTU_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DetalleFacturaFacade() {
        super(DetalleFactura.class);
    }
    
}
