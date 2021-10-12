/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.ejb;

import com.hotel.model.EstadoHabitacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author juan
 */
@Local
public interface EstadoHabitacionFacadeLocal {

    void create(EstadoHabitacion estadoHabitacion);

    void edit(EstadoHabitacion estadoHabitacion);

    void remove(EstadoHabitacion estadoHabitacion);

    EstadoHabitacion find(Object id);

    List<EstadoHabitacion> findAll();

    List<EstadoHabitacion> findRange(int[] range);

    int count();
    
}
