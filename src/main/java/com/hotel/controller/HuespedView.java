/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.controller;

import com.hotel.ejb.CiudadFacadeLocal;
import com.hotel.ejb.HuespedFacadeLocal;
import com.hotel.model.Ciudad;
import com.hotel.model.Huesped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "huespedView")
@ViewScoped
public class HuespedView implements Serializable {

    @EJB
    private HuespedFacadeLocal huespedFacadeLocal;

    @EJB
    private CiudadFacadeLocal ciudadFacadeLocal;

    @Inject
    private Ciudad ciudad;

    private int fk_ciudad;

    private List<Huesped> huespedes;
    private List<Ciudad> ciudades;

    private Huesped hueReg = new Huesped();
    private Huesped hueTemporal = new Huesped();

    @PostConstruct
    public void init() {
        huespedes = huespedFacadeLocal.findAll();
        ciudades = ciudadFacadeLocal.findAll();
    }

    public void registrarHuesped() {
        if (huespedFacadeLocal.registrarHuesped(hueReg, fk_ciudad)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Huesped registrado", "Huesped registrado"));
            hueReg = new Huesped();
            huespedes = huespedFacadeLocal.leerTodos();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de registro", "Error de registro"));
        }
    }

    public void guardarTemporal(Huesped h) {
        hueTemporal = h;
        fk_ciudad = h.getCiudadIdCiudad().getIdCiudad();
    }

    public void actualizarHuesped() {
        try {
            huespedFacadeLocal.actualizarHuesped(hueTemporal, fk_ciudad);
            huespedes = huespedFacadeLocal.leerTodos();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Huesped editado", "Huesped editado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de edición", "Error de edición"));
        }
    }

    public void eliminarHuesped(Huesped h) {
        try {
            huespedFacadeLocal.remove(h);
            huespedes = huespedFacadeLocal.leerTodos();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Huesped eliminado", "Huesped eliminado"));

        } catch (Exception e) {
        }
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public List<Huesped> getHuespedes() {
        return huespedes;
    }

    public void setHuespedes(List<Huesped> huespedes) {
        this.huespedes = huespedes;
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }

    public Huesped getHueReg() {
        return hueReg;
    }

    public void setHueReg(Huesped hueReg) {
        this.hueReg = hueReg;
    }

    public Huesped getHueTemporal() {
        return hueTemporal;
    }

    public void setHueTemporal(Huesped hueTemporal) {
        this.hueTemporal = hueTemporal;
    }

    public int getFk_ciudad() {
        return fk_ciudad;
    }

    public void setFk_ciudad(int fk_ciudad) {
        this.fk_ciudad = fk_ciudad;
    }

}
