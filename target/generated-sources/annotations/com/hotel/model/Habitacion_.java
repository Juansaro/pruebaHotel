package com.hotel.model;

import com.hotel.model.Hotel;
import com.hotel.model.Reserva;
import com.hotel.model.TipoHabitacion;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-10-09T10:54:27")
@StaticMetamodel(Habitacion.class)
public class Habitacion_ { 

    public static volatile SingularAttribute<Habitacion, Short> estado;
    public static volatile SingularAttribute<Habitacion, Integer> numeroHabitacion;
    public static volatile CollectionAttribute<Habitacion, Hotel> hotelCollection;
    public static volatile SingularAttribute<Habitacion, TipoHabitacion> tipoHabitacionIdTipoHabitacion;
    public static volatile SingularAttribute<Habitacion, String> habitacionFoto;
    public static volatile SingularAttribute<Habitacion, Short> calefaccion;
    public static volatile CollectionAttribute<Habitacion, Reserva> reservaCollection;
    public static volatile SingularAttribute<Habitacion, Short> telefono;
    public static volatile SingularAttribute<Habitacion, Short> bano;

}