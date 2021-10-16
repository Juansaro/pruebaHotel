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
            Query qr = em.createNativeQuery("INSERT INTO usuario (nombre, apellido, correo, contrasena, fk_rol, documento, estado) VALUES (?, ?, ?, ?, ?, ?, ?)");
            qr.setParameter(1, usuIn.getNombre());
            qr.setParameter(2, usuIn.getApellido());
            qr.setParameter(3, usuIn.getCorreo());
            qr.setParameter(4, usuIn.getContrasena());
            qr.setParameter(5, rolIn);
            qr.setParameter(6, usuIn.getDocumento());
            qr.setParameter(7, Short.parseShort("1"));
            qr.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public Usuario validarUsuario(int docIn, String claveIn) {

        try {
            em.getEntityManagerFactory().getCache().evictAll();
            Query qt = em.createQuery("SELECT u FROM Usuario u WHERE u.documento = :docIn AND u.contrasena = :claveIn");
            qt.setParameter("docIn", docIn);
            qt.setParameter("claveIn", claveIn);
            return (Usuario) qt.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }
    
    @Override
    public Usuario encontrarUsuarioDocumento(int documento){
        Query q = em.createNamedQuery("Usuario.findByDocumento", Usuario.class).setParameter("documento", documento);
        
        List<Usuario> listado = q.getResultList();
        
        if(!listado.isEmpty()){
            return listado.get(0);
        }
        return null;
    }

    @Override
    public boolean actualizarUsuario(Usuario usuIn, int rolIn) {
        try {
            Query qr = em.createNativeQuery("UPDATE usuario SET  nombre = ?, apellido = ?, correo = ?, contrasena = ?, fk_rol = ? WHERE (documento = ?)");
            qr.setParameter(1, usuIn.getNombre());
            qr.setParameter(2, usuIn.getApellido());
            qr.setParameter(3, usuIn.getCorreo());
            qr.setParameter(4, usuIn.getContrasena());
            qr.setParameter(5, rolIn);
            qr.setParameter(6, usuIn.getDocumento());
            qr.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public boolean eliminarUsuario(Usuario usuIn) {
        try {
            Query qr = em.createNativeQuery("DELETE FROM usuario WHERE (documento =  ?)");
            qr.setParameter(1, usuIn.getDocumento());
            qr.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    
    @Override
    public List<Usuario> leerEmpleado(){
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.fkRol.idRol = 2");
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<Usuario> leerTodos(){
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            Query q = em.createQuery("SELECT u FROM Usuario u");
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
