package com.hotel.model;

import com.hotel.model.Ciudad;
import com.hotel.model.Habitacion;
import com.hotel.model.Reserva;
import com.hotel.model.Telefono;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-10-14T17:08:12")
@StaticMetamodel(Hotel.class)
public class Hotel_ { 

    public static volatile SingularAttribute<Hotel, Integer> idHotel;
    public static volatile CollectionAttribute<Hotel, Habitacion> habitacionCollection;
    public static volatile SingularAttribute<Hotel, String> hotelFoto;
    public static volatile SingularAttribute<Hotel, String> direccion;
    public static volatile SingularAttribute<Hotel, Ciudad> fkCiudad;
    public static volatile CollectionAttribute<Hotel, Reserva> reservaCollection;
    public static volatile SingularAttribute<Hotel, String> nombre;
    public static volatile CollectionAttribute<Hotel, Telefono> telefonoCollection;

}