package com.hotel.model;

import com.hotel.model.Hotel;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-10-08T17:10:27")
@StaticMetamodel(Telefonos.class)
public class Telefonos_ { 

    public static volatile SingularAttribute<Telefonos, Integer> idTelefonos;
    public static volatile SingularAttribute<Telefonos, Hotel> fkHotel;
    public static volatile SingularAttribute<Telefonos, String> numero;

}