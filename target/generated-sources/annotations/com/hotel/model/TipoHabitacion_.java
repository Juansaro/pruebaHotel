package com.hotel.model;

import com.hotel.model.Habitacion;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-10-11T12:24:01")
@StaticMetamodel(TipoHabitacion.class)
public class TipoHabitacion_ { 

    public static volatile SingularAttribute<TipoHabitacion, String> descripcion;
    public static volatile CollectionAttribute<TipoHabitacion, Habitacion> habitacionCollection;
    public static volatile SingularAttribute<TipoHabitacion, Integer> idTipoHabitacion;

}