/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "telefonos")
@NamedQueries({
    @NamedQuery(name = "Telefonos.findAll", query = "SELECT t FROM Telefonos t")})
public class Telefonos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_telefonos")
    private Integer idTelefonos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "numero")
    private String numero;
    @JoinColumn(name = "fk_hotel", referencedColumnName = "id_hotel")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Hotel fkHotel;

    public Telefonos() {
    }

    public Telefonos(Integer idTelefonos) {
        this.idTelefonos = idTelefonos;
    }

    public Telefonos(Integer idTelefonos, String numero) {
        this.idTelefonos = idTelefonos;
        this.numero = numero;
    }

    public Integer getIdTelefonos() {
        return idTelefonos;
    }

    public void setIdTelefonos(Integer idTelefonos) {
        this.idTelefonos = idTelefonos;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Hotel getFkHotel() {
        return fkHotel;
    }

    public void setFkHotel(Hotel fkHotel) {
        this.fkHotel = fkHotel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTelefonos != null ? idTelefonos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Telefonos)) {
            return false;
        }
        Telefonos other = (Telefonos) object;
        if ((this.idTelefonos == null && other.idTelefonos != null) || (this.idTelefonos != null && !this.idTelefonos.equals(other.idTelefonos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hotel.model.Telefonos[ idTelefonos=" + idTelefonos + " ]";
    }
    
}
