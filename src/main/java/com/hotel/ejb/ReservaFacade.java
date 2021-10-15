/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.ejb;

import com.hotel.model.Huesped;
import com.hotel.model.Reserva;
import com.hotel.model.Usuario;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ReservaFacade extends AbstractFacade<Reserva> implements ReservaFacadeLocal {

    @PersistenceContext(unitName = "pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReservaFacade() {
        super(Reserva.class);
    }
    
    @Override
    public boolean registrarReserva(Reserva resIn, int fk_huesped, int fk_habitacion, int fk_empleado, int fk_hotel) {
        try {
            Query qr = em.createNativeQuery("INSERT INTO reserva (fecha_ingreso, fecha_salida, fk_huesped, fk_habitacion, fk_empleado, fk_hotel, fk_estado, precio, fechaRegistro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            qr.setParameter(1, resIn.getFechaIngreso());
            qr.setParameter(2, resIn.getFechaSalida());
            qr.setParameter(3, fk_huesped);
            qr.setParameter(4, fk_habitacion);
            qr.setParameter(5, fk_empleado);
            qr.setParameter(6, fk_hotel);
            qr.setParameter(7, 1);
            qr.setParameter(8, resIn.getPrecio());
            qr.setParameter(9, resIn.getFechaRegistro());
            qr.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean actualizarReserva(Reserva resIn, int fk_huesped, int fk_habitacion, int fk_empleado, int fk_hotel, int fk_estado) {
        try {
            Query qr = em.createNativeQuery("UPDATE reserva SET fecha_ingreso = ?, fecha_salida = ? ,fk_huesped = ?, fk_habitacion = ?, fk_empleado = ?, fk_hotel = ?, estado_reserva_id_estado_reserva WHERE (id_reserva = ?)");
            qr.setParameter(1, resIn.getFechaIngreso());
            qr.setParameter(2, resIn.getFechaSalida());
            qr.setParameter(3, fk_huesped);
            qr.setParameter(4, fk_habitacion);
            qr.setParameter(5, fk_empleado);
            qr.setParameter(6, fk_hotel);
            qr.setParameter(7, fk_estado);
            qr.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public boolean eliminarReserva(int res_id) {
        try {
            Query c = em.createNativeQuery("DELETE FROM reserva WHERE (id_reserva = ?)");
            c.setParameter(1, res_id);
            c.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public List<Reserva> leerReservasEmpleado(Usuario usuIn) {
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            Query qt = em.createQuery("SELECT r FROM Reserva r WHERE r.fkEmpleado = :usuIn");
            qt.setParameter("usuIn", usuIn);
            return qt.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<Reserva> leerReservasHuesped(Huesped hueIn) {
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            Query qt = em.createQuery("SELECT r FROM Reserva r WHERE r.fkHuesped = :hueIn");
            qt.setParameter("hueIn", hueIn);
            return qt.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
}
