/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.ejb;

import com.hotel.model.Hotel;
import com.hotel.model.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author juan
 */
@Stateless
public class HotelFacade extends AbstractFacade<Hotel> implements HotelFacadeLocal {

    @PersistenceContext(unitName = "pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HotelFacade() {
        super(Hotel.class);
    }
    
    @Override
    public boolean registrarHotel(Hotel hotIn, int fk_ciudad) {
        try {
            Query qr = em.createNativeQuery("INSERT INTO hotel (nombre, fk_ciudad) VALUES (?, ?)");
            qr.setParameter(1, hotIn.getNombre());
            qr.setParameter(2, fk_ciudad);
            qr.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean actualizarHotel(Hotel hotIn, int fk_hotel) {
        try {
            Query qr = em.createNativeQuery("UPDATE hotel SET nombre = ?, fk_hotel = ? WHERE (id_hotel = ?)");
            qr.setParameter(1, hotIn.getNombre());
            qr.setParameter(2, fk_hotel);
            qr.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    
    @Override
    public boolean eliminarHotel(int hot_id) {
        try {
            Query c = em.createNativeQuery("DELETE FROM hotel WHERE (id_hotel = ?)");
            c.setParameter(1, hot_id);
            c.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
