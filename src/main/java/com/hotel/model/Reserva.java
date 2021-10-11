/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juan
 */
@Entity
@Table(name = "reserva")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reserva.findAll", query = "SELECT r FROM Reserva r"),
    @NamedQuery(name = "Reserva.findByIdReserva", query = "SELECT r FROM Reserva r WHERE r.idReserva = :idReserva"),
    @NamedQuery(name = "Reserva.findByFechaIngreso", query = "SELECT r FROM Reserva r WHERE r.fechaIngreso = :fechaIngreso"),
    @NamedQuery(name = "Reserva.findByFechaSalida", query = "SELECT r FROM Reserva r WHERE r.fechaSalida = :fechaSalida")})
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_reserva")
    private Integer idReserva;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    @Column(name = "fecha_salida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSalida;
    @JoinColumn(name = "estado_reserva_id_estado_reserva", referencedColumnName = "id_estado_reserva")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EstadoReserva estadoReservaIdEstadoReserva;
    @JoinColumn(name = "fk_habitacion", referencedColumnName = "numero_habitacion")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Habitacion fkHabitacion;
    @JoinColumn(name = "fk_hotel", referencedColumnName = "id_hotel")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Hotel fkHotel;
    @JoinColumn(name = "fk_huesped", referencedColumnName = "id_huesped")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Huesped fkHuesped;
    @JoinColumn(name = "fk_empleado", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario fkEmpleado;

    public Reserva() {
    }

    public Reserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public Reserva(Integer idReserva, Date fechaIngreso) {
        this.idReserva = idReserva;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public EstadoReserva getEstadoReservaIdEstadoReserva() {
        return estadoReservaIdEstadoReserva;
    }

    public void setEstadoReservaIdEstadoReserva(EstadoReserva estadoReservaIdEstadoReserva) {
        this.estadoReservaIdEstadoReserva = estadoReservaIdEstadoReserva;
    }

    public Habitacion getFkHabitacion() {
        return fkHabitacion;
    }

    public void setFkHabitacion(Habitacion fkHabitacion) {
        this.fkHabitacion = fkHabitacion;
    }

    public Hotel getFkHotel() {
        return fkHotel;
    }

    public void setFkHotel(Hotel fkHotel) {
        this.fkHotel = fkHotel;
    }

    public Huesped getFkHuesped() {
        return fkHuesped;
    }

    public void setFkHuesped(Huesped fkHuesped) {
        this.fkHuesped = fkHuesped;
    }

    public Usuario getFkEmpleado() {
        return fkEmpleado;
    }

    public void setFkEmpleado(Usuario fkEmpleado) {
        this.fkEmpleado = fkEmpleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReserva != null ? idReserva.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reserva)) {
            return false;
        }
        Reserva other = (Reserva) object;
        if ((this.idReserva == null && other.idReserva != null) || (this.idReserva != null && !this.idReserva.equals(other.idReserva))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hotel.model.Reserva[ idReserva=" + idReserva + " ]";
    }
    
}
