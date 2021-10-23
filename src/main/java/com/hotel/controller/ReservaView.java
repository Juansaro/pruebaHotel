/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.controller;

import com.hotel.ejb.EstadoReservaFacadeLocal;
import com.hotel.ejb.HabitacionFacadeLocal;
import com.hotel.ejb.HotelFacadeLocal;
import com.hotel.ejb.HuespedFacadeLocal;
import com.hotel.ejb.ReservaFacadeLocal;
import com.hotel.ejb.TipoHabitacionFacadeLocal;
import com.hotel.ejb.UsuarioFacadeLocal;
import com.hotel.model.EstadoReserva;
import com.hotel.model.Habitacion;
import com.hotel.model.Hotel;
import com.hotel.model.Huesped;
import com.hotel.model.Reserva;
import com.hotel.model.Usuario;
import com.hotel.utilidades.ReservaMail;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleDocxExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Named(value = "reservaView")
@ViewScoped
public class ReservaView implements Serializable {

    @EJB
    private ReservaFacadeLocal reservaFacadeLocal;

    @EJB
    private HuespedFacadeLocal huespedFacadeLocal;

    @EJB
    private HabitacionFacadeLocal habitacionFacadeLocal;

    @EJB
    private UsuarioFacadeLocal usuarioFacadeLocal;

    @EJB
    private HotelFacadeLocal hotelFacadeLocal;

    @EJB
    private EstadoReservaFacadeLocal estadoReservaFacadeLocal;

    @Inject
    private Huesped huesped;

    @Inject
    private Habitacion habitacion;

    @Inject
    private Usuario usuario;

    @Inject
    private EstadoReserva estadoReserva;

    @Inject
    private UsuarioSesion u;

    @Resource(lookup = "jdbc/hoteles")
    DataSource dataSource;

    private int fk_huesped;
    private int fk_habitacion;
    private int fk_usuario;
    private int fk_hotel;
    private int fk_estado;
    private boolean[] outs;
    private Part archivoCarga;
    private Date fechaFin;
    private Date fechaInicio;

    private List<Reserva> reservas;
    private List<Reserva> hueReservas;
    private List<Reserva> reservasEmpleados;
    private List<Habitacion> habitaciones;
    private List<Usuario> empleados;
    private List<Hotel> hoteles;
    private List<EstadoReserva> estados;
    private List<Reserva> listaUltimaFecha = new ArrayList<>();

    private Reserva resReg = new Reserva();
    private Reserva resTemporal = new Reserva();
    private Huesped hueIn = new Huesped();
    private Hotel hotIn = new Hotel();
    private Hotel hotTemporal = new Hotel();
    private Habitacion habIn = new Habitacion();

    @PostConstruct
    public void init() {
        reservas = reservaFacadeLocal.leerTodos();
        //Hacer filtro de empleados
        reservasEmpleados = reservaFacadeLocal.leerReservasEmpleado(u.getUsuLog());
        //habitaciones = habitacionFacadeLocal.findAll();

        empleados = usuarioFacadeLocal.findAll();
        hoteles = hotelFacadeLocal.findAll();
        estados = estadoReservaFacadeLocal.findAll();
    }

    public Date obtenerFechaActual() {
        try {
            DateTimeFormatter d = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            String fechaActual = d.format(LocalDateTime.now());
            SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date fechaFormateada = formato.parse(fechaActual);
            return fechaFormateada;
        } catch (ParseException e) {
            return null;
        }
    }

    public boolean validarReservaRepetida() {

        listaUltimaFecha.addAll(hueReservas);
        if (listaUltimaFecha != null && !listaUltimaFecha.isEmpty()) {
            Reserva item = listaUltimaFecha.get(listaUltimaFecha.size() - 1);
            Date registro = item.getFechaRegistro();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(registro);
            calendar.add(Calendar.MINUTE, 45);
            Date fechaSalida = calendar.getTime();
            calendar.getTime();
            return obtenerFechaActual().after(fechaSalida);
        } else {
            return true;
        }

    }

    public void guardarHotelTemporal(Hotel h) {
        hotTemporal = h;
        habitaciones = habitacionFacadeLocal.leerHabitacionesHotel(h);
    }

