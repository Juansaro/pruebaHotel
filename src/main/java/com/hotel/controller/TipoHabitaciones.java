/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.controller;

import com.hotel.ejb.TipoHabitacionFacadeLocal;
import com.hotel.model.TipoHabitacion;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named(value = "tipoHabitaciones")
@RequestScoped
public class TipoHabitaciones implements Serializable{
    
    @EJB
    private TipoHabitacionFacadeLocal tipoHabitacionFacadeLocal; 
    
    private List<TipoHabitacion> tipoHabitaciones;
    
    @PostConstruct
    public void init(){
        tipoHabitaciones = tipoHabitacionFacadeLocal.findAll();
    }

    public List<TipoHabitacion> getTipoHabitaciones() {
        return tipoHabitaciones;
    }

    public void setTipoHabitaciones(List<TipoHabitacion> tipoHabitaciones) {
        this.tipoHabitaciones = tipoHabitaciones;
    }
    
}
