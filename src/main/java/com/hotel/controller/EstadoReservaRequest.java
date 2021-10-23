/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.controller;

import com.hotel.ejb.EstadoReservaFacadeLocal;
import com.hotel.model.EstadoReserva;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named(value = "estadoReservaRequest")
@RequestScoped
public class EstadoReservaRequest implements Serializable{
    
    @EJB
    private EstadoReservaFacadeLocal estadoReservaFacadeLocal;
    
    private List<EstadoReserva> estadoReservas;
    
    @PostConstruct
    public void init(){
        estadoReservas = estadoReservaFacadeLocal.leerTodos();
    }

    public List<EstadoReserva> getEstadoReservas() {
        return estadoReservas;
    }

    public void setEstadoReservas(List<EstadoReserva> estadoReservas) {
        this.estadoReservas = estadoReservas;
    }
    
}
