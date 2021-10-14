/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.ejb;

import com.hotel.model.Habitacion;
import com.hotel.model.Hotel;
import java.util.List;
import javax.ejb.Local;


@Local
public interface HabitacionFacadeLocal {

    void create(Habitacion habitacion);

    void edit(Habitacion habitacion);

    void remove(Habitacion habitacion);

    Habitacion find(Object id);

    List<Habitacion> findAll();

    List<Habitacion> findRange(int[] range);

    int count();
    
    public boolean eliminarHabitacion(int hab_id);

    public void crearHotelHabitacion(int fk_hotel, Habitacion ha);
   
    public boolean eliminarHotelHabitacion(Habitacion ha);

    public boolean actualizarHotelHabitacion(Habitacion habIn);

    public Habitacion validarSiExiste(int idIn);

    public boolean crearHabitacion(Habitacion h, int fk_tipo, int fk_hotel);

    public boolean actualizarHabitacion(Habitacion habIn, int fk_tipo, int fk_estado, int fk_hotel);

    public List<Habitacion> leerTodos();

    public Habitacion leerTipoHabitacion(int h);


}
