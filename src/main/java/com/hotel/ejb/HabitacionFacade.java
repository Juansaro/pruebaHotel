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
            Query c = em.createNativeQuery("INSERT INTO habitacion ( bano, calefaccion, telefono, fk_tipo, precio, estado_habitacion_id_estado, hotel_id_hotel) VALUES (?,?,?,?,?,?,?)");
            c.setParameter(1, Short.parseShort("1"));
            c.setParameter(2, Short.parseShort("1"));
            c.setParameter(3, Short.parseShort("1"));
            c.setParameter(4, fk_tipo);
            c.setParameter(5, h.getPrecio());
            c.setParameter(6, 2);
            c.setParameter(7, fk_hotel);
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
    public boolean actualizarHabitacion(Habitacion habIn, int fk_tipo, int fk_estado,int fk_hotel) {
        try {
            Query qr = em.createNativeQuery("UPDATE habitacion SET fk_tipo = ?, precio = ?, estado_habitacion_id_estado = ?, hotel_id_hotel = ? WHERE (id_habitacion = ?)");
            qr.setParameter(1, fk_tipo);
            qr.setParameter(2, habIn.getPrecio());
            qr.setParameter(3, fk_estado);
            qr.setParameter(4, fk_hotel);
            qr.setParameter(5, habIn.getIdHabitacion());
            qr.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    
    @Override
    public boolean actualizarHabitacionReserva(int habIn) {
        try {
            Query qr = em.createNativeQuery("UPDATE habitacion SET estado_habitacion_id_estado = ? WHERE (id_habitacion = ?)");
            qr.setParameter(1, 2);
            qr.setParameter(2, habIn);
            qr.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
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
    public List<Habitacion> leerTodos(){
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            Query q = em.createQuery("SELECT ha FROM Habitacion ha");
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<Habitacion> leerDisponibles(){
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            Query q = em.createQuery("SELECT ha FROM Habitacion ha WHERE ha.estadoHabitacionIdEstado.idEstado = 2");
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public Habitacion leerTipoHabitacion(int h) {
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            Query qt = em.createQuery("SELECT h FROM Habitacion h WHERE h.idHabitacion = :h");
            qt.setParameter("h", h);
            return (Habitacion) qt.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public Float leerCostoHabitacion(int h) {
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            Query qt = em.createQuery("SELECT h.precio FROM Habitacion h WHERE h.idHabitacion = :h");
            qt.setParameter("h", h);
            return (Float) qt.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<Habitacion> leerHabitacionesHotel(Hotel h){
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            Query q = em.createQuery("SELECT ha FROM Habitacion ha WHERE ha.estadoHabitacionIdEstado.idEstado = 2 AND ha.hotelIdHotel = :h");
            q.setParameter("h", h);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
}
