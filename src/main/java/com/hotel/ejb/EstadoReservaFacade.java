/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.ejb;

import com.hotel.model.EstadoReserva;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author juan
 */
@Stateless
public class EstadoReservaFacade extends AbstractFacade<EstadoReserva> implements EstadoReservaFacadeLocal {

    @PersistenceContext(unitName = "pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstadoReservaFacade() {
        super(EstadoReserva.class);
    }
    
    @Override
    public List<EstadoReserva> leerTodos(){
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            Query q = em.createQuery("SELECT e FROM EstadoReserva e");
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
}
