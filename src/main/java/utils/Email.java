package utils;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import io.github.cdimascio.dotenv.Dotenv;

public class Email {

    private static final Dotenv ENV = Dotenv.load();

    public static void enviar(String destinatario, String assunto, String mensagem) {

        final String remetente = ENV.get("EMAIL_REMETENTE");
        final String senhaApp = ENV.get("EMAIL_SENHA");

        Properties propriedades = new Properties();

        propriedades.put("mail.smtp.auth", "true");
        propriedades.put("mail.smtp.starttls.enable", "true");
        propriedades.put("mail.smtp.host", "smtp.gmail.com");
        propriedades.put("mail.smtp.port","587");
        
        System.out.println(remetente);
        System.out.println(senhaApp);
        
        Session sessao = Session.getInstance(propriedades, new Authenticator() {
        	protected PasswordAuthentication getPasswordAuthentication() {
        		return new PasswordAuthentication(remetente, senhaApp);
                    }
                });
        try {

            Message email = new MimeMessage(sessao);
            email.setFrom(new InternetAddress(remetente));
            email.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            email.setSubject(assunto);
            email.setText(mensagem);
            Transport.send(email);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}

