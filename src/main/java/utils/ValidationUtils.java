package utils;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

    private static final Pattern TELEFONE_PATTERN = Pattern.compile("^\\(\\d{2}\\)\\s\\d{9}$");

    private ValidationUtils() { throw new UnsupportedOperationException("Classe utilitária"); }

    public static boolean isEmailValido(String email){
        if(email.trim().isEmpty() || email.isBlank()) throw new IllegalArgumentException("E-mail vazio.");

        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            return true;
        } catch (AddressException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isTelefoneValido(String telefone){
        if(telefone.trim().isEmpty() || telefone.isBlank()) throw new IllegalArgumentException("Telefone vazio.");

        Matcher matcher = TELEFONE_PATTERN.matcher(telefone);

        return telefone.length() < 13 || matcher.matches();
    }
}
