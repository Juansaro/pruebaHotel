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
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

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
    public boolean validarFechaEntrada(Date resIni) {

        StoredProcedureQuery q = em.createStoredProcedureQuery("RESERVA_ENTRADA")
                .registerStoredProcedureParameter(1, Date.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, boolean.class, ParameterMode.OUT)
                .setParameter(1, resIni);

        q.execute();
        //Out (2) y (3)
        boolean outFecEnt = (boolean) q.getOutputParameterValue(2);
        //Array de booleanos
        return outFecEnt;

    }
    
    @Override
    public boolean validarFechaSalida(Date resIni, Date resFin) {

        StoredProcedureQuery q = em.createStoredProcedureQuery("RESERVA_SALIDA")
                .registerStoredProcedureParameter(1, Date.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Date.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, boolean.class, ParameterMode.OUT)
                .setParameter(1, resIni)
                .setParameter(2, resFin);

        q.execute();
        //Out (2) y (3)
        boolean outFecEnt = (boolean) q.getOutputParameterValue(3);
        //Array de booleanos
        return outFecEnt;

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
    public boolean actualizarReserva(Reserva resIn,int fk_estado) {
        try {
            Query qr = em.createNativeQuery("UPDATE reserva SET fk_estado = ? WHERE (id_reserva = ?)");
            qr.setParameter(1, fk_estado);
            qr.setParameter(2, resIn.getIdReserva());
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
    
    @Override
    public List<Reserva> leerTodos(){
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            Query q = em.createQuery("SELECT r FROM Reserva r");
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
}
