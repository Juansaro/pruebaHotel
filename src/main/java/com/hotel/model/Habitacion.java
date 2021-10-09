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
@Table(name = "habitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Habitacion.findAll", query = "SELECT h FROM Habitacion h"),
    @NamedQuery(name = "Habitacion.findByNumeroHabitacion", query = "SELECT h FROM Habitacion h WHERE h.numeroHabitacion = :numeroHabitacion"),
    @NamedQuery(name = "Habitacion.findByBano", query = "SELECT h FROM Habitacion h WHERE h.bano = :bano"),
    @NamedQuery(name = "Habitacion.findByCalefaccion", query = "SELECT h FROM Habitacion h WHERE h.calefaccion = :calefaccion"),
    @NamedQuery(name = "Habitacion.findByTelefono", query = "SELECT h FROM Habitacion h WHERE h.telefono = :telefono"),
    @NamedQuery(name = "Habitacion.findByHabitacionFoto", query = "SELECT h FROM Habitacion h WHERE h.habitacionFoto = :habitacionFoto"),
    @NamedQuery(name = "Habitacion.findByEstado", query = "SELECT h FROM Habitacion h WHERE h.estado = :estado")})
public class Habitacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero_habitacion")
    private Integer numeroHabitacion;
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private short estado;
    @JoinTable(name = "hotel_has_habitacion", joinColumns = {
        @JoinColumn(name = "fk_habitacion", referencedColumnName = "numero_habitacion")}, inverseJoinColumns = {
        @JoinColumn(name = "fk_hotel", referencedColumnName = "id_hotel")})
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Hotel> hotelCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkHabitacion", fetch = FetchType.LAZY)
    private Collection<Reserva> reservaCollection;
    @JoinColumn(name = "tipo_habitacion_id_tipo_habitacion", referencedColumnName = "id_tipo_habitacion")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoHabitacion tipoHabitacionIdTipoHabitacion;

    public Habitacion() {
    }

    public Habitacion(Integer numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public Habitacion(Integer numeroHabitacion, short bano, short calefaccion, short telefono, short estado) {
        this.numeroHabitacion = numeroHabitacion;
        this.bano = bano;
        this.calefaccion = calefaccion;
        this.telefono = telefono;
        this.estado = estado;
    }

    public Integer getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(Integer numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
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

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Collection<Hotel> getHotelCollection() {
        return hotelCollection;
    }

    public void setHotelCollection(Collection<Hotel> hotelCollection) {
        this.hotelCollection = hotelCollection;
    }

    @XmlTransient
    public Collection<Reserva> getReservaCollection() {
        return reservaCollection;
    }

    public void setReservaCollection(Collection<Reserva> reservaCollection) {
        this.reservaCollection = reservaCollection;
    }

    public TipoHabitacion getTipoHabitacionIdTipoHabitacion() {
        return tipoHabitacionIdTipoHabitacion;
    }

    public void setTipoHabitacionIdTipoHabitacion(TipoHabitacion tipoHabitacionIdTipoHabitacion) {
        this.tipoHabitacionIdTipoHabitacion = tipoHabitacionIdTipoHabitacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroHabitacion != null ? numeroHabitacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Habitacion)) {
            return false;
        }
        Habitacion other = (Habitacion) object;
        if ((this.numeroHabitacion == null && other.numeroHabitacion != null) || (this.numeroHabitacion != null && !this.numeroHabitacion.equals(other.numeroHabitacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hotel.model.Habitacion[ numeroHabitacion=" + numeroHabitacion + " ]";
    }
    
}
