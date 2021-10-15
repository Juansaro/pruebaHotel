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
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public abstract class envioMasivoHoteles {

    public static void recuperarCliente(List<String> correos) {

        // from email address
        final String username = "senaland066@gmail.com";

        // make sure you put your correct password
        final String password = "sennaland 432";

        Properties props = new Properties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com"); // envia 
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", "25");
        props.setProperty("mail.smtp.starttls.required", "false");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");

        // authentcate using your email and password and on successful
        // create the session
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        String emails = null;

        try {
            // create new message
            Message message = new MimeMessage(session);

            // set the from 'email address'
            message.setFrom(new InternetAddress(username));

            // set email subject
            message.setSubject(correos.get(0));

            // set email message
            // this will send html mail to the intended recipients
            // if you do not want to send html mail then you do not need to wrap the message
            // inside html tags
            String content = "<html>\n<body>\n";
            content += "";
            content += "<br/>";
            content += "<h2> Hola, Recuerda que en Descanso y placer tenemos los mejores habitaciones para ti!</h2>";
            content += "<br/>";content += "<br/>";
            content += "Puedes alquilar entre una habitación normal o una suite!.";
            content += "<br/>";
            content += "Recuerda hablar con un empleado para que registre tu reserva";
            //Recordar servicios a los clientes en general
            content += "<br/>";
            content += "Puedes reservar cuando quieras, visitanos!.";
            content += "\n";
            content += "</body>\n</html>";
            message.setContent(content, "text/html");

            StringBuilder sb = new StringBuilder();

            int i = 0;
            for (String email : correos) {
                sb.append(email);
                i++;
                if (correos.size() > i) {
                    sb.append(", ");
                }
            }

            emails = sb.toString();

            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(sb.toString()));

            // send the email
            Transport.send(message);

        } catch (MessagingException e) {
            System.out.println(e);
        }
    }

}
