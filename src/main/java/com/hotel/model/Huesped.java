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
@Table(name = "huesped")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Huesped.findAll", query = "SELECT h FROM Huesped h"),
    @NamedQuery(name = "Huesped.findByIdHuesped", query = "SELECT h FROM Huesped h WHERE h.idHuesped = :idHuesped"),
    @NamedQuery(name = "Huesped.findByDocumento", query = "SELECT h FROM Huesped h WHERE h.documento = :documento"),
    @NamedQuery(name = "Huesped.findByNombre", query = "SELECT h FROM Huesped h WHERE h.nombre = :nombre"),
    @NamedQuery(name = "Huesped.findByApellido", query = "SELECT h FROM Huesped h WHERE h.apellido = :apellido"),
    @NamedQuery(name = "Huesped.findByCorreo", query = "SELECT h FROM Huesped h WHERE h.correo = :correo"),
    @NamedQuery(name = "Huesped.findByDireccion", query = "SELECT h FROM Huesped h WHERE h.direccion = :direccion"),
    @NamedQuery(name = "Huesped.findByBarrio", query = "SELECT h FROM Huesped h WHERE h.barrio = :barrio")})
public class Huesped implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_huesped")
    private Integer idHuesped;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "documento")
    private String documento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "apellido")
    private String apellido;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "correo")
    private String correo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 45)
    @Column(name = "barrio")
    private String barrio;
    @JoinColumn(name = "fk_ciudad", referencedColumnName = "id_ciudad")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Ciudad fkCiudad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkHuesped", fetch = FetchType.LAZY)
    private Collection<Reserva> reservaCollection;

    public Huesped() {
    }

    public Huesped(Integer idHuesped) {
        this.idHuesped = idHuesped;
    }

    public Huesped(Integer idHuesped, String documento, String nombre, String apellido, String correo, String direccion) {
        this.idHuesped = idHuesped;
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.direccion = direccion;
    }

    public Integer getIdHuesped() {
        return idHuesped;
    }

    public void setIdHuesped(Integer idHuesped) {
        this.idHuesped = idHuesped;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
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
        hash += (idHuesped != null ? idHuesped.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Huesped)) {
            return false;
        }
        Huesped other = (Huesped) object;
        if ((this.idHuesped == null && other.idHuesped != null) || (this.idHuesped != null && !this.idHuesped.equals(other.idHuesped))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hotel.model.Huesped[ idHuesped=" + idHuesped + " ]";
    }
    
}
