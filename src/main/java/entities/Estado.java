package entities;

public enum Estado {
    NAO_PAGO,
    PAGO,
    CONCLUIDO,
    CANCELADO;

    public static Estado fromString(String str) {
        return Estado.valueOf(str.toUpperCase());
    }

    public static String estadoToString(Estado estado) {
        return String.valueOf(estado).replace("_", " ");
    }
}
