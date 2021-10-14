/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.ejb;

import com.hotel.model.Telefono;
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
public class TelefonoFacade extends AbstractFacade<Telefono> implements TelefonoFacadeLocal {

    @PersistenceContext(unitName = "pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TelefonoFacade() {
        super(Telefono.class);
    }
    
    @Override
    public boolean registrarTelefono(Telefono telIn, int fk_hotel) {
        try {
            Query qr = em.createNativeQuery("INSERT INTO telefono (numero, fk_hotel) VALUES (?, ?)");
            qr.setParameter(1, telIn.getNumero());
            qr.setParameter(2, fk_hotel);
            qr.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean actualizarTelefono(Telefono telIn, int fk_hotel) {
        try {
            Query qr = em.createNativeQuery("UPDATE telefono SET numero = ?, fk_hotel = ? WHERE (id_telefono = ?)");
            qr.setParameter(1, telIn.getNumero());
            qr.setParameter(2, fk_hotel);
            qr.setParameter(3, telIn.getIdTelefono());
            qr.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    
    @Override
    public boolean eliminarTelefono(int tel_id) {
        try {
            Query c = em.createNativeQuery("DELETE FROM telefono WHERE (id_telefono = ?)");
            c.setParameter(1, tel_id);
            c.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public List<Telefono> leerTodos(){
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            Query q = em.createQuery("SELECT t FROM Telefono t");
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
}