    public void registrarReserva() throws IOException {
        hueReservas = reservaFacadeLocal.leerReservasHuesped(huesped);
        resReg.setFechaRegistro(obtenerFechaActual());
        resReg.setPrecio(habitacionFacadeLocal.leerCostoHabitacion(habitacion.getIdHabitacion()));
        //Validación de rangos de citas
        boolean outEnt = reservaFacadeLocal.validarFechaEntrada(resReg.getFechaIngreso());
        boolean outFin = reservaFacadeLocal.validarFechaSalida(resReg.getFechaIngreso(), resReg.getFechaSalida());

        if (outEnt) {
            if (outFin) {
                if (reservaFacadeLocal.registrarReserva(resReg, huesped.getIdHuesped(), habitacion.getIdHabitacion(), u.getUsuLog().getDocumento(), hotTemporal.getIdHotel())) {
                    //Cambiar estado de la habitación
                    habitacionFacadeLocal.actualizarHabitacionReserva(habitacion.getIdHabitacion());
                    //Leer huesped para enviar al correo
                    hueIn = huespedFacadeLocal.leerHuesped(huesped.getIdHuesped());
                    //Leer tipo habitacion para enviar al correo
                    habIn = habitacionFacadeLocal.leerTipoHabitacion(habitacion.getIdHabitacion());
                    //Leer hotel para enviar el correo
                    hotIn = hotelFacadeLocal.leerHotel(hotTemporal.getIdHotel());

                    ReservaMail.correoReserva(
                            hueIn.getNombre(),
                            hueIn.getApellido(),
                            hueIn.getCorreo(),
                            hotIn.getNombre(),
                            habIn.getFkTipo().getDescripcion(),
                            resReg.getFechaIngreso(),
                            resReg.getFechaSalida(),
                            resReg.getPrecio()
                    );
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Reserva registrada", "Reserva registrada"));
                    limpiezaReserva();
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/pruebaHotel/faces/empleado/reserva.xhtml");
                } else {
                    limpiezaReserva();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de registro", "Error de registro"));
                }
            } else {
                limpiezaReserva();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de registro", "Error de registro"));
            }
        } else {
            limpiezaReserva();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de registro", "Error de registro"));
        }

    }

    public void limpiezaReserva() {
        resReg = new Reserva();
        huesped = new Huesped();
        hotTemporal = new Hotel();
        habitacion = new Habitacion();
        listaUltimaFecha = new ArrayList<>();
        hueReservas = new ArrayList<>();
        //Reservas por cada empleado
        reservasEmpleados = reservaFacadeLocal.leerReservasEmpleado(u.getUsuLog());
    }

    public void guardarTemporal(Reserva r) {
        resTemporal = r;
        fk_huesped = r.getFkHuesped().getIdHuesped();
        fk_habitacion = r.getFkHabitacion().getIdHabitacion();
        fk_usuario = u.getUsuLog().getDocumento();
        fk_hotel = r.getFkHotel().getIdHotel();
        fk_estado = r.getFkEstado().getIdEstadoReserva();
    }

