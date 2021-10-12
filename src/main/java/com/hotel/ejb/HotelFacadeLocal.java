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

/**
 *
 * @author juan
 */
@Local
public interface HotelFacadeLocal {

    void create(Hotel hotel);

    void edit(Hotel hotel);

    void remove(Hotel hotel);

    Hotel find(Object id);

    List<Hotel> findAll();

    List<Hotel> findRange(int[] range);

    int count();

    public boolean registrarHotel(Hotel hotIn, int fk_ciudad);

    public boolean actualizarHotel(Hotel hotIn, int fk_hotel);

    public boolean eliminarHotel(int hot_id);

    public Hotel leerHotel(int idHot);

}
