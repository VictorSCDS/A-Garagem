package utils;

import java.io.File;
import java.util.Properties;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class Email {

    private static final Dotenv ENV = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    private static String ultimoErro = "";

    public static String getUltimoErro() {
        return ultimoErro;
    }

    private static String buscarVariavel(String nome) {
        String valor = ENV.get(nome);

        if (valor == null || valor.trim().isEmpty()) {
            valor = System.getenv(nome);
        }

        return valor != null ? valor.trim() : "";
    }

    public static boolean enviar(String destinatario, String assunto, String mensagem) {
        return enviarComAnexo(destinatario, assunto, mensagem, null);
    }

    public static boolean enviarComAnexo(String destinatario, String assunto, String mensagem, String caminhoAnexo) {
        ultimoErro = "";

        final String remetente = buscarVariavel("EMAIL_REMETENTE");
        final String senhaApp = buscarVariavel("EMAIL_SENHA").replace(" ", "");

        if (remetente.isEmpty()) {
            ultimoErro = "EMAIL_REMETENTE não foi encontrado no .env ou nas variáveis de ambiente.";
            System.err.println(ultimoErro);
            return false;
        }

        if (senhaApp.isEmpty()) {
            ultimoErro = "EMAIL_SENHA não foi encontrado no .env ou nas variáveis de ambiente.";
            System.err.println(ultimoErro);
            return false;
        }

        if (destinatario == null || destinatario.trim().isEmpty()) {
            ultimoErro = "E-mail do destinatário está vazio.";
            System.err.println(ultimoErro);
            return false;
        }

        File arquivoAnexo = null;

        if (caminhoAnexo != null && !caminhoAnexo.trim().isEmpty()) {
            arquivoAnexo = new File(caminhoAnexo);

            if (!arquivoAnexo.exists()) {
                ultimoErro = "Arquivo PDF não encontrado: " + caminhoAnexo;
                System.err.println(ultimoErro);
                return false;
            }
        }

        Properties propriedades = new Properties();
        propriedades.put("mail.smtp.auth", "true");
        propriedades.put("mail.smtp.starttls.enable", "true");
        propriedades.put("mail.smtp.starttls.required", "true");
        propriedades.put("mail.smtp.host", "smtp.gmail.com");
        propriedades.put("mail.smtp.port", "587");
        propriedades.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        propriedades.put("mail.smtp.connectiontimeout", "10000");
        propriedades.put("mail.smtp.timeout", "10000");
        propriedades.put("mail.smtp.writetimeout", "10000");

        Session sessao = Session.getInstance(propriedades, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remetente, senhaApp);
            }
        });

        // Deixar true enquanto estiver testando, aí epois trocar para false.
        sessao.setDebug(true);

        try {
            Message email = new MimeMessage(sessao);
            email.setFrom(new InternetAddress(remetente));
            email.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            email.setSubject(assunto);

            if (arquivoAnexo == null) {
                email.setText(mensagem);
            } else {
                MimeBodyPart corpoMensagem = new MimeBodyPart();
                corpoMensagem.setText(mensagem, "UTF-8");

                MimeBodyPart anexo = new MimeBodyPart();
                anexo.attachFile(arquivoAnexo);

                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(corpoMensagem);
                multipart.addBodyPart(anexo);

                email.setContent(multipart);
            }

            Transport.send(email);
            return true;

        } catch (Exception e) {
            ultimoErro = e.getClass().getSimpleName() + ": " + e.getMessage();
            e.printStackTrace();
            return false;
        }
    }
}