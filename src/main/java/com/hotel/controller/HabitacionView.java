/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.controller;

import com.hotel.ejb.EstadoReservaFacadeLocal;
import com.hotel.ejb.HabitacionFacadeLocal;
import com.hotel.ejb.HotelFacadeLocal;
import com.hotel.model.EstadoReserva;
import com.hotel.model.Habitacion;
import com.hotel.model.Hotel;
import java.io.IOException;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
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
public class HabitacionView implements Serializable {

    @EJB
    private HabitacionFacadeLocal habitacionFacadeLocal;

    @EJB
    private EstadoReservaFacadeLocal estadoReservaFacadeLocal;

    @EJB
    private HotelFacadeLocal hotelFacadeLocal;

    @Inject
    private EstadoReserva estadoReserva;
    @Inject
    private Hotel hotel;

    private int fk_tipo_habitacion;
    private int fk_hotel;
    private int fk_estado;

    private Habitacion habReg;
    private Habitacion habTemporal;
    private Hotel htIn;
    private Hotel htTemporal;

    private List<Habitacion> habitaciones;
    private List<EstadoReserva> estadoReservas;
    private List<Habitacion> listaHabitaciones = new ArrayList<>();

    @PostConstruct
    public void init() {
        habitaciones = habitacionFacadeLocal.leerTodos();
        habReg = new Habitacion();
        habTemporal = new Habitacion();
    }

    public void registrarHabitacion() {

        try {
            if (habitacionFacadeLocal.crearHabitacion(habReg, fk_tipo_habitacion, fk_hotel)) {
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

    public void eliminarHabitacion(Habitacion ha) {
        try {
            if (habitacionFacadeLocal.eliminarHabitacion(ha.getIdHabitacion())) {
                listaHabitaciones = habitacionFacadeLocal.findAll();

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Habitación Eliminada", "Habitación eliminada"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de registro", "Error de registro"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error del componente", "Error del componente"));
        }
    }

    public void guardarTemporal(Habitacion ha) {
        habTemporal = ha;
    }

    public void consultarHabitacion() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/administrador/tablaHabitacion.xhtml");
    }

    public void editarHabitacion() {
        try {
            if (habitacionFacadeLocal.actualizarHabitacion(habTemporal, fk_tipo_habitacion, fk_estado, fk_hotel)) {
                habTemporal = new Habitacion();
                habitaciones = habitacionFacadeLocal.leerTodos();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Habitación editada", "Habitación editada"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "No se pudo editar", "No se pudo editar"));
            }
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
        if (habIn.getTelefono() == Short.parseShort("0")) {
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

    public int getFk_hotel() {
        return fk_hotel;
    }

    public void setFk_hotel(int fk_hotel) {
        this.fk_hotel = fk_hotel;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Hotel getHtIn() {
        return htIn;
    }

    public void setHtIn(Hotel htIn) {
        this.htIn = htIn;
    }

    public Hotel getHtTemporal() {
        return htTemporal;
    }

    public void setHtTemporal(Hotel htTemporal) {
        this.htTemporal = htTemporal;
    }

    public List<Habitacion> getListaHabitaciones() {
        return listaHabitaciones;
    }

    public void setListaHabitaciones(List<Habitacion> listaHabitaciones) {
        this.listaHabitaciones = listaHabitaciones;
    }

    public int getFk_estado() {
        return fk_estado;
    }

    public void setFk_estado(int fk_estado) {
        this.fk_estado = fk_estado;
    }

}
