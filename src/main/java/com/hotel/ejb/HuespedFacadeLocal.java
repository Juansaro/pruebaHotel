/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.ejb;

import com.hotel.model.Huesped;
import java.util.List;
import javax.ejb.Local;

@Local
public interface HuespedFacadeLocal {

    void create(Huesped huesped);

    void edit(Huesped huesped);

    void remove(Huesped huesped);

    Huesped find(Object id);

    List<Huesped> findAll();

    List<Huesped> findRange(int[] range);

    int count();
    
}
