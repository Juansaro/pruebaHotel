/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.ejb;

import com.hotel.model.Telefonos;
import java.util.List;
import javax.ejb.Local;


@Local
public interface TelefonosFacadeLocal {

    void create(Telefonos telefonos);

    void edit(Telefonos telefonos);

    void remove(Telefonos telefonos);

    Telefonos find(Object id);

    List<Telefonos> findAll();

    List<Telefonos> findRange(int[] range);

    int count();
    
}
