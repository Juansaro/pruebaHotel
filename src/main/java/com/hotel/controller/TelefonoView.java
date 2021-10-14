/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.controller;

import com.hotel.ejb.HotelFacadeLocal;
import com.hotel.ejb.TelefonoFacadeLocal;
import com.hotel.model.Hotel;
import com.hotel.model.Telefono;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "telefonoView")
@ViewScoped
public class TelefonoView implements Serializable {

    @EJB
    private TelefonoFacadeLocal telefonoFacadeLocal;

    @EJB
    private HotelFacadeLocal hotelFacadeLocal;

    @Inject
    private Hotel hotel;

    private int fk_hotel;

    private List<Hotel> hoteles;
    private List<Telefono> telefonos;

    private Telefono telReg = new Telefono();
    private Telefono telTemporal = new Telefono();

    @PostConstruct
    public void init() {
        telefonos = telefonoFacadeLocal.findAll();
    }

    public void registrarTelefono() {
        if (telefonoFacadeLocal.registrarTelefono(telReg, fk_hotel)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Hotel registrado", "Hotel registrado"));
            telReg = new Telefono();
            telefonos = telefonoFacadeLocal.leerTodos();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de registro", "Error de registro"));
        }
    }

    public void guardarTemporal(Telefono t) {
        telTemporal = t;
    }

    public void actualizarTelefono() {
        try {

            if (telefonoFacadeLocal.actualizarTelefono(telTemporal, fk_hotel)) {
                telefonos = telefonoFacadeLocal.leerTodos();
                telTemporal = new Telefono();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Hotel editado", "Hotel editado"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de edición", "Error de edición"));
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de componente", "Error de componente"));
        }
    }

    public void eliminarTelefono(Telefono t) {
        try {
            if (telefonoFacadeLocal.eliminarTelefono(t.getIdTelefono())) {
                telefonos = telefonoFacadeLocal.leerTodos();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Hotel eliminado", "Hotel eliminado"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de eliminación", "Error de eliminación"));
            }
        } catch (Exception e) {
        }
    }

    public int getFk_hotel() {
        return fk_hotel;
    }

    public void setFk_hotel(int fk_hotel) {
        this.fk_hotel = fk_hotel;
    }

    public List<Hotel> getHoteles() {
        return hoteles;
    }

    public void setHoteles(List<Hotel> hoteles) {
        this.hoteles = hoteles;
    }

    public List<Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<Telefono> telefonos) {
        this.telefonos = telefonos;
    }

    public Telefono getTelReg() {
        return telReg;
    }

    public void setTelReg(Telefono telReg) {
        this.telReg = telReg;
    }

    public Telefono getTelTemporal() {
        return telTemporal;
    }

    public void setTelTemporal(Telefono telTemporal) {
        this.telTemporal = telTemporal;
    }

}
