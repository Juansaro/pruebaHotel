/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.ejb;

import com.hotel.model.Telefono;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author juan
 */
@Local
public interface TelefonoFacadeLocal {

    void create(Telefono telefono);

    void edit(Telefono telefono);

    void remove(Telefono telefono);

    Telefono find(Object id);

    List<Telefono> findAll();

    List<Telefono> findRange(int[] range);

    int count();

    public boolean registrarTelefono(Telefono telIn, int hotelIn);

    public boolean actualizarTelefono(Telefono telIn, int hotelIn);

    public boolean eliminarTelefono(int tel_id);

    public List<Telefono> leerTodos();
    
}
