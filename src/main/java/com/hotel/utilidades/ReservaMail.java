/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.utilidades;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public abstract class ReservaMail {

    public static void correoReserva(String nombrePara, String apellidoPara, String correoPara, String hotelPara, String tipoHabitacionPara, Date fechaReservaPara, Date fechaFinalPara, float costoPara) {
        final String usuario = "";
        final String clave = "";

        Properties props = new Properties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com"); // envia 
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.starttls.required", "false");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, clave);
            }
        });

        try {
            MimeMessage mensage = new MimeMessage(session);
            mensage.setFrom(new InternetAddress(usuario));
            mensage.addRecipient(Message.RecipientType.TO, new InternetAddress(correoPara));
            mensage.setSubject("Hola " +nombrePara + " hemos registrado tu reserva en hotel " + hotelPara);
            mensage.setContent("<center> "
                    + "</center>"
                    + "<br/>"
                    + "<h1> Hola, " + nombrePara + " " + apellidoPara + " </h1>"
                    + "Hemos registrado tu reserva para el día: " + fechaReservaPara + " en el hotel " + hotelPara
                    + "<br/> Recuerda que la reserva termina el día: " + fechaFinalPara
                    + "<br/> El tipo de habitación es: " + tipoHabitacionPara + "."
                    + "<br/> El costo de tu reserva es: " +costoPara
                    + "<br/>",
                    "text/html");
            Transport.send(mensage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
            
        }

    }

}

