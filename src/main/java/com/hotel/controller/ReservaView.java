/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.controller;

import com.hotel.ejb.EstadoReservaFacadeLocal;
import com.hotel.ejb.HabitacionFacadeLocal;
import com.hotel.ejb.HotelFacadeLocal;
import com.hotel.ejb.HuespedFacadeLocal;
import com.hotel.ejb.ReservaFacadeLocal;
import com.hotel.ejb.UsuarioFacadeLocal;
import com.hotel.model.EstadoReserva;
import com.hotel.model.Habitacion;
import com.hotel.model.Hotel;
import com.hotel.model.Huesped;
import com.hotel.model.Reserva;
import com.hotel.model.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "reservaView")
@ViewScoped
public class ReservaView implements Serializable{
    
    @EJB
    private ReservaFacadeLocal reservaFacadeLocal;
    
    @EJB
    private HuespedFacadeLocal huespedFacadeLocal; 
    
    @EJB
    private HabitacionFacadeLocal HabitacionFacadeLocal;
    
    @EJB
    private UsuarioFacadeLocal usuarioFacadeLocal;
    
    @EJB
    private HotelFacadeLocal hotelFacadeLocal;
    
    @EJB
    private EstadoReservaFacadeLocal estadoReservaFacadeLocal;
    
    @Inject
    private Huesped huespued;
    
    @Inject
    private Habitacion habitacion;
    
    @Inject
    private Usuario usuario;
    
    @Inject
    private EstadoReserva estadoReserva;
    
    private List<Reserva> reservas;
    private List<Habitacion> habitaciones;
    private List<Usuario> empleados;
    private List<Hotel> hoteles;
    private List<EstadoReserva> estados;
    
    @PostConstruct
    public void init(){
        reservas = reservaFacadeLocal.findAll();
        habitaciones = HabitacionFacadeLocal.findAll();
        //Hacer filtro de empleados
        empleados = usuarioFacadeLocal.findAll();
        hoteles = hotelFacadeLocal.findAll();
        estados = estadoReservaFacadeLocal.findAll();
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public List<Usuario> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Usuario> empleados) {
        this.empleados = empleados;
    }

    public List<Hotel> getHoteles() {
        return hoteles;
    }

    public void setHoteles(List<Hotel> hoteles) {
        this.hoteles = hoteles;
    }

    public List<EstadoReserva> getEstados() {
        return estados;
    }

    public void setEstados(List<EstadoReserva> estados) {
        this.estados = estados;
    }

    public Huesped getHuespued() {
        return huespued;
    }

    public void setHuespued(Huesped huespued) {
        this.huespued = huespued;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public EstadoReserva getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(EstadoReserva estadoReserva) {
        this.estadoReserva = estadoReserva;
    }
    
}
