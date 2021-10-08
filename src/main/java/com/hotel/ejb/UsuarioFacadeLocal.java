/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.ejb;

import com.hotel.model.Rol;
import com.hotel.model.Usuario;
import java.util.List;
import javax.ejb.Local;


@Local
public interface UsuarioFacadeLocal {

    void create(Usuario usuario);

    void edit(Usuario usuario);

    void remove(Usuario usuario);

    Usuario find(Object id);

    List<Usuario> findAll();

    List<Usuario> findRange(int[] range);

    int count();

    public boolean registrarUsuario(Usuario usuIn, int rolIn);

    public Usuario validarUsuario(String correoIn, String claveIn);
    
}
