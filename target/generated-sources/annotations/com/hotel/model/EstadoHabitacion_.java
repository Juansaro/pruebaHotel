package com.hotel.model;

import com.hotel.model.Habitacion;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-10-14T17:08:12")
@StaticMetamodel(EstadoHabitacion.class)
public class EstadoHabitacion_ { 

    public static volatile SingularAttribute<EstadoHabitacion, String> descripcion;
    public static volatile SingularAttribute<EstadoHabitacion, Integer> idEstado;
    public static volatile CollectionAttribute<EstadoHabitacion, Habitacion> habitacionCollection;

}