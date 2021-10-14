/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.ejb;

import com.hotel.model.Habitacion;
import com.hotel.model.Hotel;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class HabitacionFacade extends AbstractFacade<Habitacion> implements HabitacionFacadeLocal {

    @PersistenceContext(unitName = "pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HabitacionFacade() {
        super(Habitacion.class);
    }
    
    @Override
    public boolean crearHabitacion(Habitacion h, int fk_tipo, int fk_hotel) {
        try {
            Query c = em.createNativeQuery("INSERT INTO habitacion ( bano, calefaccion, telefono, fk_tipo, precio, fk_estado, fk_hotel) VALUES (?,?,?,?,?,?,?)");
            c.setParameter(1, Short.parseShort("1"));
            c.setParameter(2, Short.parseShort("1"));
            c.setParameter(3, Short.parseShort("1"));
            c.setParameter(4, fk_tipo);
            c.setParameter(5, h.getPrecio());
            c.setParameter(6, Short.parseShort("1"));
            c.setParameter(6, fk_hotel);
            c.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }    
    } 
    
    
    @Override
    public boolean eliminarHabitacion(int hab_id){
        try {
            Query c = em.createNativeQuery("DELETE FROM habitacion WHERE (id_habitacion = ?)");
            c.setParameter(1, hab_id);
            c.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    
    }
    
    
    @Override
    public Habitacion validarSiExiste(int idIn){
        try {
            Query q = em.createQuery("SELECT h FROM Habitacion h WHERE h.idHabitacion = :nombreIn");
            q.setParameter("nombreIn", idIn);
            return (Habitacion) q.getSingleResult();
        } catch (Exception e) {
            return  null;
        }
    }
    
    @Override
    public boolean actualizarHabitacion(Habitacion habIn, int fk_tipo) {
        try {
            Query qr = em.createNativeQuery("UPDATE usuario SET nombre = ?, apellido = ?, correo = ?, contrasena = ?, fk_rol = ? WHERE (id_usuario = ?)");
            qr.setParameter(1, habIn.getBano());
            qr.setParameter(2, habIn.getCalefaccion());
            qr.setParameter(3, habIn.getTelefono());
            qr.setParameter(4, fk_tipo);
            qr.setParameter(5, habIn.getFkTipo());
            qr.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    
    @Override
    public boolean actualizarHotelHabitacion(Habitacion habIn) {
        try {
            Query qr = em.createNativeQuery("UPDATE hotel_has_habitacion SET fk_habitacion = ? WHERE (fk_habitacion = ?)");
            qr.setParameter(1, habIn.getIdHabitacion());
            qr.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    
    @Override
    public void crearHotelHabitacion(int fk_hotel, Habitacion ha) {
        try {
            Query c = em.createNativeQuery("INSERT INTO hotel_has_habitacion (fk_hotel, fk_habitacion) VALUES (?,?)");
            c.setParameter(1, fk_hotel);
            c.setParameter(2, ha.getIdHabitacion());
            c.executeUpdate();
        } catch (Exception e) {
        
        }    
    }
    
    @Override
    public boolean eliminarHotelHabitacion(Habitacion ha) {
        try {
            Query c = em.createNativeQuery("DELETE FROM hotel_has_habitacion  WHERE fk_habitacion = ?");
            c.setParameter(1, ha.getIdHabitacion());
            c.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
     @Override
    public List<Habitacion> leerTodos(Hotel hotIn){
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            Query q = em.createQuery("SELECT ha, h FROM Habitacion ha JOIN ha.hotelCollection  h WHERE h.idHotel = :hotIn");
            q.setParameter("hotIn", hotIn.getIdHotel());
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    
}
