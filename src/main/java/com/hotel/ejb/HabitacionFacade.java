/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.ejb;

import com.hotel.model.Habitacion;
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
    public boolean crearHabitacion(Habitacion h, int fk_tipo_habitacion) {
        try {
            Query c = em.createNativeQuery("INSERT INTO habitacion (bano, calefaccion, telefono, estado, tipo_habitacion_id_tipo_habitacion) VALUES (?,?,?,?,?)");
            c.setParameter(1, h.getBano());
            c.setParameter(2, h.getCalefaccion());
            c.setParameter(3, h.getTelefono());
            c.setParameter(4, h.getEstado());
            c.setParameter(5, fk_tipo_habitacion);
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
    public Habitacion validarSiExiste(String nombreIn){
        try {
            Query q = em.createQuery("SELECT h FROM habitacion h WHERE h LIKE CONCAT('%',:nombreIn,'%')");
            q.setParameter("nombreIn", nombreIn);
            return (Habitacion) q.getSingleResult();
        } catch (Exception e) {
            return  null;
        }
    }
    
}
