/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.controller;

import com.hotel.ejb.RolFacadeLocal;
import com.hotel.model.Rol;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named(value = "rolRequest")
@RequestScoped
public class RolRequest implements Serializable{
    
    @EJB
    private RolFacadeLocal rolFacadeLocal;
    
    private Rol rol;
    
    private List<Rol> roles;
    
    @PostConstruct
    public void init(){
        roles = rolFacadeLocal.findAll();
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }
    
}
