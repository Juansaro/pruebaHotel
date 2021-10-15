/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.ejb;

import com.hotel.model.Huesped;
import com.hotel.model.Reserva;
import com.hotel.model.Usuario;
import java.util.List;
import javax.ejb.Local;


@Local
public interface ReservaFacadeLocal {

    void create(Reserva reserva);

    void edit(Reserva reserva);

    void remove(Reserva reserva);

    Reserva find(Object id);

    List<Reserva> findAll();

    List<Reserva> findRange(int[] range);

    int count();

    public boolean actualizarReserva(Reserva resIn, int fk_huesped, int fk_habitacion, int fk_empleado, int fk_hotel, int fk_estado);

    public boolean eliminarReserva(int res_id); 

    public List<Reserva> leerReservasEmpleado(Usuario usuIn);

    public boolean registrarReserva(Reserva resIn, int fk_huesped, int fk_habitacion, int fk_empleado, int fk_hotel);

    public List<Reserva> leerReservasHuesped(Huesped hueIn);
    
}
