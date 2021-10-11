/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.ejb;

import com.hotel.model.TipoHabitacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author juan
 */
@Local
public interface TipoHabitacionFacadeLocal {

    void create(TipoHabitacion tipoHabitacion);

    void edit(TipoHabitacion tipoHabitacion);

    void remove(TipoHabitacion tipoHabitacion);

    TipoHabitacion find(Object id);

    List<TipoHabitacion> findAll();

    List<TipoHabitacion> findRange(int[] range);

    int count();
    
}
