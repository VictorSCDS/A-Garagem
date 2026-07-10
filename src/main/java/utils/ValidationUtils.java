package utils;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

    private static final Pattern TELEFONE_PATTERN = Pattern.compile("^\\(\\d{2}\\)\\s\\d{9}$");
    private static final Pattern PLACA_ANTIGA_PATTERN = Pattern.compile("[A-Z]{3}[0-9]{4}");
    private static final Pattern PLACA_ATUAL_PATTERN = Pattern.compile("[A-Z]{3}[0-9][A-Z][0-9]{2}");

    private ValidationUtils() { throw new UnsupportedOperationException("Classe utilitária"); }

    public static boolean isEmailValido(String email){
        if(email == null || email.isEmpty()) throw new IllegalArgumentException("E-mail vazio");

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
        if(telefone == null || telefone.isEmpty()) throw new IllegalArgumentException("Telefone vazio");

        Matcher matcher = TELEFONE_PATTERN.matcher(telefone);

        return !(telefone.length() < 13) || matcher.matches();
    }

    public static boolean isPlacaValida(String placa) {

        if(placa == null || placa.isEmpty()) throw new IllegalArgumentException("Placa vazia");

        Matcher matcherAntigo = PLACA_ANTIGA_PATTERN.matcher(placa);
        Matcher matcherAtual = PLACA_ATUAL_PATTERN.matcher(placa);

        return matcherAntigo.matches() || matcherAtual.matches();
    }
}