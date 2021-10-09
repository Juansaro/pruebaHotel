/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.ejb;

import com.hotel.model.EstadoReserva;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author juan
 */
@Local
public interface EstadoReservaFacadeLocal {

    void create(EstadoReserva estadoReserva);

    void edit(EstadoReserva estadoReserva);

    void remove(EstadoReserva estadoReserva);

    EstadoReserva find(Object id);

    List<EstadoReserva> findAll();

    List<EstadoReserva> findRange(int[] range);

    int count();
    
}
