/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "habitacion")
@NamedQueries({
    @NamedQuery(name = "Habitacion.findAll", query = "SELECT h FROM Habitacion h")})
public class Habitacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_habitacion")
    private Integer idHabitacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bano")
    private short bano;
    @Basic(optional = false)
    @NotNull
    @Column(name = "calefaccion")
    private short calefaccion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "telefono")
    private short telefono;
    @Size(max = 300)
    @Column(name = "habitacion_foto")
    private String habitacionFoto;
    @JoinTable(name = "hotel_has_habitacion", joinColumns = {
        @JoinColumn(name = "fk_habitacion", referencedColumnName = "id_habitacion")}, inverseJoinColumns = {
        @JoinColumn(name = "fk_hotel", referencedColumnName = "id_hotel")})
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Hotel> hotelCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkHabitacion", fetch = FetchType.LAZY)
    private Collection<Reserva> reservaCollection;

    public Habitacion() {
    }

    public Habitacion(Integer idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public Habitacion(Integer idHabitacion, short bano, short calefaccion, short telefono) {
        this.idHabitacion = idHabitacion;
        this.bano = bano;
        this.calefaccion = calefaccion;
        this.telefono = telefono;
    }

    public Integer getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(Integer idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public short getBano() {
        return bano;
    }

    public void setBano(short bano) {
        this.bano = bano;
    }

    public short getCalefaccion() {
        return calefaccion;
    }

    public void setCalefaccion(short calefaccion) {
        this.calefaccion = calefaccion;
    }

    public short getTelefono() {
        return telefono;
    }

    public void setTelefono(short telefono) {
        this.telefono = telefono;
    }

    public String getHabitacionFoto() {
        return habitacionFoto;
    }

    public void setHabitacionFoto(String habitacionFoto) {
        this.habitacionFoto = habitacionFoto;
    }

    public Collection<Hotel> getHotelCollection() {
        return hotelCollection;
    }

    public void setHotelCollection(Collection<Hotel> hotelCollection) {
        this.hotelCollection = hotelCollection;
    }

    public Collection<Reserva> getReservaCollection() {
        return reservaCollection;
    }

    public void setReservaCollection(Collection<Reserva> reservaCollection) {
        this.reservaCollection = reservaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHabitacion != null ? idHabitacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Habitacion)) {
            return false;
        }
        Habitacion other = (Habitacion) object;
        if ((this.idHabitacion == null && other.idHabitacion != null) || (this.idHabitacion != null && !this.idHabitacion.equals(other.idHabitacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hotel.model.Habitacion[ idHabitacion=" + idHabitacion + " ]";
    }
    
}
