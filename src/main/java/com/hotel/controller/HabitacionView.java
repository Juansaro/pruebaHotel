/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.controller;

import com.hotel.ejb.EstadoReservaFacadeLocal;
import com.hotel.ejb.HabitacionFacadeLocal;
import com.hotel.model.EstadoReserva;
import com.hotel.model.Habitacion;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "habitacionView")
@ViewScoped
public class HabitacionView implements Serializable{
    
    @EJB
    private HabitacionFacadeLocal habitacionFacadeLocal;
    
    @EJB
    private EstadoReservaFacadeLocal estadoReservaFacadeLocal;
    
    @Inject
    private EstadoReserva estadoReserva;
    
    private List<Habitacion> habitaciones;
    private List<EstadoReserva> estadoReservas;
    
    @PostConstruct
    public void init(){
        habitaciones = habitacionFacadeLocal.findAll();
        estadoReservas = estadoReservaFacadeLocal.findAll();
    }

    public EstadoReserva getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(EstadoReserva estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public List<EstadoReserva> getEstadoReservas() {
        return estadoReservas;
    }

    public void setEstadoReservas(List<EstadoReserva> estadoReservas) {
        this.estadoReservas = estadoReservas;
    }
    
}
