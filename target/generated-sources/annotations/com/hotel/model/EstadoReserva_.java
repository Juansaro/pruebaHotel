package com.hotel.model;

import com.hotel.model.Reserva;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-10-09T10:54:27")
@StaticMetamodel(EstadoReserva.class)
public class EstadoReserva_ { 

    public static volatile SingularAttribute<EstadoReserva, String> descripcion;
    public static volatile SingularAttribute<EstadoReserva, Integer> idEstadoReserva;
    public static volatile CollectionAttribute<EstadoReserva, Reserva> reservaCollection;

}