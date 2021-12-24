package com.hotel.model;

import com.hotel.model.EstadoReserva;
import com.hotel.model.Habitacion;
import com.hotel.model.Hotel;
import com.hotel.model.Huesped;
import com.hotel.model.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-12-04T18:16:35")
@StaticMetamodel(Reserva.class)
public class Reserva_ { 

    public static volatile SingularAttribute<Reserva, EstadoReserva> fkEstado;
    public static volatile SingularAttribute<Reserva, Date> fechaIngreso;
    public static volatile SingularAttribute<Reserva, Huesped> fkHuesped;
    public static volatile SingularAttribute<Reserva, Float> precio;
    public static volatile SingularAttribute<Reserva, Hotel> fkHotel;
    public static volatile SingularAttribute<Reserva, Usuario> fkEmpleado;
    public static volatile SingularAttribute<Reserva, Date> fechaRegistro;
    public static volatile SingularAttribute<Reserva, Date> fechaSalida;
    public static volatile SingularAttribute<Reserva, Habitacion> fkHabitacion;
    public static volatile SingularAttribute<Reserva, Integer> idReserva;

}