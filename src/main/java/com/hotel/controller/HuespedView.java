/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.controller;

import com.hotel.ejb.CiudadFacadeLocal;
import com.hotel.ejb.HuespedFacadeLocal;
import com.hotel.model.Ciudad;
import com.hotel.model.Huesped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "huespedView")
@ViewScoped
public class HuespedView implements Serializable{
    
    @EJB
    private HuespedFacadeLocal huespedFacadeLocal;
    
    @EJB
    private CiudadFacadeLocal ciudadFacadeLocal;
    
    @Inject
    private Ciudad ciudad;
    
    private List<Huesped> huespedes;
    private List<Ciudad> ciudades;
    
    @PostConstruct
    public void init(){
        huespedes = huespedFacadeLocal.findAll();
        ciudades = ciudadFacadeLocal.findAll();
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public List<Huesped> getHuespedes() {
        return huespedes;
    }

    public void setHuespedes(List<Huesped> huespedes) {
        this.huespedes = huespedes;
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }
    
}
