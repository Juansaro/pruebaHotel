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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    
    private int fk_huesped;
    private int fk_habitacion;
    private int fk_usuario;
    private int fk_hotel;
    private int fk_estado;
    
    private List<Reserva> reservas;
    private List<Habitacion> habitaciones;
    private List<Usuario> empleados;
    private List<Hotel> hoteles;
    private List<EstadoReserva> estados;
    
    private Reserva resReg = new Reserva();
    private Reserva resTemporal = new Reserva();
    
    @PostConstruct
    public void init(){
        reservas = reservaFacadeLocal.findAll();
        habitaciones = HabitacionFacadeLocal.findAll();
        //Hacer filtro de empleados
        empleados = usuarioFacadeLocal.findAll();
        hoteles = hotelFacadeLocal.findAll();
        estados = estadoReservaFacadeLocal.findAll();
    }
    
    public void registrarReserva(){
        if(reservaFacadeLocal.registrarReserva(resReg, fk_huesped, fk_habitacion, fk_estado, fk_hotel, fk_estado)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Reserva registrada", "Reserva registrada"));
            resReg = new Reserva();
            reservas = reservaFacadeLocal.findAll();
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de registro", "Error de registro"));
        }
    }
    
    public void guardarTemporal(Reserva r){
        resTemporal = r;
        fk_huesped = r.getFkHuesped().getIdHuesped();
        fk_habitacion = r.getFkHabitacion().getNumeroHabitacion();
        fk_usuario = r.getFkEmpleado().getIdUsuario();
        fk_hotel = r.getFkHotel().getIdHotel();
        fk_estado = r.getEstadoReservaIdEstadoReserva().getIdEstadoReserva();
    }
    
    public void actualizarReserva(){
        try{
            reservaFacadeLocal.actualizarReserva(resTemporal, fk_huesped, fk_habitacion, fk_estado, fk_hotel, fk_estado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Reserva editado", "Reserva editado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de edición", "Error de edición"));
        }
    }
    
    public void eliminarReserva(Reserva r){
        try {
            if (reservaFacadeLocal.eliminarReserva(r.getIdReserva())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Reserva eliminada", "Reserva eliminada"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de eliminación", "Error de eliminación"));
            }
        } catch (Exception e) {
        }
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

    public int getFk_huesped() {
        return fk_huesped;
    }

    public void setFk_huesped(int fk_huesped) {
        this.fk_huesped = fk_huesped;
    }

    public int getFk_habitacion() {
        return fk_habitacion;
    }

    public void setFk_habitacion(int fk_habitacion) {
        this.fk_habitacion = fk_habitacion;
    }

    public int getFk_usuario() {
        return fk_usuario;
    }

    public void setFk_usuario(int fk_usuario) {
        this.fk_usuario = fk_usuario;
    }

    public int getFk_hotel() {
        return fk_hotel;
    }

    public void setFk_hotel(int fk_hotel) {
        this.fk_hotel = fk_hotel;
    }

    public int getFk_estado() {
        return fk_estado;
    }

    public void setFk_estado(int fk_estado) {
        this.fk_estado = fk_estado;
    }

    public Reserva getResReg() {
        return resReg;
    }

    public void setResReg(Reserva resReg) {
        this.resReg = resReg;
    }

    public Reserva getResTemporal() {
        return resTemporal;
    }

    public void setResTemporal(Reserva resTemporal) {
        this.resTemporal = resTemporal;
    }
    
}
