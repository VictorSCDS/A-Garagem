package utils;

public class Codigo {

    public static String gerar() {
        int numero = (int)(100000 + Math.random() * 900000);
        return String.valueOf(numero);
    }
}