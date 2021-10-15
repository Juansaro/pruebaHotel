/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.controller;

import com.hotel.ejb.RolFacadeLocal;
import com.hotel.ejb.UsuarioFacadeLocal;
import com.hotel.model.Rol;
import com.hotel.model.Usuario;
import com.hotel.utilidades.usuClaveMail;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import static org.passay.AllowedCharacterRule.ERROR_CODE;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

@Named(value = "usuarioSesion")
@SessionScoped
public class UsuarioSesion implements Serializable {

    @EJB
    private UsuarioFacadeLocal usuarioFacadeLocal;
    @EJB
    private RolFacadeLocal rolFacadeLocal;

    @Inject
    private Rol rol;

    private int fk_rol;
    private int docIn;
    private String claveIn;

    private List<Usuario> usuarios;
    private List<Rol> roles;

    private Usuario usuReg = new Usuario();
    private Usuario usuLog = new Usuario();
    private Usuario usuTemporal = new Usuario();

    @PostConstruct
    public void init() {
        usuarios = usuarioFacadeLocal.findAll();
        roles = rolFacadeLocal.findAll();
    }

    public void validarUsuario() {
        try {
            usuLog = usuarioFacadeLocal.encontrarUsuarioDocumento(docIn);
            if (usuLog != null) {
                if (usuLog.getDocumento().equals(docIn)) {
                    if (usuLog.getContrasena().equals(claveIn)) {
                        switch (usuLog.getFkRol().toString()) {
                            case "Administrador": {
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.getExternalContext().redirect("administrador/index.xhtml");
                                break;
                            }
                            case "Empleado": {
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.getExternalContext().redirect("empleado/index.xhtml");
                                break;
                            }
                            default:
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "No exite", "No existe"));
                                break;
                        }
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Correo o clave incorrectos", "Correo o clave incorrectos"));
                    }
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Correo o clave incorrectos", "Correo o clave incorrectos"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de registro", "Error de registro"));
            }

        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de registro", "Error de registro"));
        }

    }

    public void validarUsuarioSesion() throws IOException {

        if (usuLog == null || usuLog.getCorreo() == null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().invalidateSession();
            fc.getExternalContext().redirect("../index.xhtml");
        }
    }

    public void cerrarSesion() throws IOException {
        usuLog = null;
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.getExternalContext().invalidateSession();
        fc.getExternalContext().redirect("../index.xhtml");
    }

    public void cambiarEstado(Usuario usuIn) {
        if (usuIn.getEstado() == Short.parseShort("0")) {
            usuIn.setEstado(Short.parseShort("1"));
        } else {
            usuIn.setEstado(Short.parseShort("0"));
        }
        usuarioFacadeLocal.edit(usuIn);
    }

    public void registrarUsuario() {
        usuReg.setContrasena(generatePassayPassword());
        if (usuarioFacadeLocal.registrarUsuario(usuReg, fk_rol)) {
            usuClaveMail.correoReserva(
                    usuReg.getNombre(),
                    usuReg.getApellido(),
                    usuReg.getCorreo(),
                    usuReg.getDocumento(),
                    usuReg.getContrasena()
            );
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario registrado", "Usuario registrado"));
            usuReg = new Usuario();
            usuarios = usuarioFacadeLocal.findAll();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de registro", "Error de registro"));
        }
    }

    public void guardarTemporal(Usuario u) {
        usuTemporal = u;
        fk_rol = u.getFkRol().getIdRol();
    }

    public void actualizarUsuario() {
        try {
            usuarioFacadeLocal.actualizarUsuario(usuTemporal, fk_rol);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario editado", "Usuario editado"));
            usuTemporal = new Usuario();
            usuarios = usuarioFacadeLocal.findAll();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de edición", "Error de edición"));
        }
    }

    public void eliminarUsuario(Usuario u) {
        try {
            if (usuarioFacadeLocal.eliminarUsuario(u)) {
                usuarios = usuarioFacadeLocal.findAll();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario eliminado", "Usuario eliminado"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de eliminación", "Error de eliminación"));
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de eliminación", "Error de eliminación"));
        }
    }

    public String generatePassayPassword() {
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        CharacterData specialChars = new CharacterData() {
            @Override
            public String getErrorCode() {
                return ERROR_CODE;
            }

            @Override
            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(2);

        String password = gen.generatePassword(10, splCharRule, lowerCaseRule,
                upperCaseRule, digitRule);
        return password;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public Usuario getUsuReg() {
        return usuReg;
    }

    public void setUsuReg(Usuario usuReg) {
        this.usuReg = usuReg;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Usuario getUsuLog() {
        return usuLog;
    }

    public void setUsuLog(Usuario usuLog) {
        this.usuLog = usuLog;
    }

    public int getFk_rol() {
        return fk_rol;
    }

    public void setFk_rol(int fk_rol) {
        this.fk_rol = fk_rol;
    }

    public Usuario getUsuTemporal() {
        return usuTemporal;
    }

    public void setUsuTemporal(Usuario usuTemporal) {
        this.usuTemporal = usuTemporal;
    }

    public String getClaveIn() {
        return claveIn;
    }

    public void setClaveIn(String claveIn) {
        this.claveIn = claveIn;
    }

    public int getDocIn() {
        return docIn;
    }

    public void setDocIn(int docIn) {
        this.docIn = docIn;
    }

}