    public void actualizarReserva() {
        try {
            reservaFacadeLocal.actualizarReserva(resTemporal, fk_estado);

            habitacionFacadeLocal.actualizarHabitacionReservaEliminada(resTemporal.getFkHabitacion().getIdHabitacion());
            reservasEmpleados = reservaFacadeLocal.leerReservasEmpleado(u.getUsuLog());
            reservas = reservaFacadeLocal.leerTodos();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Reserva editado", "Reserva editado"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de edición", "Error de edición"));
        }
    }

    public void eliminarReserva(Reserva r) {
        try {
            if (reservaFacadeLocal.cancelarReserva(r.getFechaIngreso())) {
                if (reservaFacadeLocal.eliminarReserva(r.getIdReserva())) {
                    int fk_habitaciones = r.getFkHabitacion().getIdHabitacion();
                    habitacionFacadeLocal.actualizarHabitacionReservaEliminada(fk_habitaciones);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Reserva eliminada", "Reserva eliminada"));
                    reservas = reservaFacadeLocal.leerTodos();
                    reservasEmpleados = reservaFacadeLocal.leerReservasEmpleado(u.getUsuLog());
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Debes cancelar con dos días de antelación!", "Debes cancelar con dos días de antelación!"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Debes eliminar con dos días de antelación", "Debes eliminar con dos días de antelación"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de eliminación", "Error de eliminación"));
        }
    }

    public void generarArchivo(String tipoArchivo) throws JRException, IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext context = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) context.getResponse();
        File jasper = new File(context.getRealPath("/reportes/estadisticoTipoHab.jasper"));
        try {
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), new HashMap(), dataSource.getConnection());
            switch (tipoArchivo) {
                case "pdf":
                    response.setContentType("application/pdf");
                    response.addHeader("Content-disposition", "attachment; filename=Lista servicios.pdf");
                    OutputStream os = response.getOutputStream();
                    JasperExportManager.exportReportToPdfStream(jp, os);
                    os.flush();
                    os.close();
                    break;

                case "xlsx":
                    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    response.addHeader("Content-disposition", "attachment; filename=Lista reservas.xlsx");

                    JRXlsxExporter exporter = new JRXlsxExporter(); // initialize exporter 
                    exporter.setExporterInput(new SimpleExporterInput(jp)); // set compiled report as input
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

                    SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
                    configuration.setOnePagePerSheet(true); // setup configuration
                    configuration.setDetectCellType(true);
                    configuration.setSheetNames(new String[]{"estadisticoTipoHab"});
                    exporter.setConfiguration(configuration); // set configuration    
                    exporter.exportReport();
                    break;

                case "docx":
                    response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                    response.addHeader("Content-disposition", "attachment; filename=Lista servicios.docx");

                    JRDocxExporter exporterDoc = new JRDocxExporter(); // initialize exporter 
                    exporterDoc.setExporterInput(new SimpleExporterInput(jp)); // set compiled report as input
                    exporterDoc.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

                    SimpleDocxExporterConfiguration configurationDoc = new SimpleDocxExporterConfiguration();
                    configurationDoc.setMetadataAuthor("Hoteles."); // setup configuration
                    configurationDoc.setMetadataTitle("Reporte de reservas");
                    configurationDoc.setMetadataSubject("Listado de reservas");

                    exporterDoc.setConfiguration(configurationDoc); // set configuration    
                    exporterDoc.exportReport();
                    break;

                default:
                    System.err.println(" No se encontro este caso :: CategoriaView::generarArchivo");
                    break;

            }
            facesContext.responseComplete();

        } catch (SQLException ex) {
            //Logger.getLogger(CategoriaView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void generarArchivoReservaFecha(String tipoArchivo) throws JRException, IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext context = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) context.getResponse();
        String nombreReporte = "";
        String nombreReporteDescarga = "";

        try {

            Map parametros = new HashMap();

            parametros.put("fechaInicio", fechaInicio);
            parametros.put("fechaFin", fechaFin);
            nombreReporte = "hueNoIngresados";

            File jasper = new File(context.getRealPath("/reportes/" + nombreReporte + ".jasper"));

            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametros, dataSource.getConnection());
            switch (tipoArchivo) {
                case "pdf":
                    response.setContentType("application/pdf");
                    response.addHeader("Content-disposition", "attachment; filename=" + nombreReporte + ".pdf");
                    OutputStream os = response.getOutputStream();
                    JasperExportManager.exportReportToPdfStream(jp, os);
                    os.flush();
                    os.close();
                    break;

                case "xlsx":
                    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    response.addHeader("Content-disposition", "attachment; filename=Lista usuarios.xlsx");

                    JRXlsxExporter exporter = new JRXlsxExporter(); // initialize exporter 
                    exporter.setExporterInput(new SimpleExporterInput(jp)); // set compiled report as input
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

                    SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
                    configuration.setOnePagePerSheet(true); // setup configuration
                    configuration.setDetectCellType(true);
                    configuration.setSheetNames(new String[]{"noIngreso"});
                    exporter.setConfiguration(configuration); // set configuration    
                    exporter.exportReport();
                    break;

                case "docx":
                    response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                    response.addHeader("Content-disposition", "attachment; filename=Lista usuarios.docx");

                    JRDocxExporter exporterDoc = new JRDocxExporter(); // initialize exporter 
                    exporterDoc.setExporterInput(new SimpleExporterInput(jp)); // set compiled report as input
                    exporterDoc.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

                    SimpleDocxExporterConfiguration configurationDoc = new SimpleDocxExporterConfiguration();
                    configurationDoc.setMetadataAuthor("Descanso y placer."); // setup configuration
                    configurationDoc.setMetadataTitle("Reporte de no ingresos");
                    configurationDoc.setMetadataSubject("Listado de reservas");

                    exporterDoc.setConfiguration(configurationDoc); // set configuration    
                    exporterDoc.exportReport();
                    break;

                default:
                    System.err.println(" No se encontro este caso :: CategoriaView::generarArchivo");
                    break;

            }
            facesContext.responseComplete();

        } catch (SQLException ex) {
            Logger.getLogger(ReservaView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<Reserva> reservasEmpleado() {
        return reservasEmpleados = reservaFacadeLocal.leerReservasEmpleado(u.getUsuLog());
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public List<Usuario> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Usuario> empleados) {
        this.empleados = empleados;
    }

    public List<Hotel> getHoteles() {
        return hoteles;
    }

    public void setHoteles(List<Hotel> hoteles) {
        this.hoteles = hoteles;
    }

    public List<EstadoReserva> getEstados() {
        return estados;
    }

    public void setEstados(List<EstadoReserva> estados) {
        this.estados = estados;
    }

    public Huesped getHuesped() {
        return huesped;
    }

    public void setHuesped(Huesped huesped) {
        this.huesped = huesped;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public EstadoReserva getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(EstadoReserva estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    public int getFk_huesped() {
        return fk_huesped;
    }

    public void setFk_huesped(int fk_huesped) {
        this.fk_huesped = fk_huesped;
    }

    public int getFk_habitacion() {
        return fk_habitacion;
    }

    public void setFk_habitacion(int fk_habitacion) {
        this.fk_habitacion = fk_habitacion;
    }

    public int getFk_usuario() {
        return fk_usuario;
    }

    public void setFk_usuario(int fk_usuario) {
        this.fk_usuario = fk_usuario;
    }

    public int getFk_hotel() {
        return fk_hotel;
    }

    public void setFk_hotel(int fk_hotel) {
        this.fk_hotel = fk_hotel;
    }

    public int getFk_estado() {
        return fk_estado;
    }

    public void setFk_estado(int fk_estado) {
        this.fk_estado = fk_estado;
    }

    public Reserva getResReg() {
        return resReg;
    }

    public void setResReg(Reserva resReg) {
        this.resReg = resReg;
    }

    public Reserva getResTemporal() {
        return resTemporal;
    }

    public void setResTemporal(Reserva resTemporal) {
        this.resTemporal = resTemporal;
    }

    public UsuarioSesion getU() {
        return u;
    }

    public void setU(UsuarioSesion u) {
        this.u = u;
    }

    public List<Reserva> getReservasEmpleados() {
        return reservasEmpleados;
    }

    public void setReservasEmpleados(List<Reserva> reservasEmpleados) {
        this.reservasEmpleados = reservasEmpleados;
    }

    public List<Reserva> getListaUltimaFecha() {
        return listaUltimaFecha;
    }

    public void setListaUltimaFecha(List<Reserva> listaUltimaFecha) {
        this.listaUltimaFecha = listaUltimaFecha;
    }

    public List<Reserva> getHueReservas() {
        return hueReservas;
    }

    public void setHueReservas(List<Reserva> hueReservas) {
        this.hueReservas = hueReservas;
    }

    public boolean[] getOuts() {
        return outs;
    }

    public void setOuts(boolean[] outs) {
        this.outs = outs;
    }

    public Hotel getHotTemporal() {
        return hotTemporal;
    }

    public void setHotTemporal(Hotel hotTemporal) {
        this.hotTemporal = hotTemporal;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

}
