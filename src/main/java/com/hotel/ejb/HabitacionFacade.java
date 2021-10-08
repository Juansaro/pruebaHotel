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
    public boolean crearHabitacion(String bod_nombre, String bod_direccion, String bod_telefono) {
        try {
            Query c = em.createNativeQuery("INSERT INTO tbl_bodega (bdg_nombre,bdg_direccion,bdg_telefono) VALUES (?,?,?)");
            c.setParameter(1, bod_nombre);
            c.setParameter(2, bod_direccion );
            c.setParameter(3, bod_telefono );
            c.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }    
    } 
    
    
    @Override
    public boolean eliminarBodega(int bod_id){
        try {
            Query c = em.createNativeQuery("DELETE FROM tbl_bodega WHERE (bdg_bodegaid = ?)");
            c.setParameter(1, bod_id);
            c.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    
    }
    
    
    @Override
    public Bodega validarSiExiste(String nombreIn){
        try {
            Query q = em.createQuery("SELECT b FROM Bodega b WHERE b.bdgNombre LIKE CONCAT('%',:nombreIn,'%')");
            q.setParameter("nombreIn", nombreIn);
            return (Bodega) q.getSingleResult();
        } catch (Exception e) {
            return  null;
        }
    }
    
}
