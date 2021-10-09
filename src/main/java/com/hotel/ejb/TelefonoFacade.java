/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.ejb;

import com.hotel.model.Telefono;

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
    public boolean registrarTelefono(Telefono telIn, int hotelIn) {
        try {
            Query qr = em.createNativeQuery("INSERT INTO telefono (numero, hotel_id_hotel) VALUES (?, ?)");
            qr.setParameter(1, telIn.getNumero());
            qr.setParameter(2, hotelIn);
            qr.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean actualizarTelefono(Telefono telIn, int hotelIn) {
        try {
            Query qr = em.createNativeQuery("UPDATE usuario SET nombre = ?, apellido = ?, correo = ?, contrasena = ?, fk_rol = ? WHERE (id_usuario = ?)");
            qr.setParameter(1, telIn.getNumero());
            qr.setParameter(2, hotelIn);
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
    
}
