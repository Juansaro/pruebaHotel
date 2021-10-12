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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juan
 */
@Entity
@Table(name = "hotel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hotel.findAll", query = "SELECT h FROM Hotel h"),
    @NamedQuery(name = "Hotel.findByIdHotel", query = "SELECT h FROM Hotel h WHERE h.idHotel = :idHotel"),
    @NamedQuery(name = "Hotel.findByNombre", query = "SELECT h FROM Hotel h WHERE h.nombre = :nombre"),
    @NamedQuery(name = "Hotel.findByHotelFoto", query = "SELECT h FROM Hotel h WHERE h.hotelFoto = :hotelFoto"),
    @NamedQuery(name = "Hotel.findByDireccion", query = "SELECT h FROM Hotel h WHERE h.direccion = :direccion")})
public class Hotel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_hotel")
    private Integer idHotel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 300)
    @Column(name = "hotel_foto")
    private String hotelFoto;
    @Size(max = 45)
    @Column(name = "direccion")
    private String direccion;
    @JoinTable(name = "hotel_has_telefono", joinColumns = {
        @JoinColumn(name = "fk_hotel", referencedColumnName = "id_hotel")}, inverseJoinColumns = {
        @JoinColumn(name = "fk_telefono", referencedColumnName = "id_telefono")})
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Telefono> telefonoCollection;
    @ManyToMany(mappedBy = "hotelCollection", fetch = FetchType.LAZY)
    private Collection<Habitacion> habitacionCollection;
    @JoinColumn(name = "fk_ciudad", referencedColumnName = "id_ciudad")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Ciudad fkCiudad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkHotel", fetch = FetchType.LAZY)
    private Collection<Reserva> reservaCollection;

    public Hotel() {
    }

    public Hotel(Integer idHotel) {
        this.idHotel = idHotel;
    }

    public Hotel(Integer idHotel, String nombre) {
        this.idHotel = idHotel;
        this.nombre = nombre;
    }

    public Integer getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(Integer idHotel) {
        this.idHotel = idHotel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHotelFoto() {
        return hotelFoto;
    }

    public void setHotelFoto(String hotelFoto) {
        this.hotelFoto = hotelFoto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @XmlTransient
    public Collection<Telefono> getTelefonoCollection() {
        return telefonoCollection;
    }

    public void setTelefonoCollection(Collection<Telefono> telefonoCollection) {
        this.telefonoCollection = telefonoCollection;
    }

    @XmlTransient
    public Collection<Habitacion> getHabitacionCollection() {
        return habitacionCollection;
    }

    public void setHabitacionCollection(Collection<Habitacion> habitacionCollection) {
        this.habitacionCollection = habitacionCollection;
    }

    public Ciudad getFkCiudad() {
        return fkCiudad;
    }

    public void setFkCiudad(Ciudad fkCiudad) {
        this.fkCiudad = fkCiudad;
    }

    @XmlTransient
    public Collection<Reserva> getReservaCollection() {
        return reservaCollection;
    }

    public void setReservaCollection(Collection<Reserva> reservaCollection) {
        this.reservaCollection = reservaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHotel != null ? idHotel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hotel)) {
            return false;
        }
        Hotel other = (Hotel) object;
        if ((this.idHotel == null && other.idHotel != null) || (this.idHotel != null && !this.idHotel.equals(other.idHotel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hotel.model.Hotel[ idHotel=" + idHotel + " ]";
    }
    
}
