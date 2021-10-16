/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.controller;

import com.hotel.ejb.EstadoReservaFacadeLocal;
import com.hotel.ejb.HabitacionFacadeLocal;
import com.hotel.ejb.HotelFacadeLocal;
import com.hotel.ejb.HuespedFacadeLocal;
import com.hotel.ejb.ReservaFacadeLocal;
import com.hotel.ejb.TipoHabitacionFacadeLocal;
import com.hotel.ejb.UsuarioFacadeLocal;
import com.hotel.model.EstadoReserva;
import com.hotel.model.Habitacion;
import com.hotel.model.Hotel;
import com.hotel.model.Huesped;
import com.hotel.model.Reserva;
import com.hotel.model.Usuario;
import com.hotel.utilidades.ReservaMail;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "reservaView")
@ViewScoped
public class ReservaView implements Serializable {

    @EJB
    private ReservaFacadeLocal reservaFacadeLocal;

    @EJB
    private HuespedFacadeLocal huespedFacadeLocal;

    @EJB
    private HabitacionFacadeLocal habitacionFacadeLocal;

    @EJB
    private UsuarioFacadeLocal usuarioFacadeLocal;

    @EJB
    private HotelFacadeLocal hotelFacadeLocal;

    @EJB
    private EstadoReservaFacadeLocal estadoReservaFacadeLocal;

    @Inject
    private Huesped huesped;

    @Inject
    private Habitacion habitacion;

    @Inject
    private Usuario usuario;

    @Inject
    private EstadoReserva estadoReserva;

    @Inject
    private UsuarioSesion u;

    private int fk_huesped;
    private int fk_habitacion;
    private int fk_usuario;
    private int fk_hotel;
    private int fk_estado;
    private boolean[] outs;

    private List<Reserva> reservas;
    private List<Reserva> hueReservas;
    private List<Reserva> reservasEmpleados;
    private List<Habitacion> habitaciones;
    private List<Usuario> empleados;
    private List<Hotel> hoteles;
    private List<EstadoReserva> estados;
    private List<Reserva> listaUltimaFecha = new ArrayList<>();

    private Reserva resReg = new Reserva();
    private Reserva resTemporal = new Reserva();
    private Huesped hueIn = new Huesped();
    private Hotel hotIn = new Hotel();
    private Habitacion habIn = new Habitacion();

    @PostConstruct
    public void init() {
        reservas = reservaFacadeLocal.leerTodos();
        reservasEmpleados = reservaFacadeLocal.leerReservasEmpleado(u.getUsuLog());
        habitaciones = habitacionFacadeLocal.findAll();
        //Hacer filtro de empleados
        empleados = usuarioFacadeLocal.findAll();
        hoteles = hotelFacadeLocal.findAll();
        estados = estadoReservaFacadeLocal.findAll();
    }

    public Date obtenerFechaActual() {
        try {
            DateTimeFormatter d = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            String fechaActual = d.format(LocalDateTime.now());
            SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date fechaFormateada = formato.parse(fechaActual);
            return fechaFormateada;
        } catch (ParseException e) {
            return null;
        }
    }

    public boolean validarReservaRepetida() {

        listaUltimaFecha.addAll(hueReservas);
        if (listaUltimaFecha != null && !listaUltimaFecha.isEmpty()) {
            Reserva item = listaUltimaFecha.get(listaUltimaFecha.size() - 1);
            Date registro = item.getFechaRegistro();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(registro);
            calendar.add(Calendar.MINUTE, 45);
            Date fechaSalida = calendar.getTime();
            calendar.getTime();
            return obtenerFechaActual().after(fechaSalida);
        } else {
            return true;
        }
    }

    public boolean validarReservaEdicion() {

        listaUltimaFecha.addAll(hueReservas);
        if (listaUltimaFecha != null && !listaUltimaFecha.isEmpty()) {
            Reserva item = listaUltimaFecha.get(listaUltimaFecha.size() - 1);
            Date registro = item.getFechaRegistro();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(registro);
            calendar.add(Calendar.MINUTE, 45);
            Date fechaSalida = calendar.getTime();
            calendar.getTime();
            return obtenerFechaActual().after(fechaSalida);
        } else {
            return true;
        }
    }

