/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.ejb;

import com.hotel.model.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    @PersistenceContext(unitName = "pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    @Override
    public boolean registrarUsuario(Usuario usuIn, int rolIn) {
        try {
            Query qr = em.createNativeQuery("INSERT INTO usuario (nombre, apellido, correo, contrasena, fk_rol) VALUES (?, ?, ?, ?, ?)");
            qr.setParameter(1, usuIn.getNombre());
            qr.setParameter(2, usuIn.getApellido());
            qr.setParameter(3, usuIn.getCorreo());
            qr.setParameter(4, usuIn.getContrasena());
            qr.setParameter(5, rolIn);
            qr.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public Usuario validarUsuario(String correoIn, String claveIn) {

        try {
            em.getEntityManagerFactory().getCache().evictAll();
            Query qt = em.createQuery("SELECT u FROM Usuario u WHERE u.correo = :correoIn AND u.contrasena = :claveIn");
            qt.setParameter("correoIn", correoIn);
            qt.setParameter("claveIn", claveIn);
            return (Usuario) qt.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }
    
    @Override
    public Usuario encontrarUsuarioCorreo(String correo){
        Query q = em.createNamedQuery("Usuario.findByCorreo", Usuario.class).setParameter("correo", correo);
        
        List<Usuario> listado = q.getResultList();
        
        if(!listado.isEmpty()){
            return listado.get(0);
        }
        return null;
    }

    @Override
    public boolean actualizarUsuario(Usuario usuIn, int rolIn) {
        try {
            Query qr = em.createNativeQuery("UPDATE usuario SET nombre = ?, apellido = ?, correo = ?, contrasena = ?, fk_rol = ? WHERE (id_usuario = ?)");
            qr.setParameter(1, usuIn.getNombre());
            qr.setParameter(2, usuIn.getApellido());
            qr.setParameter(3, usuIn.getCorreo());
            qr.setParameter(4, usuIn.getContrasena());
            qr.setParameter(5, rolIn);
            qr.setParameter(6, usuIn.getIdUsuario());
            qr.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

}
