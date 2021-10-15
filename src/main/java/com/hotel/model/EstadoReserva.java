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
@Table(name = "estado_reserva")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoReserva.findAll", query = "SELECT e FROM EstadoReserva e"),
    @NamedQuery(name = "EstadoReserva.findByIdEstadoReserva", query = "SELECT e FROM EstadoReserva e WHERE e.idEstadoReserva = :idEstadoReserva"),
    @NamedQuery(name = "EstadoReserva.findByDescripcion", query = "SELECT e FROM EstadoReserva e WHERE e.descripcion = :descripcion")})
public class EstadoReserva implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_estado_reserva")
    private Integer idEstadoReserva;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkEstado", fetch = FetchType.LAZY)
    private Collection<Reserva> reservaCollection;

    public EstadoReserva() {
    }

    public EstadoReserva(Integer idEstadoReserva) {
        this.idEstadoReserva = idEstadoReserva;
    }

    public EstadoReserva(Integer idEstadoReserva, String descripcion) {
        this.idEstadoReserva = idEstadoReserva;
        this.descripcion = descripcion;
    }

    public Integer getIdEstadoReserva() {
        return idEstadoReserva;
    }

    public void setIdEstadoReserva(Integer idEstadoReserva) {
        this.idEstadoReserva = idEstadoReserva;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        hash += (idEstadoReserva != null ? idEstadoReserva.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoReserva)) {
            return false;
        }
        EstadoReserva other = (EstadoReserva) object;
        if ((this.idEstadoReserva == null && other.idEstadoReserva != null) || (this.idEstadoReserva != null && !this.idEstadoReserva.equals(other.idEstadoReserva))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hotel.model.EstadoReserva[ idEstadoReserva=" + idEstadoReserva + " ]";
    }
    
}
