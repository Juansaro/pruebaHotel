/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.controller;

import com.hotel.ejb.CiudadFacadeLocal;
import com.hotel.ejb.HotelFacadeLocal;
import com.hotel.model.Ciudad;
import com.hotel.model.Hotel;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.shaded.commons.io.FilenameUtils;

@Named(value = "hotelView")
@ViewScoped
public class HotelView implements Serializable{
    
    @EJB
    private HotelFacadeLocal hotelFacadeLocal;
    
    @EJB
    private CiudadFacadeLocal ciudadFacadeLocal;
    
    @Inject
    private Ciudad ciudad;
    
    private int fk_ciudad;
    
    private List<Hotel> hoteles;
    private List<Ciudad> ciudades;
    
    private Hotel hotelReg = new Hotel();
    private Hotel hotelTemporal = new Hotel();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    
    private Part archivoFoto;
    
    @PostConstruct
    private void init(){
        hoteles = hotelFacadeLocal.leerTodos();
        ciudades = ciudadFacadeLocal.findAll();
    }
    
    public void registrarHotel(){
        if(hotelFacadeLocal.registrarHotel(hotelReg, fk_ciudad)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Hotel registrado", "Hotel registrado"));
            hotelReg = new Hotel();
            hoteles = hotelFacadeLocal.leerTodos();
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de registro", "Error de registro"));
        }
    }
    
    public void guardarTemporal(Hotel h){
        hotelTemporal = h;
        fk_ciudad = h.getFkCiudad().getIdCiudad();
    }
    
    public void actualizarHotel(){
        try{
            hotelFacadeLocal.actualizarHotel(hotelTemporal, fk_ciudad);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Hotel editado", "Hotel editado"));
            hotelTemporal = new Hotel();
            hoteles = hotelFacadeLocal.leerTodos();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de edición", "Error de edición"));
        }
    }
    
    public void eliminarHotel(Hotel h){
        try {
            if (hotelFacadeLocal.eliminarHotel(h.getIdHotel())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Hotel eliminado", "Hotel eliminado"));
                hoteles = hotelFacadeLocal.leerTodos();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de eliminación", "Error de eliminación"));
            }
        } catch (Exception e) {
        }
    }
    
    public void cargarFotoHotel() {
        if (archivoFoto != null) {
            if (archivoFoto.getSize() > 700000 || archivoFoto.getSize() < 10000) {
                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'El archivo !',"
                        + "  text: 'No se puede cargar por el tamaño !!!',"
                        + "  icon: 'error',"
                        + "  confirmButtonText: 'Ok'"
                        + "})");
            } else if (archivoFoto.getContentType().equalsIgnoreCase("image/png") || archivoFoto.getContentType().equalsIgnoreCase("image/jpeg")) {

                try (InputStream is = archivoFoto.getInputStream()) {
                    File carpeta = new File("C:\\descansoPlacer\\hoteles");
                    if (!carpeta.exists()) {
                        carpeta.mkdirs();
                    }
                    Calendar hoy = Calendar.getInstance();
                    String nuevoNombre = sdf.format(hoy.getTime()) + ".";
                    nuevoNombre += FilenameUtils.getExtension(archivoFoto.getSubmittedFileName());
                    Files.copy(is, (new File(carpeta, nuevoNombre)).toPath(), StandardCopyOption.REPLACE_EXISTING);
                    hotelTemporal.setHotelFoto(nuevoNombre);
                    hotelFacadeLocal.edit(hotelTemporal);

                } catch (Exception e) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de componente", "Error de componente"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Revisa el peso de la imagen", "Revisa el peso de la imagen"));
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de registro", "Error de registro"));
        }

        PrimeFaces.current().executeScript("document.getElementById('resetform').click()");

    }


    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public List<Hotel> getHoteles() {
        return hoteles;
    }

    public void setHoteles(List<Hotel> hoteles) {
        this.hoteles = hoteles;
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }

    public Hotel getHotelReg() {
        return hotelReg;
    }

    public void setHotelReg(Hotel hotelReg) {
        this.hotelReg = hotelReg;
    }

    public Hotel getHotelTemporal() {
        return hotelTemporal;
    }

    public void setHotelTemporal(Hotel hotelTemporal) {
        this.hotelTemporal = hotelTemporal;
    }

    public int getFk_ciudad() {
        return fk_ciudad;
    }

    public void setFk_ciudad(int fk_ciudad) {
        this.fk_ciudad = fk_ciudad;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public Part getArchivoFoto() {
        return archivoFoto;
    }

    public void setArchivoFoto(Part archivoFoto) {
        this.archivoFoto = archivoFoto;
    }
    
}
