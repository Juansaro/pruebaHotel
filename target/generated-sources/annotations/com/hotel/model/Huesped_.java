package com.hotel.model;

import com.hotel.model.Ciudad;
import com.hotel.model.Reserva;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-10-18T21:46:24")
@StaticMetamodel(Huesped.class)
public class Huesped_ { 

    public static volatile SingularAttribute<Huesped, String> barrio;
    public static volatile SingularAttribute<Huesped, String> apellido;
    public static volatile SingularAttribute<Huesped, String> correo;
    public static volatile SingularAttribute<Huesped, String> direccion;
    public static volatile SingularAttribute<Huesped, String> documento;
    public static volatile SingularAttribute<Huesped, Integer> idHuesped;
    public static volatile SingularAttribute<Huesped, Ciudad> ciudadIdCiudad;
    public static volatile CollectionAttribute<Huesped, Reserva> reservaCollection;
    public static volatile SingularAttribute<Huesped, String> nombre;

}