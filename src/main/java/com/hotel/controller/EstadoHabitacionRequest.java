/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.controller;

import com.hotel.ejb.EstadoHabitacionFacadeLocal;
import com.hotel.model.EstadoHabitacion;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named(value = "estadoHabitacionRequest")
@RequestScoped
public class EstadoHabitacionRequest implements Serializable{
    
    @EJB
    private EstadoHabitacionFacadeLocal estadoHabitacionFacadeLocal;
    
    private List<EstadoHabitacion> estadoHabitaciones;
    
    @PostConstruct
    public void init(){
        estadoHabitaciones = estadoHabitacionFacadeLocal.findAll();
    }

    public List<EstadoHabitacion> getEstadoHabitaciones() {
        return estadoHabitaciones;
    }

    public void setEstadoHabitaciones(List<EstadoHabitacion> estadoHabitaciones) {
        this.estadoHabitaciones = estadoHabitaciones;
    }
    
}
