package com.example.demo.util;

import java.sql.SQLException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.example.demo.DAO.procedure.CallerClient;
import com.example.demo.entity.Login;

public class SendEmail {
  
  public String sendBlockEmail(String myEmail) {
    Login login = null;
    String uid = null;
    try {
      CallerClient callerClient = new CallerClient();
      login = callerClient.getLoginInit(myEmail);
      uid = callerClient.getUID(myEmail);
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
    
    // Debemos de obtener el user y la password del email
    String usery = login.getUser(), password = login.getPassword();
    System.out.println(usery+" "+password);
    // Es necesario mencionar el ID de correo electrónico del destinatario.
    String to = "anothercountry123456@gmail.com";// this.myEmail;
    // Se debe mencionar el ID de correo electrónico del remitente
    String from = "pruebacorreos1255@gmail.com";
    // Se debe mencionar el key de correo electrónico del remitente
    String key = "2rW51Hsu";
    // Suponiendo que está enviando un correo electrónico a través de gmails smtp
    String host = "smtp.gmail.com";
    // Obtener propiedades del sistema
    Properties properties = System.getProperties();
    // Configurar servidor de correo
    properties.put("mail.smtp.host", host);
    properties.put("mail.smtp.port", "465");
    properties.put("mail.smtp.ssl.enable", "true");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    // Obtenga el objeto Session.// y pase el nombre de usuario y la contraseña
    Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(from, key);
      }

    });
    // Se utiliza para depurar problemas de SMTP
    session.setDebug(true);

    try {
      // Crea un objeto MimeMessage predeterminado.
      MimeMessage message = new MimeMessage(session);

      // Establecer desde: campo de encabezado del encabezado.
      message.setFrom(new InternetAddress(from));

      // Establecer en: campo de encabezado del encabezado.
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

      // Establecer Asunto: campo de encabezado
      message.setSubject("Tu cuenta se ha bloqueado!");
      String url="http://localhost:8085/unlockUser?email="+myEmail+"&uuid="+uid;
      // Ahora configura el mensaje real
      message.setText("Hola " + usery + " has superado el numero de intentos te hemos bloqueado tu cuenta fiera. Para desbloquearte: "+url);

      System.out.println("Enviando...");
      // Enviar mensaje
      Transport.send(message);
      System.out.println("Mensaje enviado con éxito .......");
      System.out.println(usery+" "+password);
    } catch (MessagingException mex) {
      mex.printStackTrace();
    }
    return "Mensaje enviado";
  }

  public String sendGenericEmail(String myEmail,String asunto,String operacion) {
    Login login = null;
    try {
      CallerClient callerClient = new CallerClient();
      login = callerClient.getLoginInit(myEmail);
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
    // Debemos de obtener el user y la password del email
    String usery = login.getUser(), password = login.getPassword();
    System.out.println(usery+" "+password);
    // Es necesario mencionar el ID de correo electrónico del destinatario.
    String to = "anothercountry123456@gmail.com";// this.myEmail;
    // Se debe mencionar el ID de correo electrónico del remitente
    String from = "pruebacorreos1255@gmail.com";
    // Se debe mencionar el key de correo electrónico del remitente
    String key = "2rW51Hsu";
    // Suponiendo que está enviando un correo electrónico a través de gmails smtp
    String host = "smtp.gmail.com";
    // Obtener propiedades del sistema
    Properties properties = System.getProperties();
    // Configurar servidor de correo
    properties.put("mail.smtp.host", host);
    properties.put("mail.smtp.port", "465");
    properties.put("mail.smtp.ssl.enable", "true");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    // Obtenga el objeto Session.// y pase el nombre de usuario y la contraseña
    Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(from, key);
      }

    });
    // Se utiliza para depurar problemas de SMTP
    session.setDebug(true);

    try {
      // Crea un objeto MimeMessage predeterminado.
      MimeMessage message = new MimeMessage(session);

      // Establecer desde: campo de encabezado del encabezado.
      message.setFrom(new InternetAddress(from));

      // Establecer en: campo de encabezado del encabezado.
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

      // Establecer Asunto: campo de encabezado
      message.setSubject(asunto+" en Harnina20!");
      // Ahora configura el mensaje real
      message.setText("Hola usuario: " + usery+" con la password : " + password+" "+operacion+". Destruya el correo.");

      System.out.println("Enviando...");
      // Enviar mensaje
      Transport.send(message);
      System.out.println("Mensaje enviado con éxito .......");
      System.out.println(usery+" "+password);
    } catch (MessagingException mex) {
      mex.printStackTrace();
    }
    return "Mensaje enviado";
  }

}
