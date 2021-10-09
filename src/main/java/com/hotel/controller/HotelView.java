/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.controller;

import com.hotel.ejb.CiudadFacadeLocal;
import com.hotel.ejb.HotelFacadeLocal;
import com.hotel.model.Ciudad;
import com.hotel.model.Hotel;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "hotelView")
@ViewScoped
public class HotelView implements Serializable{
    
    @EJB
    private HotelFacadeLocal hotelFacadeLocal;
    
    @EJB
    private CiudadFacadeLocal ciudadFacadeLocal;
    
    @Inject
    private Ciudad ciudad;
    
    private List<Hotel> hoteles;
    private List<Ciudad> ciudades;
    
    private Hotel hotelReg = new Hotel();
    private Hotel hotelTemporal = new Hotel();
    
    @PostConstruct
    private void init(){
        hoteles = hotelFacadeLocal.findAll();
        ciudades = ciudadFacadeLocal.findAll();
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public List<Hotel> getHoteles() {
        return hoteles;
    }

    public void setHoteles(List<Hotel> hoteles) {
        this.hoteles = hoteles;
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }

    public Hotel getHotelReg() {
        return hotelReg;
    }

    public void setHotelReg(Hotel hotelReg) {
        this.hotelReg = hotelReg;
    }

    public Hotel getHotelTemporal() {
        return hotelTemporal;
    }

    public void setHotelTemporal(Hotel hotelTemporal) {
        this.hotelTemporal = hotelTemporal;
    }
    
    
    
}
