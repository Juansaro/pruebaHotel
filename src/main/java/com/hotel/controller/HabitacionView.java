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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    
    private int fk_tipo_habitacion;
    
    private Habitacion habReg = new Habitacion();
    private Habitacion habTemporal = new Habitacion();
    
    private List<Habitacion> habitaciones;
    private List<EstadoReserva> estadoReservas;
    
    @PostConstruct
    public void init(){
        habitaciones = habitacionFacadeLocal.findAll();
        estadoReservas = estadoReservaFacadeLocal.findAll();
    }
    
    public void registrarHabitacion(){
        try {
            if (habitacionFacadeLocal.crearHabitacion(habReg, fk_tipo_habitacion)) {
                habReg = new Habitacion();
                habitaciones = habitacionFacadeLocal.findAll();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Habitación registrada", "Habitación registrada"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de registro", "Error de registro"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error del componente", "Error del componente"));
        }
    }
    
     public void eliminarHabitacion(Habitacion h){
        try {
            if (habitacionFacadeLocal.eliminarHabitacion(h.getNumeroHabitacion())) {
                habReg = new Habitacion();
                habitaciones = habitacionFacadeLocal.findAll();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Habitación Eliminada", "Habitación eliminada"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de registro", "Error de registro"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error del componente", "Error del componente"));
        }
    }
     
    public void guardarTemporal(Habitacion h){
        habTemporal = new Habitacion();
        fk_tipo_habitacion = h.getTipoHabitacionIdTipoHabitacion().getIdTipoHabitacion();
    }
    
    public void editarHabitacion(){
        try {
            habitacionFacadeLocal.actualizarHabitacion(habTemporal, fk_tipo_habitacion);
            habTemporal = new Habitacion();
            habitaciones = habitacionFacadeLocal.findAll();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Habitación Eliminada", "Habitación eliminada"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error del componente", "Error del componente"));
        }
    }
    
    public void cambiarBano(Habitacion habIn) {
        if (habIn.getBano() == Short.parseShort("0")) {
            habIn.setBano(Short.parseShort("1"));
        } else {
            habIn.setBano(Short.parseShort("0"));
        }
        habitacionFacadeLocal.edit(habIn);
    }
    
    public void cambiarTelefono(Habitacion habIn) {
        if (habIn.getTelefono()== Short.parseShort("0")) {
            habIn.setTelefono(Short.parseShort("1"));
        } else {
            habIn.setTelefono(Short.parseShort("0"));
        }
        habitacionFacadeLocal.edit(habIn);
    }
    
    public void cambiarCalefaccion(Habitacion habIn) {
        if (habIn.getCalefaccion() == Short.parseShort("0")) {
            habIn.setCalefaccion(Short.parseShort("1"));
        } else {
            habIn.setCalefaccion(Short.parseShort("0"));
        }
        habitacionFacadeLocal.edit(habIn);
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

    public Habitacion getHabReg() {
        return habReg;
    }

    public void setHabReg(Habitacion habReg) {
        this.habReg = habReg;
    }

    public Habitacion getHabTemporal() {
        return habTemporal;
    }

    public void setHabTemporal(Habitacion habTemporal) {
        this.habTemporal = habTemporal;
    }

    public int getFk_tipo_habitacion() {
        return fk_tipo_habitacion;
    }

    public void setFk_tipo_habitacion(int fk_tipo_habitacion) {
        this.fk_tipo_habitacion = fk_tipo_habitacion;
    }
    
}
