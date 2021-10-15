package com.hotel.model;

import com.hotel.model.Hotel;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-10-15T14:17:58")
@StaticMetamodel(Telefono.class)
public class Telefono_ { 

    public static volatile SingularAttribute<Telefono, Integer> idTelefono;
    public static volatile SingularAttribute<Telefono, String> numero;
    public static volatile SingularAttribute<Telefono, Hotel> hotelIdHotel;

}