    public void registrarReserva() throws IOException {
        hueReservas = reservaFacadeLocal.leerReservasHuesped(huesped);
        resReg.setFechaRegistro(obtenerFechaActual());
        resReg.setPrecio(habitacionFacadeLocal.leerCostoHabitacion(fk_habitacion));
        boolean outEnt = reservaFacadeLocal.validarFechaEntrada(resReg.getFechaIngreso());
        boolean outFin = reservaFacadeLocal.validarFechaSalida(resReg.getFechaIngreso(), resReg.getFechaSalida());

        if (validarReservaRepetida()) {
            if (outEnt) {
                if (outFin) {
                    if (reservaFacadeLocal.registrarReserva(resReg, huesped.getIdHuesped(), fk_habitacion, u.getUsuLog().getDocumento(), fk_hotel)
                            && habitacionFacadeLocal.actualizarHabitacionReserva(fk_habitacion)) {

                        hueIn = huespedFacadeLocal.leerHuesped(huesped.getIdHuesped());
                        hotIn = hotelFacadeLocal.leerHotel(fk_hotel);
                        habIn = habitacionFacadeLocal.leerTipoHabitacion(fk_habitacion);
                        ReservaMail.correoReserva(
                                hueIn.getNombre(),
                                hueIn.getApellido(),
                                hueIn.getCorreo(),
                                hotIn.getNombre(),
                                habIn.getFkTipo().getDescripcion(),
                                resReg.getFechaIngreso(),
                                resReg.getFechaSalida(),
                                resReg.getPrecio()
                        );
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Reserva registrada", "Reserva registrada"));
                        resReg = new Reserva();
                        huesped = new Huesped();
                        reservasEmpleados = reservaFacadeLocal.leerReservasEmpleado(u.getUsuLog());
                        FacesContext.getCurrentInstance().getExternalContext().redirect("/pruebaHotel/faces/empleado/reserva.xhtml");
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de registro", "Error de registro"));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de registro", "Error de registro"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de registro", "Error de registro"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Acabaste de reservar un huesped, espera 45 min", "Acabaste de reservar un huesped, espera 45 min"));
        }

    }

    public void guardarTemporal(Reserva r) {
        resTemporal = r;
        fk_huesped = r.getFkHuesped().getIdHuesped();
        fk_habitacion = r.getFkHabitacion().getIdHabitacion();
        fk_usuario = u.getUsuLog().getDocumento();
        fk_hotel = r.getFkHotel().getIdHotel();
        fk_estado = r.getFkEstado().getIdEstadoReserva();
    }

    public void actualizarReserva() {
        try {
            reservaFacadeLocal.actualizarReserva(resTemporal, fk_estado);
            switch (fk_estado) {
                case 2:
                    habitacionFacadeLocal.actualizarHabitacionReserva(fk_habitacion);
                    reservasEmpleados = reservaFacadeLocal.leerReservasEmpleado(u.getUsuLog());
                    reservas = reservaFacadeLocal.leerTodos();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Reserva editado", "Reserva editado"));
                    break;
                case 3:
                    habitacionFacadeLocal.actualizarHabitacionReserva(fk_habitacion);
                    reservas = reservaFacadeLocal.leerTodos();
                    reservasEmpleados = reservaFacadeLocal.leerReservasEmpleado(u.getUsuLog());
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Reserva editado", "Reserva editado"));
                    break;
                default:
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Siges en el mismo estado", "Sigues en el mismo estado"));
                    break;
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de edición", "Error de edición"));
        }
    }

    public void eliminarReserva(Reserva r) {
        try {
            if (reservaFacadeLocal.eliminarReserva(r.getIdReserva())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Reserva eliminada", "Reserva eliminada"));
                reservas = reservaFacadeLocal.leerTodos();
                reservasEmpleados = reservaFacadeLocal.leerReservasEmpleado(u.getUsuLog());
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de eliminación", "Error de eliminación"));
            }
        } catch (Exception e) {
        }
    }

    public List<Reserva> reservasEmpleado() {
        return reservasEmpleados = reservaFacadeLocal.leerReservasEmpleado(u.getUsuLog());
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public List<Usuario> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Usuario> empleados) {
        this.empleados = empleados;
    }

    public List<Hotel> getHoteles() {
        return hoteles;
    }

    public void setHoteles(List<Hotel> hoteles) {
        this.hoteles = hoteles;
    }

    public List<EstadoReserva> getEstados() {
        return estados;
    }

    public void setEstados(List<EstadoReserva> estados) {
        this.estados = estados;
    }

    public Huesped getHuesped() {
        return huesped;
    }

    public void setHuesped(Huesped huesped) {
        this.huesped = huesped;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public EstadoReserva getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(EstadoReserva estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    public int getFk_huesped() {
        return fk_huesped;
    }

    public void setFk_huesped(int fk_huesped) {
        this.fk_huesped = fk_huesped;
    }

    public int getFk_habitacion() {
        return fk_habitacion;
    }

    public void setFk_habitacion(int fk_habitacion) {
        this.fk_habitacion = fk_habitacion;
    }

    public int getFk_usuario() {
        return fk_usuario;
    }

    public void setFk_usuario(int fk_usuario) {
        this.fk_usuario = fk_usuario;
    }

    public int getFk_hotel() {
        return fk_hotel;
    }

    public void setFk_hotel(int fk_hotel) {
        this.fk_hotel = fk_hotel;
    }

    public int getFk_estado() {
        return fk_estado;
    }

    public void setFk_estado(int fk_estado) {
        this.fk_estado = fk_estado;
    }

    public Reserva getResReg() {
        return resReg;
    }

    public void setResReg(Reserva resReg) {
        this.resReg = resReg;
    }

    public Reserva getResTemporal() {
        return resTemporal;
    }

    public void setResTemporal(Reserva resTemporal) {
        this.resTemporal = resTemporal;
    }

    public UsuarioSesion getU() {
        return u;
    }

    public void setU(UsuarioSesion u) {
        this.u = u;
    }

    public List<Reserva> getReservasEmpleados() {
        return reservasEmpleados;
    }

    public void setReservasEmpleados(List<Reserva> reservasEmpleados) {
        this.reservasEmpleados = reservasEmpleados;
    }

    public List<Reserva> getListaUltimaFecha() {
        return listaUltimaFecha;
    }

    public void setListaUltimaFecha(List<Reserva> listaUltimaFecha) {
        this.listaUltimaFecha = listaUltimaFecha;
    }

    public List<Reserva> getHueReservas() {
        return hueReservas;
    }

    public void setHueReservas(List<Reserva> hueReservas) {
        this.hueReservas = hueReservas;
    }

    public boolean[] getOuts() {
        return outs;
    }

    public void setOuts(boolean[] outs) {
        this.outs = outs;
    }

}
