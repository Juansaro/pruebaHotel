/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.ejb;

import com.hotel.model.Habitacion;
import com.hotel.model.Hotel;
import com.hotel.model.Usuario;
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
            Query qr = em.createNativeQuery("INSERT INTO hotel (nombre, fk_ciudad, direccion) VALUES (?, ?, ?)");
            qr.setParameter(1, hotIn.getNombre());
            qr.setParameter(2, fk_ciudad);
            qr.setParameter(3, hotIn.getDireccion());
            qr.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean actualizarHotel(Hotel hotIn, int fk_ciudad) {
        try {
            Query qr = em.createNativeQuery("UPDATE hotel SET nombre = ?, fk_ciudad = ?, direccion = ? WHERE (id_hotel = ?)");
            qr.setParameter(1, hotIn.getNombre());
            qr.setParameter(2, fk_ciudad);
            qr.setParameter(3, hotIn.getDireccion());
            qr.setParameter(4, hotIn.getIdHotel());
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
    
    @Override
    public Hotel leerHotel(int idHot) {
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            Query qt = em.createQuery("SELECT h FROM Hotel h WHERE h.idHotel = :hotel");
            qt.setParameter("hotel", idHot);
            return (Hotel) qt.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<Hotel> leerTodos(){
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            Query q = em.createQuery("SELECT h FROM Hotel h");
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
   
     @Override
    public boolean ingresarFoto(int hoIn) {
        try {
            Query c = em.createNativeQuery("INSERT INTO hotel (hotelFoto) VALUES (?)");
            c.setParameter(1, hoIn);
            c.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
