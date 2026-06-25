package utils;

import java.security.MessageDigest;

public class Hash {

    public static String gerarHash(String texto) {

        try {

            MessageDigest algoritmoHash = MessageDigest.getInstance("SHA-256");
            byte[] bytesHash = algoritmoHash.digest(texto.getBytes());

            StringBuilder hashConvertido = new StringBuilder();
            for (byte byteAtual : bytesHash) {
                hashConvertido.append(String.format("%02x", byteAtual));
            }
            return hashConvertido.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}