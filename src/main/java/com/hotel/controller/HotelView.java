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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    
    private int fk_ciudad;
    
    private List<Hotel> hoteles;
    private List<Ciudad> ciudades;
    
    private Hotel hotelReg = new Hotel();
    private Hotel hotelTemporal = new Hotel();
    
    @PostConstruct
    public void init(){
        hoteles = hotelFacadeLocal.findAll();
        ciudades = ciudadFacadeLocal.findAll();
    }
    
    public void registrarHotel(){
        if(hotelFacadeLocal.registrarHotel(hotelReg, fk_ciudad)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Hotel registrado", "Hotel registrado"));
            hotelReg = new Hotel();
            hoteles = hotelFacadeLocal.findAll();
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de registro", "Error de registro"));
        }
    }
    
    public void guardarTemporal(Hotel h){
        hotelTemporal = h;
        fk_ciudad = h.getFkCiudad().getIdCiudad();
    }
    
    public void actualizarHotel(){
        try{
            hotelFacadeLocal.actualizarHotel(hotelReg, fk_ciudad);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Hotel editado", "Hotel editado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de edición", "Error de edición"));
        }
    }
    
    public void eliminarHotel(Hotel h){
        try {
            if (hotelFacadeLocal.eliminarHotel(h.getIdHotel())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Hotel eliminado", "Hotel eliminado"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de eliminación", "Error de eliminación"));
            }
        } catch (Exception e) {
        }
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

    public int getFk_ciudad() {
        return fk_ciudad;
    }

    public void setFk_ciudad(int fk_ciudad) {
        this.fk_ciudad = fk_ciudad;
    }
    
}
