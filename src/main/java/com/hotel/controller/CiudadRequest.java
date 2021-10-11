/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.controller;

import com.hotel.ejb.CiudadFacadeLocal;
import com.hotel.model.Ciudad;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named(value = "ciudadRequest")
@ViewScoped
public class CiudadRequest implements Serializable{
    
    @EJB
    private CiudadFacadeLocal ciudadFacadeLocal;
    
    private List<Ciudad> ciudades;
    
    @PostConstruct
    public void init(){
        ciudades = ciudadFacadeLocal.findAll();
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }
    
}
