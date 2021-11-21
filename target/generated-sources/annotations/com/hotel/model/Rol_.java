package com.hotel.model;

import com.hotel.model.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-11-03T20:40:37")
@StaticMetamodel(Rol.class)
public class Rol_ { 

    public static volatile SingularAttribute<Rol, String> descripcion;
    public static volatile SingularAttribute<Rol, Integer> idRol;
    public static volatile CollectionAttribute<Rol, Usuario> usuarioCollection;

}