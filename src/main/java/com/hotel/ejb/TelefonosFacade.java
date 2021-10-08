/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.ejb;

import com.hotel.model.Telefonos;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class TelefonosFacade extends AbstractFacade<Telefonos> implements TelefonosFacadeLocal {

    @PersistenceContext(unitName = "pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TelefonosFacade() {
        super(Telefonos.class);
    }
    
}
