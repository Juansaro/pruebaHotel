package com.hotel.model;

import com.hotel.model.Hotel;
import com.hotel.model.Huesped;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-10-14T17:08:12")
@StaticMetamodel(Ciudad.class)
public class Ciudad_ { 

    public static volatile SingularAttribute<Ciudad, String> descripcion;
    public static volatile CollectionAttribute<Ciudad, Huesped> huespedCollection;
    public static volatile CollectionAttribute<Ciudad, Hotel> hotelCollection;
    public static volatile SingularAttribute<Ciudad, Integer> idCiudad;

}