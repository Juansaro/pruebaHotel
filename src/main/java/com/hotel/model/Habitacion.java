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
    @NamedQuery(name = "Habitacion.findByIdHabitacion", query = "SELECT h FROM Habitacion h WHERE h.idHabitacion = :idHabitacion"),
    @NamedQuery(name = "Habitacion.findByBano", query = "SELECT h FROM Habitacion h WHERE h.bano = :bano"),
    @NamedQuery(name = "Habitacion.findByCalefaccion", query = "SELECT h FROM Habitacion h WHERE h.calefaccion = :calefaccion"),
    @NamedQuery(name = "Habitacion.findByTelefono", query = "SELECT h FROM Habitacion h WHERE h.telefono = :telefono"),
    @NamedQuery(name = "Habitacion.findByHabitacionFoto", query = "SELECT h FROM Habitacion h WHERE h.habitacionFoto = :habitacionFoto"),
    @NamedQuery(name = "Habitacion.findByPrecio", query = "SELECT h FROM Habitacion h WHERE h.precio = :precio")})
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
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio")
    private Float precio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkHabitacion", fetch = FetchType.LAZY)
    private Collection<Reserva> reservaCollection;
    @JoinColumn(name = "estado_habitacion_id_estado", referencedColumnName = "id_estado")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EstadoHabitacion estadoHabitacionIdEstado;
    @JoinColumn(name = "hotel_id_hotel", referencedColumnName = "id_hotel")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Hotel hotelIdHotel;
    @JoinColumn(name = "fk_tipo", referencedColumnName = "id_tipo_habitacion")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoHabitacion fkTipo;

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

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    @XmlTransient
    public Collection<Reserva> getReservaCollection() {
        return reservaCollection;
    }

    public void setReservaCollection(Collection<Reserva> reservaCollection) {
        this.reservaCollection = reservaCollection;
    }

    public EstadoHabitacion getEstadoHabitacionIdEstado() {
        return estadoHabitacionIdEstado;
    }

    public void setEstadoHabitacionIdEstado(EstadoHabitacion estadoHabitacionIdEstado) {
        this.estadoHabitacionIdEstado = estadoHabitacionIdEstado;
    }

    public Hotel getHotelIdHotel() {
        return hotelIdHotel;
    }

    public void setHotelIdHotel(Hotel hotelIdHotel) {
        this.hotelIdHotel = hotelIdHotel;
    }

    public TipoHabitacion getFkTipo() {
        return fkTipo;
    }

    public void setFkTipo(TipoHabitacion fkTipo) {
        this.fkTipo = fkTipo;
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
