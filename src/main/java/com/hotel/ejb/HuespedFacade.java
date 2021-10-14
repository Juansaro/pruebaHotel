/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.ejb;

import com.hotel.model.Huesped;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class HuespedFacade extends AbstractFacade<Huesped> implements HuespedFacadeLocal {

    @PersistenceContext(unitName = "pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HuespedFacade() {
        super(Huesped.class);
    }
    
    @Override
    public boolean registrarHuesped(Huesped huesIn, int fk_ciudad) {
        try {
            Query qr = em.createNativeQuery("INSERT INTO huesped (documento, nombre, apellido, correo, direccion, barrio, fk_ciudad) VALUES (?, ?, ?, ?, ?, ?, ?)");
            qr.setParameter(1, huesIn.getDocumento());
            qr.setParameter(2, huesIn.getNombre());
            qr.setParameter(3, huesIn.getApellido());
            qr.setParameter(4, huesIn.getCorreo());
            qr.setParameter(5, huesIn.getDireccion());
            qr.setParameter(6, huesIn.getBarrio());
            qr.setParameter(7, fk_ciudad);
            qr.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean actualizarHuesped(Huesped huesIn, int fk_ciudad) {
        try {
            Query qr = em.createNativeQuery("UPDATE hotel SET documento = ?, nombre = ?, apellido = ?, correo = ?, direccion = ?, barrio = ?, ciudad_id_ciudad = ? WHERE (id_huesped = ?)");
            qr.setParameter(1, huesIn.getDocumento());
            qr.setParameter(2, huesIn.getNombre());
            qr.setParameter(3, huesIn.getApellido());
            qr.setParameter(4, huesIn.getCorreo());
            qr.setParameter(5, huesIn.getDireccion());
            qr.setParameter(6, huesIn.getBarrio());
            qr.setParameter(7, fk_ciudad);
            qr.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    
    @Override
    public boolean eliminarHuesped(int hues_id) {
        try {
            Query c = em.createNativeQuery("DELETE FROM huesped WHERE (id_huesped = ?)");
            c.setParameter(1, hues_id);
            c.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
